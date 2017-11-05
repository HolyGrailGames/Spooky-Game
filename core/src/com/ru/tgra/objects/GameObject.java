package com.ru.tgra.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;


public abstract class GameObject {
	protected Point3D position;
	protected Vector3D scale;
	protected Vector3D direction;
	protected Material material;
	protected Texture tex;

	public GameObject(Point3D position, Vector3D direction, Vector3D scale, Material material, Texture tex) {
		this.position = position;
		this.scale = scale;
		this.direction = direction;
		this.material = material;
		this.tex = tex;
	}

	public GameObject(Point3D position, Vector3D direction, Vector3D scale) {
		this.position = position;
		this.scale = scale;
		this.direction = direction;
		this.material = null;
		this.tex = null;
	}

	public GameObject(Point3D position, Vector3D direction) {
		this.position = position;
		this.direction = direction;
		this.scale = new Vector3D(1,1,1);
		this.material = null;
		this.tex = null;
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

	public void setDirection(Vector3D direction) {
		this.direction = direction;
	}

	public Vector3D getDirection() {
		return this.direction;
	}

	public void translate(float dx, float dy, float dz) {
		position.translate(dx, dy, dz);
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Texture getTex() {
		return tex;
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}

	/*
	 * METHODS THAT NEED TO BE IMPLEMENTED BY SUBCLASSES:
	 */
	public abstract void update(float deltaTime);
	public abstract void display(Shader shader);
}
