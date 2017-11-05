package com.ru.tgra.game;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;
import com.ru.tgra.managers.GameManager;
import com.ru.tgra.managers.InputManager;
import com.ru.tgra.objects.Floor;
import com.ru.tgra.objects.Tile;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

import static sun.audio.AudioPlayer.player;

public class SpookyGame extends ApplicationAdapter implements InputProcessor {

	static Shader shader;


	private static float fov = 90.0f;

	static MeshModel model;

	private static Texture tex;
	private static Texture groundTexture1;
	private static Texture alphaTex;

	Random rand = new Random();

	public static Player player;

	//private Floor floor;

	//private Tile tile;

	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		tex = new Texture(Gdx.files.internal("textures/phobos2k.png"));
		alphaTex = new Texture(Gdx.files.internal("textures/alphaMap01.png"));
		groundTexture1 = new Texture(Gdx.files.internal("textures/grass_tex.jpg"));

		model = G3DJModelLoader.loadG3DJFromFile("testBlob.g3dj", true);

		BoxGraphic.create();
		SphereGraphic.create();
		PlaneGraphic.create();
		NewPlaneGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());;


		//TODO: try this way to create a texture image
		/*Pixmap pm = new Pixmap(128, 128, Format.RGBA8888);
		for(int i = 0; i < pm.getWidth(); i++)
		{
			for(int j = 0; j < pm.getWidth(); j++)
			{
				pm.drawPixel(i, j, rand.nextInt());
			}
		}
		tex = new Texture(pm);*/

		Gdx.gl.glClearColor(Settings.FOG_COLOR.r, Settings.FOG_COLOR.g, Settings.FOG_COLOR.b, Settings.FOG_COLOR.a);

		player = new Player(new Point3D(0f, 2f, 0f), new Vector3D(1,0,1));
		/*
		floor = new Floor(
				new Point3D(2, 0, 2), new Vector3D(Settings.GROUND_WIDTH, 1.0f, Settings.GROUND_HEIGHT),
				Settings.TEST_MATERIAL, groundTexture1);
		
		tile = new Tile(new Point3D(3,1,3), new Vector3D(1,1,1), Settings.TEST_MATERIAL, groundTexture1);
		*/

	}

	@Override
	public void render () {

		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}

	public void input()
	{
		InputManager.processInput(player, Gdx.graphics.getDeltaTime());
	}

	public void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		player.update(deltaTime);

	}

	public void display()
	{
		Gdx.graphics.setTitle("SpookyGame | FPS: " + Gdx.graphics.getFramesPerSecond());
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		player.display(shader);
		//floor.display(shader);

		/*** LIGHTS ***/
		//shader.setLight(light);
		shader.setGlobalAmbient(0.3f, 0.3f, 0.3f, 1);


		/*** MODEL ***/
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(0.0f, 4.0f, 0.0f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		model.draw(shader, tex);
		ModelMatrix.main.popMatrix();

		/*** PLANE ***/
		drawGround();
		//tile.display(shader);
	}



	private static void drawGround()
	{
		shader.setMaterial(Settings.TEST_MATERIAL);


		// Draw the floor of the maze.
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(2, 1f, 2);
		ModelMatrix.main.addScale(10,1,10);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		//PlaneGraphic.drawSolidPlane(shader, groundTexture1, null);
		NewPlaneGraphic.drawSolidPlane(shader);
		ModelMatrix.main.popMatrix();


	}
	


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		InputManager.mouseMoved(screenX, screenY, player);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}