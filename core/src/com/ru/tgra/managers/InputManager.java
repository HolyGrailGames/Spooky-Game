package com.ru.tgra.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.graphics.Camera;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Point3D;

public class InputManager {
	
	private static Point3D lastMousePos = null;
	private static Point3D currMousePos = new Point3D();

	public static void processInput(Camera cam, float deltaTime) {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.slide(-deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.slide(deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			//cam.slide(0, 0, -3.0f * deltaTime);
			cam.walkForward(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			//cam.slide(0, 0, 3.0f * deltaTime);
			cam.walkForward(-deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.R)) {
			cam.slide(0, deltaTime, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F)) {
			cam.slide(0, -deltaTime, 0);
		}

		/*
		// Zooming
		if(Gdx.input.isKeyPressed(Input.Keys.T)) {
			fov -= 30.0f * deltaTime;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.G)) {
			fov += 30.0f * deltaTime;
		}
		*/

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			Gdx.graphics.setDisplayMode(500, 500, false);
			Gdx.app.exit();
		}
	}
	
	public static boolean mouseMoved(int screenX, int screenY) {
		
		if (lastMousePos == null) {
			lastMousePos = new Point3D(screenX, screenY, 0.0f);
			return false;
		}
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		currMousePos.set(screenX, screenY, 0.0f);
		
		float dx = currMousePos.x - lastMousePos.x;
		float dy = currMousePos.y - lastMousePos.y;
		
		lastMousePos.set(currMousePos.x, currMousePos.y, currMousePos.z);
		
		if(dx < 0) {
			GameManager.cam.rotateY(Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dx));
		}
		if(dx > 0) {
			GameManager.cam.rotateY(-Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dx));
		}
		
		if(dy < 0) {
			GameManager.cam.pitch(Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dy));
		}
		if(dy > 0) {
			GameManager.cam.pitch(-Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dy));
		}
		
		return false;
	}
}
