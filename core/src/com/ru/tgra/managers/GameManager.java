package com.ru.tgra.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.motion.BSplineMotion;
import com.ru.tgra.objects.Firefly;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Utilities;

public class GameManager {
	public static boolean wireframe = false;
	public static Texture alphaTex = new Texture(Gdx.files.internal("textures/flamealphatex.png"));
	public static Texture flameTex = new Texture(Gdx.files.internal("textures/flametex01.png"));
	
	private int fireflyCount;
	private static Random rand = new Random();

	public static List<Firefly> initializeFireflies(int fireflyCount)
	{
		List<Firefly> fireflies = new ArrayList<Firefly>();
		
		float rad = (Settings.TERRAIN_SIZE * Settings.TERRAIN_SCALE) / 2.0f;
		
		for (int i = 0; i < fireflyCount; i++)
		{	
			Point3D fireflyPosition = 	new Point3D(Utilities.map(rand.nextFloat(), 0.0f, 1.0f, -rad, rad), // X Pos
													2.0f, 													// Y Pos
													Utilities.map(rand.nextFloat(), 0.0f, 1.0f, -rad, rad));// Z Pos
			
			Firefly firefly = new Firefly(fireflyPosition, 0.2f, flameTex, alphaTex);
			fireflies.add(firefly);
		}
		
		return fireflies;
	}
	
	public static BSplineMotion initializeCurves(Point3D fireflyPosition)
	{
		float pointIncrement = 0.5f;
		
		ArrayList<Point3D> controlPoints = new ArrayList<Point3D>();
		controlPoints.add(fireflyPosition);
		
		for (int i = 1; i < 16; i++)
		{
			Point3D controlPoint = new Point3D(Utilities.map(rand.nextFloat(), 0.0f, 1.0f, -pointIncrement, pointIncrement), // X Pos
											   rand.nextFloat() * 2 + 1, // Y Pos
											   Utilities.map(rand.nextFloat(), 0.0f, 1.0f, -pointIncrement, pointIncrement));// Z Pos
					
			controlPoints.add(controlPoint);
			
			pointIncrement += 0.3f;
		}
		
		
		
		BSplineMotion curve = new BSplineMotion(controlPoints, rand.nextFloat() * 2, 50f);
		
		return curve;
	}
}
