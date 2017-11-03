package com.ru.tgra.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.shapes.SpriteGraphic;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class Particle
{
	Texture emissionTexture;
	Texture alphaTexture;
	Point3D position;
	
	Vector3D speed;
	
	public Particle (Point3D position, Vector3D speed, Texture emissionTexture, Texture alphaTexture)
	{
		this.position = position;
		this.speed = speed;
		this.emissionTexture = emissionTexture;
		this.alphaTexture = alphaTexture;
	}
	
	public void update(float deltaTime)
	{
		position.add(Vector3D.scale(speed,  deltaTime));
	}
	
	public void draw(Shader shader)
	{
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(position.x, position.y, position.z);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
		ModelMatrix.main.popMatrix();
	}
}
