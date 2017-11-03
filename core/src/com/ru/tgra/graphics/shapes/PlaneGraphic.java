package com.ru.tgra.graphics.shapes;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.graphics.Shader;

public class PlaneGraphic {

	private static FloatBuffer vertexBuffer;
	private static FloatBuffer normalBuffer;
	private static FloatBuffer uvBuffer;
	private static ShortBuffer indexBuffer;

	public static void create() {

		//VERTEX ARRAY IS FILLED HERE
		float[] vertexArray = {
				-0.5f, 0.5f, -0.5f,
				0.5f, 0.5f, -0.5f,
				0.5f, 0.5f, 0.5f,
				-0.5f, 0.5f, 0.5f};
						
		vertexBuffer = BufferUtils.newFloatBuffer(12);
		BufferUtils.copy(vertexArray, 0, vertexBuffer, 12);
		vertexBuffer.rewind();

		//NORMAL ARRAY IS FILLED HERE
		float[] normalArray = {
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 0.0f};

		normalBuffer = BufferUtils.newFloatBuffer(12);
		BufferUtils.copy(normalArray, 0, normalBuffer, 12);
		normalBuffer.rewind();

		//UV TEXTURE COORD ARRAY IS FILLED HERE
		float[] uvArray = {
				0.0f, 1.0f,
				1.0f, 1.0f,
				1.0f, 0.0f,
				0.0f, 0.0f};

		uvBuffer = BufferUtils.newFloatBuffer(8);
		BufferUtils.copy(uvArray, 0, uvBuffer, 8);
		uvBuffer.rewind();

		//INDEX ARRAY IS FILLED HERE
		short[] indexArray = {0, 1, 2, 3};

		indexBuffer = BufferUtils.newShortBuffer(4);
		BufferUtils.copy(indexArray, 0, indexBuffer, 4);
		indexBuffer.rewind();
	}

	public static void drawSolidCube(Shader shader, Texture diffuseTexture, Texture alphaTexture) {

		shader.setDiffuseTexture(diffuseTexture);
		shader.setAlphaTexture(alphaTexture);

		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);

		Gdx.gl.glDrawElements(GL20.GL_TRIANGLE_FAN, 4, GL20.GL_UNSIGNED_SHORT, indexBuffer);
	}

}
