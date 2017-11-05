package com.ru.tgra.graphics;

import com.badlogic.gdx.graphics.Color;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class Light {
	//private int index;
	public Point3D position;
	public Vector3D direction;
	public Color color;

	public float spotExponent;
	public float constantAttenuation;
	public float linearAttenuation;
	public float quadraticAttenuation;

	public Light(/*int id, */Point3D position, Vector3D direction, Color color, float spotExponent, float constantAttenuation, float linearAttenuation, float quadraticAttenuation) {
		//this.index = id;
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.spotExponent = spotExponent;
		this.constantAttenuation = constantAttenuation;
		this.linearAttenuation = linearAttenuation;
		this.quadraticAttenuation = quadraticAttenuation;
	}
	/*
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	*/
	public Point3D getPosition() {
		return position;
	}

	public void setPosition(Point3D position) {
		this.position = position;
	}

	public Vector3D getDirection() {
		return direction;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getSpotExponent() {
		return spotExponent;
	}

	public void setSpotExponent(float spotExponent) {
		this.spotExponent = spotExponent;
	}

	public float getConstantAttenuation() {
		return constantAttenuation;
	}

	public void setConstantAttenuation(float constantAttenuation) {
		this.constantAttenuation = constantAttenuation;
	}

	public float getLinearAttenuation() {
		return linearAttenuation;
	}

	public void setLinearAttenuation(float linearAttenuation) {
		this.linearAttenuation = linearAttenuation;
	}

	public float getQuadraticAttenuation() {
		return quadraticAttenuation;
	}

	public void setQuadraticAttenuation(float quadraticAttenuation) {
		this.quadraticAttenuation = quadraticAttenuation;
	}

	public void display(Shader shader) {
		shader.setLight(this);
	}
}
