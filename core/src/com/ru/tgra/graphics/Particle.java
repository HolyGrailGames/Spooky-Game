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
	
	
	float rotationAngle;
	float size;
	float timeToLive; 
	float lifeTime;
	float fadeInTime;
	float fadeOutTime;
	float maxAlpha;
	float alpha;
	
	public Particle (Point3D position, Vector3D speed, float size, float timeToLive,  float fadeInTime, float fadeOutTime, float maxAlpha, Texture emissionTexture, Texture alphaTexture)
	{
		this.position = position;
		this.speed = speed;
		this.size = size;
		this.emissionTexture = emissionTexture;
		this.alphaTexture = alphaTexture;
		
		
		this.alpha = 0.0f;
		this.fadeInTime = fadeInTime;
		this.fadeOutTime = fadeOutTime;
		this.maxAlpha = maxAlpha;
		this.timeToLive = timeToLive;
		this.lifeTime = 0.0f;
		
	}
	
	public void update(float deltaTime)
	{
		position.add(Vector3D.scale(speed,  deltaTime));
		
		if (lifeTime < fadeInTime)
		{
			alpha = (lifeTime / fadeInTime) * maxAlpha;
		}
		else if (lifeTime > (timeToLive - fadeOutTime))
		{
			 alpha = ((lifeTime - (timeToLive - fadeOutTime)) / (fadeOutTime)) * maxAlpha;
		}
		else {
			alpha = maxAlpha;
		}
		
		lifeTime += deltaTime;
	}
	
	public void draw(Shader shader)
	{
		shader.setMaterialDiffuse(0,  0,  0,  alpha);
		
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTranslation(position.x, position.y, position.z);
		
		ModelMatrix.main.addScale(size,  size,  size);
		
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
		
		ModelMatrix.main.addRotationY(90.0f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);

		ModelMatrix.main.addRotationX(90.0f);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SpriteGraphic.drawSprite(shader, emissionTexture, alphaTexture);
		
		
		
		ModelMatrix.main.popMatrix();
	}
}
