package com.ru.tgra.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.*;
import com.ru.tgra.graphics.shapes.*;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;
import com.ru.tgra.managers.InputManager;
import com.ru.tgra.motion.BSplineMotion;
import com.ru.tgra.motion.BezierMotion;
import com.ru.tgra.motion.LinearMotion;
import com.ru.tgra.motion.Motion;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class SpookyGame extends ApplicationAdapter implements InputProcessor {

	static Shader shader;

	static MeshModel model;

	private static Texture tex;
	private static Texture groundTexture1;
	private static Texture alphaTex;
	
	Motion bsplineMotion;
	Motion motion;
	
	float currentTime;
	boolean firstFrame = true;

	Random rand = new Random();

	public static Player player;
	
	float yaw = 0;

	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		//Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		tex = new Texture(Gdx.files.internal("textures/phobos2k.png"));
		alphaTex = new Texture(Gdx.files.internal("textures/alphaMap01.png"));
		groundTexture1 = new Texture(Gdx.files.internal("textures/grass_tex2.png"));

		model = G3DJModelLoader.loadG3DJFromFile("testBlob.g3dj", true);
		model.setPosition(new Point3D(0.0f, 0.0f, -1.0f));
		
		
		
		/*motion = new BezierMotion(new Point3D(0.0f, 0.0f, -1.0f),
										new Point3D(7.0f, 0.0f, 3.0f), 
										new Point3D(3.0f, 0.0f, 5.0f), 
										new Point3D(5.0f, 0.0f, 1.0f), 3.0f, 15.0f);
		
		*/
		ArrayList<Point3D> controlPoints = new ArrayList<Point3D>();
		controlPoints.add(new Point3D(0.0f, 0.0f, -1.0f));
		controlPoints.add(new Point3D(15.0f, 0.0f, 3.0f));
		controlPoints.add(new Point3D(0.0f, 0.0f, -2.0f));
		controlPoints.add(new Point3D(3.0f, 2.0f, -1.0f));
		controlPoints.add(new Point3D(6.0f, 0.0f, -1.0f));
		controlPoints.add(new Point3D(6.0f, 7.0f, 1.0f));
		controlPoints.add(new Point3D(4.0f, 0.0f, 5.0f));
		controlPoints.add(new Point3D(0.0f, -1.0f, -3.0f));
		controlPoints.add(new Point3D(2.0f, 3.0f, -7.0f));
		controlPoints.add(new Point3D(0.0f, 0.0f, -2.0f));
		controlPoints.add(new Point3D(1.0f, 1.0f, -4.0f));
		controlPoints.add(new Point3D(0.0f, 3.0f, -5.0f));
		controlPoints.add(new Point3D(3.0f, 2.0f, 1.0f));
		controlPoints.add(new Point3D(0.0f, 0.0f, -2.0f));
		controlPoints.add(new Point3D(0.0f, 1.0f, 3.0f));
		controlPoints.add(new Point3D(5.0f, 0.0f, 1.0f));
		bsplineMotion = new BSplineMotion(controlPoints, 3.0f, 45.0f);

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

		player = new Player(new Point3D(0f, 1f, 0f), new Vector3D(0,0,-1));
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
		
		yaw += player.yaw;
		player.update(deltaTime);
		
		
			
		if (firstFrame)
		{
			currentTime = 0.0f;
			firstFrame = false;
		}
		else {
			currentTime += deltaTime;
		}
		
		//bezierMotion.getCurrentPosition(currentTime, model.getPosition());
		bsplineMotion.getCurrentPosition(currentTime, model.getPosition());
	}

	public void display()
	{
		ModelMatrix.main.loadIdentityMatrix();
		Gdx.graphics.setTitle("SpookyGame | FPS: " + Gdx.graphics.getFramesPerSecond());
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		player.cam.display(shader);
		shader.setMaterial(Settings.TEST_MATERIAL);
		shader.setGlobalAmbient(0.3f, 0.3f, 0.3f, 1);
		
		player.display(shader);
		//floor.display(shader);

		/*** LIGHTS ***/
		//shader.setLight(light);
		
		/*** MODEL ***/
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(model.getPosition().x, model.getPosition().y, model.getPosition().z);
		ModelMatrix.main.addScale(0.2f, 0.2f, 0.2f);
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
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		//PlaneGraphic.drawSolidPlane(shader, groundTexture1, null);
		NewPlaneGraphic.drawSolidPlane(shader, groundTexture1, null);
		
		
		ModelMatrix.main.popMatrix();
		/*
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(0.0f, 0.01f, 0.0f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		NewPlaneGraphic.drawOutlinePlane(shader, null, null);
		ModelMatrix.main.popMatrix();
		*/
		ModelMatrix.main.pushMatrix();
		shader.setMaterialDiffuse(1, 1, 0, 1);
		ModelMatrix.main.addTranslation(-0.5f, 0.0f, 0.5f);
		ModelMatrix.main.addScale(0.03f, 0.03f, 0.03f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere(shader, null, null);
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