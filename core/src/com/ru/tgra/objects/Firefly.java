package com.ru.tgra.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.SpriteGraphic;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class Firefly
{
	Texture emissionTexture;
	Texture alphaTexture;
	Point3D position;
	
	
	float rotationAngle;
	float size;
	float lifeTime;
	float alpha;
	
	public Firefly (Point3D position, float size, Texture emissionTexture, Texture alphaTexture)
	{
		this.position = position;
		this.size = size;
		this.emissionTexture = emissionTexture;
		this.alphaTexture = alphaTexture;
		
		this.alpha = 0.4f;
	}
	
	public void update(float deltaTime)
	{

	}
	
	public void draw(Shader shader)
	{
		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		
		shader.setMaterialDiffuse(0,  0,  0,  alpha);
		
		//ModelMatrix.main.loadIdentityMatrix();
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

		Gdx.gl.glDisable(GL20.GL_BLEND);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
	}
	
	public Point3D getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Point3D pos)
	{
		this.position.x = pos.x;
		this.position.y = pos.y;
		this.position.z = pos.z;
	}
}
