package com.ru.tgra.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.shapes.SpriteGraphic;
import com.ru.tgra.utils.Point3D;

public class Particle
{
	Texture emissionTexture;
	Texture alphaTexture;
	Point3D position;
	
	public Particle (Point3D position, Texture emissionTexture, Texture alphaTexture)
	{
		this.position = position;
		this.emissionTexture = emissionTexture;
		this.alphaTexture = alphaTexture;
	}
	
	public void draw(Shader shader)
	{
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(position.x, position.y, position.z);
		SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
		ModelMatrix.main.popMatrix();
	}
}
