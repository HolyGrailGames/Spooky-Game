package com.ru.tgra.managers;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Camera;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.BoxGraphic;
import com.ru.tgra.graphics.shapes.SphereGraphic;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class GameManager {

	static Shader shader;

	private static float angle;

	public static Camera cam;
	private static Camera topCam;
	
	private static float fov = 90.0f;

	static MeshModel model;

	private static Texture tex;
	private static Texture groundTexture1;
	private static Texture alphaTex;
	
	Random rand = new Random();

	public static void create () {

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		tex = new Texture(Gdx.files.internal("textures/phobos2k.png"));
		alphaTex = new Texture(Gdx.files.internal("textures/alphaMap01.png"));
		groundTexture1 = new Texture(Gdx.files.internal("textures/grass_tex.jpg"));

		model = G3DJModelLoader.loadG3DJFromFile("testBlob.g3dj", true);

		BoxGraphic.create();
		SphereGraphic.create();

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());

		cam = new Camera();
		cam.look(new Point3D(0f, 4f, -3f), new Point3D(0,4,0), new Vector3D(0,1,0));

		topCam = new Camera();
		//orthoCam.orthographicProjection(-5, 5, -5, 5, 3.0f, 100);
		topCam.perspectiveProjection(30.0f, 1, 3, 100);

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
		

	}

	public static void input()
	{
	}
	
	public static void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		angle += 180.0f * deltaTime;

		InputManager.processInput(cam, deltaTime);

		//do all updates to the game
	}
	
	public static void display()
	{
		//do all actual drawing and rendering here
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		//Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

		for(int viewNum = 0; viewNum < 2; viewNum++)
		{
			if(viewNum == 0)
			{
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				Gdx.gl.glScissor(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				cam.perspectiveProjection(fov, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 0.2f, 15.0f);
				shader.setViewMatrix(cam.getViewMatrix());
				shader.setProjectionMatrix(cam.getProjectionMatrix());
				shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
				
				
				shader.setFogStart(0.0f);
				shader.setFogEnd(15.0f);
				shader.setFogColor(0.7f, 0.7f, 0.7f, 1.0f);	// clear color should be same as fog color
				Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
				
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
				
			}
			else
			{
				Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				Gdx.gl.glScissor(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				topCam.look(new Point3D(cam.eye.x, 20.0f, cam.eye.z), cam.eye, new Vector3D(0,0,-1));
				//orthoCam.look(new Point3D(7.0f, 40.0f, -7.0f), new Point3D(7.0f, 0.0f, -7.0f), new Vector3D(0,0,-1));
				topCam.perspectiveProjection(30.0f, (float)Gdx.graphics.getWidth() / (float)(2*Gdx.graphics.getHeight()), 3, 100);
				shader.setViewMatrix(topCam.getViewMatrix());
				shader.setProjectionMatrix(topCam.getProjectionMatrix());
				shader.setEyePosition(topCam.eye.x, topCam.eye.y, topCam.eye.z, 1.0f);
				
				// We set the fog far away enough that our top down view isn't affected by it
				shader.setFogStart(90.0f);
				shader.setFogEnd(100.0f);
				shader.setFogColor(0.0f, 0.0f, 0.0f, 1.0f);	// clear color should be same as fog color
				Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			}

	
			//BoxGraphic.drawOutlineCube();
			//SphereGraphic.drawSolidSphere();
			//SphereGraphic.drawOutlineSphere();


			ModelMatrix.main.loadIdentityMatrix();

			//ModelMatrix.main.addRotationZ(angle);

			float s = (float)Math.sin((angle / 2.0) * Math.PI / 180.0);
			float c = (float)Math.cos((angle / 2.0) * Math.PI / 180.0);

			//shader.setLightPosition(0.0f + c * 3.0f, 5.0f, 0.0f + s * 3.0f, 1.0f);
			//shader.setLightPosition(3.0f, 4.0f, 0.0f, 1.0f);
			shader.setLightPosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);


			float s2 = Math.abs((float)Math.sin((angle / 1.312) * Math.PI / 180.0));
			float c2 = Math.abs((float)Math.cos((angle / 1.312) * Math.PI / 180.0));

			shader.setSpotDirection(s2, -0.3f, c2, 0.0f);
			//shader.setSpotDirection(-cam.n.x, -cam.n.y, -cam.n.z, 0.0f);
			shader.setSpotExponent(0.0f);
			shader.setConstantAttenuation(1.0f);
			shader.setLinearAttenuation(0.00f);
			shader.setQuadraticAttenuation(0.00f);

			//shader.setLightColor(s2, 0.4f, c2, 1.0f);
			shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);
			
			shader.setGlobalAmbient(0.3f, 0.3f, 0.3f, 1);

			//shader.setMaterialDiffuse(s, 0.4f, c, 1.0f);
			shader.setMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
			//shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialSpecular(0.0f, 0.0f, 0.0f, 1.0f);
			shader.setMaterialEmission(0, 0, 0, 1);
			shader.setShininess(50.0f);

			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(0.0f, 4.0f, 0.0f);
			//ModelMatrix.main.addRotation(angle, new Vector3D(1,1,1));
			shader.setModelMatrix(ModelMatrix.main.getMatrix());

			//BoxGraphic.drawSolidCube(shader, tex, null);
			//SphereGraphic.drawSolidSphere(shader, tex, alphaTex);
			model.draw(shader, tex);

			ModelMatrix.main.popMatrix();
	
			drawPyramids();
			drawGround();
		}
	}

	
	
	private static void drawGround()
	{
		// Draw the floor of the maze.
		ModelMatrix.main.pushMatrix();
		shader.setMaterialDiffuse(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.r, 1.0f);
		ModelMatrix.main.addTranslation((Settings.GROUND_WIDTH*3)/2, -0.5f, (Settings.GROUND_HEIGHT*3)/2);
		ModelMatrix.main.addScale(Settings.GROUND_WIDTH*3, 1.0f, Settings.GROUND_HEIGHT*3);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube(shader, groundTexture1, null);
		ModelMatrix.main.popMatrix();
	}

	private static void drawPyramids()
	{
		int maxLevel = 9;

		for(int pyramidNr = 0; pyramidNr < 2; pyramidNr++)
		{
			ModelMatrix.main.pushMatrix();
			if(pyramidNr == 0)
			{
				shader.setMaterialDiffuse(0.8f, 0.8f, 0.2f, 0.3f);
				shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
				shader.setShininess(150.0f);
				shader.setMaterialEmission(0, 0, 0, 1);
				ModelMatrix.main.addTranslation(0.0f, 0.0f, -7.0f);
			}
			else
			{
				shader.setMaterialDiffuse(0.5f, 0.3f, 1.0f, 0.8f);
				shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
				shader.setShininess(150.0f);
				shader.setMaterialEmission(0, 0, 0, 1);
				ModelMatrix.main.addTranslation(0.0f, 0.0f, 7.0f);
			}
			ModelMatrix.main.pushMatrix();
			for(int level = 0; level < maxLevel; level++)
			{
	
				ModelMatrix.main.addTranslation(0.55f, 1.0f, -0.55f);
	
				ModelMatrix.main.pushMatrix();
				for(int i = 0; i < maxLevel-level; i++)
				{
					ModelMatrix.main.addTranslation(1.1f, 0, 0);
					ModelMatrix.main.pushMatrix();
					for(int j = 0; j < maxLevel-level; j++)
					{
						ModelMatrix.main.addTranslation(0, 0, -1.1f);
						ModelMatrix.main.pushMatrix();
						if(i % 2 == 0)
						{
							ModelMatrix.main.addScale(0.2f, 1, 1);
						}
						else
						{
							ModelMatrix.main.addScale(1, 1, 0.2f);
						}
						shader.setModelMatrix(ModelMatrix.main.getMatrix());

						BoxGraphic.drawSolidCube(shader, null, null);
						//BoxGraphic.drawSolidCube(shader, tex);
						ModelMatrix.main.popMatrix();
					}
					ModelMatrix.main.popMatrix();
				}
				ModelMatrix.main.popMatrix();
			}
			ModelMatrix.main.popMatrix();
			ModelMatrix.main.popMatrix();
		}
	}


}
