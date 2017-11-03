package com.ru.tgra.game;

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
import com.ru.tgra.managers.GameManager;
import com.ru.tgra.managers.InputManager;
import com.ru.tgra.objects.Floor;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class SpookyGame extends ApplicationAdapter implements InputProcessor {

	private Shader shader;
	
	private static float angle;

	//public static Camera cam;

	static MeshModel model;
	
	ParticleEffect particleEffect;
	
	private static Texture tex;
	private static Texture groundTexture1;
	private static Texture alphaTex;
	
	
	public static Player player;
	private static Floor floor;
	
	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);
		
		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		//GameManager.create();
		BoxGraphic.create();
		SphereGraphic.create();
		PlaneGraphic.create();
		SpriteGraphic.create();
		Particles.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		
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

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		tex = new Texture(Gdx.files.internal("textures/phobos2k.png"));
		alphaTex = new Texture(Gdx.files.internal("textures/flamealphatex.png"));
		groundTexture1 = new Texture(Gdx.files.internal("textures/grass_tex.jpg"));
		
		model = G3DJModelLoader.loadG3DJFromFile("testBlob.g3dj", true);
		
		particleEffect = new ParticleEffect(new Point3D(5f, 2f, 5f), 30.0f, 3.0f, Particles.flameTex, alphaTex);

		player = new Player(new Point3D(10f, 2f, 10f), new Vector3D(1,0,1));
		
		floor = new Floor(
				new Point3D((Settings.GROUND_WIDTH*3)/2, -0.5f, (Settings.GROUND_HEIGHT*3)/2),
				new Vector3D(Settings.GROUND_WIDTH*3, 1.0f, Settings.GROUND_HEIGHT*3),
				new Vector3D(), null, groundTexture1);
		
	}
	
	@Override
	public void render () {
		
		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}
	
	private void input()
	{
		InputManager.processInput(player, Gdx.graphics.getDeltaTime());
	}
	
	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		angle += 180.0f * deltaTime;
		particleEffect.update(deltaTime);
		
		//do all updates to the game
	}
	
	private void display()
	{
		//do all actual drawing and rendering here
		
		shader.setMaterialDiffuse(0.0f, 0.0f, 0.0f, 1.0f);
		shader.setMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
		shader.setMaterialEmission(1.0f, 1.0f, 1.0f, 1.0f);
		shader.setShininess(50.0f);
		
		// TODO: just have Global ambient lighting here
		
		shader.setGlobalAmbient(0.6f, 0.6f, 0.6f, 1);
		
		shader.setLightPosition(player.position.x, 0, player.position.z, 1.0f);
		shader.setSpotDirection(player.direction.x, player.direction.y, player.direction.z, 0.0f);
		
		shader.setSpotExponent(1.0f);
		shader.setConstantAttenuation(1.0f);
		//shader.setLinearAttenuation(0.00f);
		//shader.setQuadraticAttenuation(0.50f);
		
		shader.setLightColor(1.0f, 0.0f, 0.0f, 1.0f);

		player.display(shader);
		floor.display(shader);
		
		
		
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(4, 2, 4);
		
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube(shader, null, null);
		
		ModelMatrix.main.popMatrix();
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(10, 2, 2);
		ModelMatrix.main.addScale(4, 4, 4);	
		
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube(shader, null, null);
		
		ModelMatrix.main.popMatrix();
		
		
		// Particle stuff happening, its cool!
		ModelMatrix.main.pushMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		particleEffect.draw(shader);
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