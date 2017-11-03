package com.ru.tgra.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;


public abstract class GameObject {
	protected Point3D position;
	protected Vector3D scale;
	protected Vector3D rotation;
	protected Color color;
	protected Texture tex;
	
	public GameObject(Point3D position, Vector3D scale, Vector3D rotation, Color color, Texture tex) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		this.color = color;
		this.tex = tex;
	}
	
	public void setPosition(Point3D position) {
		this.position = position;
	}
	
	public Point3D getPosition() {
		return this.position;
	}
	
	public void setScale(Vector3D scale) {
		this.scale = scale;
	}
	
	public Vector3D getScale() {
		return this.scale;
	}
	
	public void setRotation(Vector3D rotation) {
		this.rotation = rotation;
	}
	
	public Vector3D getRotation() {
		return this.rotation;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void translate(float dx, float dy, float dz) {
		position.translate(dx, dy, dz);
	}
	
	/*
	 * METHODS THAT NEED TO BE IMPLEMENTED BY SUBCLASSES:
	 */
	public abstract void update(float deltaTime);
	public abstract void display(Shader shader);
}
