package com.ru.tgra.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Particles
{
	// Singleton
	private Particles() {};
	
	public static Texture flameTex;
	
	public static void create()
	{
		flameTex = new Texture(Gdx.files.internal("textures/flametex01.png"));
	}
	
}
