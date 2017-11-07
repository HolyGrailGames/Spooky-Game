package com.ru.tgra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.ru.tgra.graphics.Camera;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.BoxGraphic;
import com.ru.tgra.objects.Flashlight;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class Player {

	public Point3D position;
	public Vector3D direction;

	public Camera cam;
	private Vector3D velocity;
	public float pitch;
	public float yaw;
	private Flashlight flashlight;
	
	private Point3D startPos;
	private float totalYaw = 0;

	public Player(Point3D position, Vector3D direction) {
		this.position = position;
		this.direction = direction;
		this.cam = new Camera(position);
		this.velocity = new Vector3D();
		this.pitch = 0;
		this.yaw = 0;
		this.flashlight = new Flashlight(position, direction);

		cam.look(position, new Point3D(direction.x+position.x, direction.y+position.y, direction.z+position.z), Vector3D.up());
	}

	public void update(float deltaTime) {
		cam.slide(velocity.x, velocity.y, 0);
		cam.walkForward(velocity.z);
		cam.rotateY(yaw*deltaTime*Settings.MOUSE_SENSITIVITY);
		cam.pitch(pitch*deltaTime*Settings.MOUSE_SENSITIVITY);

		direction.set(-cam.n.x,-cam.n.y,-cam.n.z);

		velocity.zero();
		pitch = 0;
		yaw = 0;
	}

	public void display(Shader shader) {
		flashlight.display(shader);
	}

	public void lookUpDown(float angle) {
		cam.pitch(angle);
		direction.set(-cam.n.x,-cam.n.y,-cam.n.z);
	}

	public void lookLeftRight(float angle) {
		cam.rotateY(angle);
		direction.set(-cam.n.x,-cam.n.y,-cam.n.z);
	}

	public void walkLeft(float deltaTime) {
		velocity.x = -deltaTime * Settings.WALK_SPEED;
	}

	public void walkRight(float deltaTime) {
		velocity.x = deltaTime * Settings.WALK_SPEED;
	}

	public void walkForward(float deltaTime) {
		velocity.z = deltaTime * Settings.WALK_SPEED;
	}

	public void walkBackwards(float deltaTime) {
		velocity.z = -deltaTime * Settings.WALK_SPEED;
	}

	public void flyUp(float deltaTime) {
		velocity.y = deltaTime * Settings.WALK_SPEED;
	}

	public void flyDown(float deltaTime) {
		velocity.y = -deltaTime * Settings.WALK_SPEED;
	}

	public void pitch(float angle) {
		this.pitch = -angle;
	}

	public void yaw(float angle) {
		this.yaw = -angle;
	}
	
	public void toggleFlashlight() {
		flashlight.toggle();
	}
}
