package com.ru.tgra.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ru.tgra.game.Player;
import com.ru.tgra.graphics.Camera;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Point3D;

public class InputManager {
	
	private static Point3D lastMousePos = null;
	private static Point3D currMousePos = new Point3D();

	public static void processInput(Player player, float deltaTime) {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.walkSideways(-deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.walkSideways(deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.walkForward(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.walkForward(-deltaTime);
		}
		// TODO: shouldn't be walkSideways, this should be disabled anyway and walkSideways should only take in deltaTime
		if(Gdx.input.isKeyPressed(Input.Keys.R)) {
			player.walkSideways(0, deltaTime, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F)) {
			player.walkSideways(0, -deltaTime, 0);
		}

		/*
		// Zooming
		if(Gdx.input.isKeyPressed(Input.Keys.T)) {
			Settings.FOV -= 30.0f * deltaTime;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.G)) {
			Settings.FOV += 30.0f * deltaTime;
		}
		*/
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
		{
			Gdx.graphics.setDisplayMode(500, 500, false);
			Gdx.app.exit();
		}
	}
	
	public static boolean mouseMoved(int screenX, int screenY, Player player) {
		
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
			player.lookLeftRight(Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dx));
		}
		if(dx > 0) {
			player.lookLeftRight(-Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dx));
		}
		
		if(dy < 0) {
			player.lookUpDown(Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dy));
		}
		if(dy > 0) {
			player.lookUpDown(-Settings.MOUSE_SENSITIVITY * deltaTime * Math.abs(dy));
		}
		
		return false;
	}
}
