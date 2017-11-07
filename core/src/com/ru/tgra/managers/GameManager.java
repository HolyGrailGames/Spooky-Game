package com.ru.tgra.managers;

import java.util.ArrayList;
import java.util.List;
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
import com.ru.tgra.graphics.shapes.PlaneGraphic;
import com.ru.tgra.graphics.shapes.SphereGraphic;
import com.ru.tgra.graphics.shapes.g3djmodel.G3DJModelLoader;
import com.ru.tgra.graphics.shapes.g3djmodel.MeshModel;
import com.ru.tgra.objects.Firefly;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class GameManager {
	

	public static Texture alphaTex = new Texture(Gdx.files.internal("textures/flamealphatex.png"));
	public static Texture flameTex = new Texture(Gdx.files.internal("textures/flametex01.png"));

	public static List<Firefly> initializeFireflies(int fireflyCount)
	{
		List<Firefly> fireflies = new ArrayList<Firefly>();
		
		for (int i = 0; i < fireflyCount; i++)
		{
			Firefly firefly = new Firefly(new Point3D(5f, 2f, 5f), 0.2f, flameTex, alphaTex);
			fireflies.add(firefly);
		}
		
		return fireflies;
	}

}
