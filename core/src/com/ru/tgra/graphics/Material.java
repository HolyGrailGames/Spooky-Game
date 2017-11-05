package com.ru.tgra.graphics;

import com.badlogic.gdx.graphics.Color;

public class Material {
	public String id;
	public Color ambient;
	public Color diffuse;
	public Color specular;
	public Color emission;
	public float opacity;
	public float shininess;

	public Material()
	{
		ambient = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		diffuse = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		specular = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		emission = new Color(0.0f, 0.0f, 0.0f, 1.0f);
		opacity = 1.0f;
		shininess = 1.0f;
	}

	public Material(Color diffuse, Color specular, Color emission, float shininess)
	{
		this.ambient = new Color(0.0f, 0.0f, 0.0f, 1.0f); // TODO: make constructor that takes ambient
		this.diffuse = diffuse;
		this.specular = specular;
		this.emission = emission;
		this.opacity = 1.0f; // TODO: make constructor that takes opacity
		this.shininess = shininess;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Color getAmbient() {
		return ambient;
	}

	public void setAmbient(Color ambient) {
		this.ambient = ambient;
	}

	public Color getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Color diffuse) {
		this.diffuse = diffuse;
	}

	public Color getSpecular() {
		return specular;
	}

	public void setSpecular(Color specular) {
		this.specular = specular;
	}

	public Color getEmission() {
		return emission;
	}

	public void setEmission(Color emission) {
		this.emission = emission;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public float getShininess() {
		return shininess;
	}

	public void setShininess(float shininess) {
		this.shininess = shininess;
	}
}
