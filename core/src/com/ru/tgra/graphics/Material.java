package com.ru.tgra.graphics;

import com.ru.tgra.utils.Color;

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
		ambient = new Color(0.0f, 0.0f, 0.0f);
		diffuse = new Color(0.0f, 0.0f, 0.0f);
		specular = new Color(0.0f, 0.0f, 0.0f);
		emission = new Color(0.0f, 0.0f, 0.0f);
		opacity = 1.0f;
		shininess = 1.0f;
	}
}
