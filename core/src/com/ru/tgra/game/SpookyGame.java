package com.ru.tgra.game;

import java.util.ArrayList;
import java.util.List;
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
import com.ru.tgra.managers.GameManager;
import com.ru.tgra.managers.InputManager;
import com.ru.tgra.motion.BSplineMotion;
import com.ru.tgra.motion.BezierMotion;
import com.ru.tgra.motion.LinearMotion;
import com.ru.tgra.motion.Motion;
import com.ru.tgra.noise.NoiseAlgorithm;
import com.ru.tgra.objects.Firefly;
import com.ru.tgra.objects.Terrain;
import com.ru.tgra.objects.Tile;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class SpookyGame extends ApplicationAdapter implements InputProcessor {

	static Shader shader;


	private static Texture groundTexture1;
	private static Texture waterTexture;
	
	Motion bsplineMotion;
	Motion motion;
	
	List<Firefly> fireflies;
	List<BSplineMotion> curves;
	
	float currentTime;
	boolean firstFrame = true;

	Random rand = new Random();

	public static Player player;
	private Terrain terrain;
	private Terrain water;
	
	float yaw = 0;

	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		//Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();
		
		groundTexture1 = new Texture(Gdx.files.internal("textures/grass_tex.jpg"));
		waterTexture = new Texture(Gdx.files.internal("textures/water.jpg"));

		fireflies = GameManager.initializeFireflies(Settings.FIREFLY_COUNT);
		curves = new ArrayList<BSplineMotion>();
		
		for (int i = 0; i < Settings.FIREFLY_COUNT; i++)
		{
			BSplineMotion curve = GameManager.initializeCurves(fireflies.get(i).getPosition());
			curves.add(curve);
		}
		
		/*
		ArrayList<Point3D> controlPoints = new ArrayList<Point3D>();
		controlPoints.add(new Point3D(0.0f, 1.0f, -1.0f));
		controlPoints.add(new Point3D(5.0f, 1.5f, 3.0f));
		controlPoints.add(new Point3D(10.0f, 0.5f, 7.0f));
		controlPoints.add(new Point3D(15.0f, 1.0f, 11.0f));
		controlPoints.add(new Point3D(10.0f, 1.0f, 15.0f));
		controlPoints.add(new Point3D(5.0f, 1.0f, 9.0f));
		controlPoints.add(new Point3D(0.0f, 1.5f, 11.0f));
		controlPoints.add(new Point3D(5.0f, 0.5f, 13.0f));
		controlPoints.add(new Point3D(10.0f, 0.5f, 11.0f));
		controlPoints.add(new Point3D(15.0f, 1.0f, 9.0f));
		controlPoints.add(new Point3D(10.0f, 1.5f, 7.0f));
		controlPoints.add(new Point3D(5.0f, 1.0f, 5.0f));
		controlPoints.add(new Point3D(0.0f, 1.0f, 3.0f));
		controlPoints.add(new Point3D(15.0f, 1.0f, 1.0f));
		controlPoints.add(new Point3D(7.0f, 1.0f, -1.0f));
		controlPoints.add(new Point3D(0.0f, 1.0f, -1.0f));
		bsplineMotion = new BSplineMotion(controlPoints, 5.0f, 20.0f);
		*/
		
		BoxGraphic.create();
		SphereGraphic.create();
		SpriteGraphic.create();
		
		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		Gdx.gl.glClearColor(Settings.FOG_COLOR.r, Settings.FOG_COLOR.g, Settings.FOG_COLOR.b, Settings.FOG_COLOR.a);

		
		float startX = Settings.TERRAIN_SIMPLEX_SIZE;
		float startY = Settings.TERRAIN_SIMPLEX_SCALE;
		
		if (Settings.NOISE_ALG == NoiseAlgorithm.DIAMOND_SQUARE) {
			startX = Settings.TERRAIN_DSQUARE_SIZE;
			startY = Settings.TERRAIN_DSQUARE_RANGE /*Settings.TERRAIN_DSQUARE_SCALE*/;
		}
		player = new Player(new Point3D(startX, startY, startX), new Vector3D(-1,-0.7f,-1));

		terrain = new Terrain(new Point3D(0,0,0), null, Settings.TERRAIN_MATERIAL, Settings.NOISE_ALG, false);
		water = new Terrain(new Point3D(0,0,0), waterTexture, Settings.WATER_MATERIAL, Settings.NOISE_ALG, true);
		
	}

	@Override
	public void render () {
		input();
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
		
		manageTotalTime(deltaTime);
		
		for(int i = 0; i < fireflies.size(); i++)
		{
			curves.get(i).getCurrentPosition(currentTime, fireflies.get(i).getPosition());	
		}
	}

	public void display()
	{
		Gdx.graphics.setTitle("SpookyGame | FPS: " + Gdx.graphics.getFramesPerSecond());

		/*
		Gdx.gl.glEnable(GL20.GL_CULL_FACE); // cull face
		Gdx.gl.glCullFace(GL20.GL_BACK); // cull back face
		Gdx.gl.glFrontFace(GL20.GL_CCW); // GL_CCW for counter clock-wise
		*/
		
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		player.cam.display(shader);

		shader.setMaterial(Settings.TEST_MATERIAL);
		shader.setGlobalAmbient(0.3f, 0.3f, 0.3f, 1);
		
		player.display(shader);

		/*** LIGHTS ***/
		//shader.setLight(light);
		
		/*** FIREFLIES ***/
		for (Firefly firefly : fireflies)
		{
			firefly.draw(shader);
		}

		/*** TERRAIN ***/
		terrain.display(shader);
		water.display(shader);
	}
	
	private void manageTotalTime(float deltaTime)
	{
		if (firstFrame)
		{
			currentTime = 0.0f;
			firstFrame = false;
		}
		else {
			currentTime += deltaTime;
		}
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