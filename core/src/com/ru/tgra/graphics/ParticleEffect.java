package com.ru.tgra.graphics;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class ParticleEffect
{
	private Point3D position; 
	private Queue<Particle> particles;
	private float leftOverTime;
	private float particleInterval;
	

	float size;
	float timeToLive;
	float fadeInTime;
	float fadeOutTime;
	float maxAlpha;
	
	private int maxParticleCount;
	
	private Texture emissionTexture;
	private Texture alphaTexture;
	
	private Random random;
	
	public ParticleEffect(Point3D position, float rate, float particleLifetime, Texture emissionTexture, Texture alphaTexture)
	{
		this.position = position;
		this.particles = new LinkedList<Particle>();
		this.particleInterval = 1.0f / rate;
		this.maxParticleCount = (int)(particleLifetime * rate);
		this.leftOverTime = 0.0f;
		this.emissionTexture = emissionTexture;
		this.alphaTexture = alphaTexture;
		
		this.random = new Random();
	}
	
	public void update(float deltaTime)
	{
		for (Particle particle : particles)
		{
			particle.update(deltaTime);
		}
		
		 deltaTime += leftOverTime;
		 while (deltaTime >= particleInterval) {
			 Vector3D particleSpeed = new Vector3D(random.nextFloat() - 0.5f, random.nextFloat() + 0.5f, random.nextFloat() - 0.5f);
			 Particle particle = new Particle (new Point3D(position.x, position.y, position.z), particleSpeed, 0.2f, 1.0f, 0.1f, 0.4f, 0.4f, emissionTexture, alphaTexture);
			 particle.update(deltaTime);
			 particles.add(particle);
			 
			 deltaTime -= particleInterval;
		 }
		 leftOverTime = deltaTime;
		 
		for (int particleCount = particles.size(); particleCount > maxParticleCount; particleCount--)
		{
			particles.remove();
		}
	}
	
	public void draw(Shader shader)
	{
		Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		
		
		for (Particle particle : particles)
		{
			particle.draw(shader);
		}

		Gdx.gl.glDisable(GL20.GL_BLEND);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
	}
}
