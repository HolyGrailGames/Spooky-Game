package com.ru.tgra.graphics.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.utils.Vector3D;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class HeightMapGraphic {

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
		final float[] normalArray = new float[arraySize];

		int offsetPos = 0;
		int offsetNor = 0;

		/*** VERTEX ARRAY GENERATION ***/
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				final float xRatio = x / (float) (width - 1);

				// Build our heightmap from the top down, so that our triangles are
				// counter-clockwise.
				final float yRatio = 1f - (y / (float) (width - 1));

				final float xPosition = MIN_POSITION + (xRatio * POSITION_RANGE);
				final float yPosition = MIN_POSITION + (yRatio * POSITION_RANGE);

				// Cheap normal using a derivative of the function.
				// The slope for X will be 2X, for Y will be 2Y.
				// Divide by 10 since the position's Z is also divided by 10.
				final float xSlope = (2 * xPosition) / 10f;
				final float ySlope = (2 * yPosition) / 10f;

				// Calculate the normal using the cross product of the slopes.
				final float[] planeVectorX = {1f, 0f, xSlope};
				final float[] planeVectorY = {0f, 1f, ySlope};
				final float[] normalVector = {
						(planeVectorX[1] * planeVectorY[2]) - (planeVectorX[2] * planeVectorY[1]),
						(planeVectorX[2] * planeVectorY[0]) - (planeVectorX[0] * planeVectorY[2]),
						(planeVectorX[0] * planeVectorY[1]) - (planeVectorX[1] * planeVectorY[0])};

				// Normalize the normal
				final float length = Vector3D.length(new Vector3D(normalVector[0], normalVector[1], normalVector[2]));

				// Position
				vertexArray[offsetPos++] = xPosition;
				vertexArray[offsetPos++] = yPosition;
				vertexArray[offsetPos++] = ((xPosition * xPosition) + (yPosition * yPosition)) / 10f;

				// Normals
				normalArray[offsetNor++] = normalVector[0] / length;
				normalArray[offsetNor++] = normalVector[1] / length;
				normalArray[offsetNor++] = normalVector[2] / length;

				/*
				normalArray[offset++] = 0.0f;
				normalArray[offset++] = 1.0f;
				normalArray[offset++] = 0.0f;
				*/
			}
		}
		vertexBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(vertexArray, 0, vertexBuffer, arraySize);
		vertexBuffer.rewind();

		normalBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(normalArray, 0, normalBuffer, arraySize);
		normalBuffer.rewind();

		/*** INDEX ARRAY GENERATION ***/
		final int numStripsRequired = height - 1;
		final int numDegensRequired = 2 * (numStripsRequired - 1);
		final int verticesPerStrip = 2 * width;

		final short[] indexArray = new short[(verticesPerStrip * numStripsRequired) + numDegensRequired];

		int offset = 0;
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

	public static void drawSolidHeightMap(Shader shader) {
		shader.setTextures(null,  null,  null);

		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		//Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);

		Gdx.gl.glDrawElements(GL20.GL_TRIANGLE_STRIP, indexCount, GL20.GL_UNSIGNED_SHORT, indexBuffer);
	}
}