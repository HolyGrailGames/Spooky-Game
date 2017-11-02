package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.graphics.Camera;
import com.ru.tgra.managers.GameManager;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class Player {
	
	private Point3D position;
	private Camera cam;

	public Player(Point3D position, Vector3D direction) {
		this.position = position;
		this.cam = new Camera();
		cam.look(new Point3D(position), new Point3D(direction.x+position.x, position.y, direction.z+position.z), Vector3D.up());
	}

	public void update(float deltaTime) {
		
	}

	public void display() {
		displayCamera();
	}
	
	public void walkSideways(float dx, float dy, float dz) {
		cam.slide(dx, dy, dz);
	}
	
	public void walkForward(float deltaTime) {
		cam.walkForward(deltaTime);
	}
	
	public void lookUpDown(float angle) {
		cam.pitch(angle);
	}
	
	public void lookLeftRight(float angle) {
		cam.rotateY(angle);
	}
	
	private void displayCamera() {
		Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		//Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glScissor(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.perspectiveProjection(Settings.FOV, (float)Gdx.graphics.getWidth() / (float)(Gdx.graphics.getHeight()), 0.2f, Settings.FOG_END);
		GameManager.shader.setViewMatrix(cam.getViewMatrix());
		GameManager.shader.setProjectionMatrix(cam.getProjectionMatrix());
		GameManager.shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
		
		GameManager.shader.setFogStart(Settings.FOG_START);
		GameManager.shader.setFogEnd(Settings.FOG_END);
		GameManager.shader.setFogColor(Settings.FOG_COLOR.r, Settings.FOG_COLOR.g, Settings.FOG_COLOR.b, Settings.FOG_COLOR.a);
		// clear color should be same as fog color
		Gdx.gl.glClearColor(Settings.FOG_COLOR.r, Settings.FOG_COLOR.g, Settings.FOG_COLOR.b, Settings.FOG_COLOR.a);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}
}
