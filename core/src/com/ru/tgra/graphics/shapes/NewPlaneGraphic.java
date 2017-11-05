package com.ru.tgra.graphics.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.graphics.Shader;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class NewPlaneGraphic {

	private static FloatBuffer vertexBuffer;
	private static FloatBuffer normalBuffer;
	//private static FloatBuffer uvBuffer;
	private static ShortBuffer indexBuffer;

	static final int SIZE_PER_SIDE = 32;
	static final float MIN_POSITION = -0.5f;
	static final float POSITION_RANGE = 1f;

	static int indexCount;

	public static void create() {
		final int width = SIZE_PER_SIDE;
		final int height = SIZE_PER_SIDE;

		int arraySize = width * height * 3;
		final float[] vertexArray = new float[arraySize];

		int offset = 0;

		/*** VERTEX ARRAY GENERATION ***/
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final float xRatio = x / (float) (width - 1);

				// Build our heightmap from the top down, so that our triangles are
				// counter-clockwise.
				final float zRatio = 1f - (y / (float) (width - 1));

				final float xPosition = MIN_POSITION + (xRatio * POSITION_RANGE);
				final float zPosition = MIN_POSITION + (zRatio * POSITION_RANGE);

				// Position
				vertexArray[offset++] = xPosition;
				vertexArray[offset++] = 0.0f;
				vertexArray[offset++] = zPosition;
			}
		}

		vertexBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(vertexArray, 0, vertexBuffer, arraySize);
		vertexBuffer.rewind();

		/*** NORMAL ARRAY GENERATION ***/
		final float[] normalArray = new float[arraySize];

		offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				normalArray[offset++] = 0.0f;
				normalArray[offset++] = 1.0f;
				normalArray[offset++] = 0.0f;
			}
		}

		normalBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(normalArray, 0, normalBuffer, arraySize);
		normalBuffer.rewind();

		/*** INDEX ARRAY GENERATION ***/
		final int numStripsRequired = height - 1;
		final int numDegensRequired = 2 * (numStripsRequired - 1);
		final int verticesPerStrip = 2 * width;

		final short[] indexArray = new short[(verticesPerStrip * numStripsRequired) + numDegensRequired];

		System.out.println((verticesPerStrip * numStripsRequired) + numDegensRequired);
		offset = 0;
		for (int y = 0; y < height - 1; y++) {
			if (y > 0) {
				// Degenerate begin: repeat first vertex
				indexArray[offset++] = (short) (y * height);
			}

			for (int x = 0; x < width; x++){
				// One part of the strip
				indexArray[offset++] = (short) ((y * height) + x);
				indexArray[offset++] = (short) (((y + 1) * height) + x);
			}

			if (y < height - 2) {
				// Degenerate end: repeat last vertex
				indexArray[offset++] = (short) (((y + 1) * height) + (width - 1));
			}
		}

		indexCount = indexArray.length;

		indexBuffer = BufferUtils.newShortBuffer(indexCount);
		BufferUtils.copy(indexArray, 0, indexBuffer, indexCount);
		indexBuffer.rewind();
	}

	public static void drawSolidPlane(Shader shader) {
		shader.setDiffuseTexture(null);
		shader.setAlphaTexture(null);

		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		//Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);

		Gdx.gl.glDrawElements(GL20.GL_TRIANGLE_STRIP, indexCount, GL20.GL_UNSIGNED_SHORT, indexBuffer);
	}
}