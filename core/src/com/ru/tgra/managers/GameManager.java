package com.ru.tgra.managers;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.game.Player;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.BoxGraphic;
import com.ru.tgra.graphics.shapes.PlaneGraphic;
import com.ru.tgra.graphics.shapes.SphereGraphic;
import com.ru.tgra.graphics.shapes.SpriteGraphic;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;
import com.ru.tgra.objects.Floor;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class GameManager {

	public static Shader shader;

	private static float angle;

	//public static Camera cam;

	static MeshModel model;

	private static Texture tex;
	private static Texture groundTexture1;
	//private static Texture alphaTex;
	
	public static Player player;
	private static Floor floor;
	
	Random rand = new Random();

	public static void create () {

		DisplayMode disp = Gdx.graphics.getDesktopDisplayMode();
		Gdx.graphics.setDisplayMode(disp.width, disp.height, true);

		shader = new Shader();

		tex = new Texture(Gdx.files.internal("textures/phobos2k.png"));
		//alphaTex = new Texture(Gdx.files.internal("textures/alphaMap01.png"));
		groundTexture1 = new Texture(Gdx.files.internal("textures/grass_tex.jpg"));

		model = G3DJModelLoader.loadG3DJFromFile("testBlob.g3dj", true);

		BoxGraphic.create();
		SphereGraphic.create();
		PlaneGraphic.create();
		SpriteGraphic.create();

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
		
		player = new Player(new Point3D(0f,2f,-3f), new Vector3D(0,0,1));
		
		floor = new Floor(
				new Point3D((Settings.GROUND_WIDTH*3)/2, -0.5f, (Settings.GROUND_HEIGHT*3)/2),
				new Vector3D(Settings.GROUND_WIDTH*3, 1.0f, Settings.GROUND_HEIGHT*3),
				new Vector3D(), null, groundTexture1);
	}

	public static void input()
	{
		InputManager.processInput(player, Gdx.graphics.getDeltaTime());
	}
	
	public static void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();

		angle += 180.0f * deltaTime;

		//do all updates to the game
	}
	
	public static void display()
	{
		//do all actual drawing and rendering here
		
		player.display();
				
		//BoxGraphic.drawOutlineCube();
		//SphereGraphic.drawSolidSphere();
		//SphereGraphic.drawOutlineSphere();


		ModelMatrix.main.loadIdentityMatrix();

		//ModelMatrix.main.addRotationZ(angle);

		float s = (float)Math.sin((angle / 2.0) * Math.PI / 180.0);
		float c = (float)Math.cos((angle / 2.0) * Math.PI / 180.0);

		//shader.setLightPosition(0.0f + c * 3.0f, 5.0f, 0.0f + s * 3.0f, 1.0f);
		shader.setLightPosition(3.0f, 4.0f, 0.0f, 1.0f);
		//shader.setLightPosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);

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

		floor.display();
	}
}
