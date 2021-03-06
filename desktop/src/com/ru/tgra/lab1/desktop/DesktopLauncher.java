package com.ru.tgra.lab1.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.game.SpookyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Mesh and Texture exercise"; // or whatever you like
		//config.width = 1024;  //experiment with
		//config.height = 512;  //the window size
		config.width = 1365;  //experiment with
		config.height = 768;  //the window size
		config.x = 80;
		config.y = 80;
		//config.fullscreen = true;
		

		new LwjglApplication(new SpookyGame(), config);
	}
}
