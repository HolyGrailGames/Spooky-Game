package com.ru.tgra.graphics.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.graphics.Shader;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class NewPlaneGraphic {

	private static FloatBuffer vertexBuffer;
	private static FloatBuffer normalBuffer;
	private static FloatBuffer uvBuffer;
	private static ShortBuffer indexBuffer;

	static final int SIZE_PER_SIDE = 128;
	static final float MIN_POSITION = -0.5f;
	static final float POSITION_RANGE = 1f;

	static int indexCount;

	public static void create() {
		final int width = SIZE_PER_SIDE;
		final int height = SIZE_PER_SIDE;

		int arraySize = (width+1) * (height+1) * 3;
		int uvArraySize = (width+1) * (height+1) * 2;
		final float[] vertexArray = new float[arraySize];
		final float[] uvArray = new float[uvArraySize];
		final float[] normalArray = new float[arraySize];

		int offset = 0;
		int normalOffset = 0;
		int uvOffset = 0;

		
		for (int z = 0; z <= height; z++) {
			for (int x = 0; x <= width; x++) {
				final float xRatio = x / (float) (width);

				// Build our heightmap from the top down, so that our triangles are
				// counter-clockwise.
				final float zRatio = 1f - (z / (float) (height));
				
				final float u = x / (float)width;
				final float v = z / (float)height;

				final float xPosition = MIN_POSITION + (xRatio * POSITION_RANGE);
				final float zPosition = MIN_POSITION + (zRatio * POSITION_RANGE);
				
				System.out.println(u + " " + (v-1.0f));

				// Position
				vertexArray[offset++] = xPosition;
				vertexArray[offset++] = 0.0f;
				vertexArray[offset++] = zPosition;
				
				// UV
				uvArray[uvOffset++] = u;
				uvArray[uvOffset++] = v-1.0f;
				
				// Normal
				normalArray[normalOffset++] = 0.0f;
				normalArray[normalOffset++] = 1.0f;
				normalArray[normalOffset++] = 0.0f;
			}
			System.out.println();
		}

		vertexBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(vertexArray, 0, vertexBuffer, arraySize);
		vertexBuffer.rewind();
		
		uvBuffer = BufferUtils.newFloatBuffer(uvArraySize);
		BufferUtils.copy(uvArray, 0, uvBuffer, uvArraySize);
		uvBuffer.rewind();
		
		normalBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(normalArray, 0, normalBuffer, arraySize);
		normalBuffer.rewind();
		
		/*** INDEX ARRAY GENERATION ***/
		final int numStripsRequired = height;
		final int numDegensRequired = 4 * (numStripsRequired);
		final int verticesPerStrip = 2 * width;

		final short[] indexArray = new short[(verticesPerStrip * numStripsRequired) + numDegensRequired];
		
		System.out.println("calc -> " + ((verticesPerStrip * numStripsRequired) + numDegensRequired));

		offset = 0;
		for (int y = 0; y < height; y++) {
			/*
			if (y > 0) {
				// Degenerate begin: repeat first vertex
				indexArray[offset++] = (short) (y * height);
			}
			*/
			indexArray[offset++] = (short) ((y+0) * (width-1) + 0);
			System.out.println(((y+0) * (width-1) + 0));
			if (((y+0) * (width-1) + 0) != 2) {
				System.out.println("first");
			}

			for (int x = 0; x < width+1; x++){
				/*
				// One part of the strip
				indexArray[offset++] = (short) ((y * height) + x);
				indexArray[offset++] = (short) (((y + 1) * height) + x);
				*/
				indexArray[offset++] = (short)((y+0) * (width+1) + x);
				indexArray[offset++] = (short)((y+1) * (width+1) + x);
				
				System.out.println(((y+0) * (width+1) + x));
				if (((y+0) * (width+1) + x) == 2) {
					System.out.println("second");
				}
				System.out.println(((y+1) * (width+1) + x));
				if (((y+1) * (width+1) + x) == 2) {
					System.out.println("third");
				}
			}

			/*
			if (y < height - 2) {
				// Degenerate end: repeat last vertex
				indexArray[offset++] = (short) (((y + 1) * height) + (width - 1));
			}
			*/
			indexArray[offset++] = (short)((y+0) * (width+1) + width+1);
			System.out.println(((y+0) * (width+1) + width+1));
			if (((y+0) * (width+1) + width+1) == 2) {
				System.out.println("fourth");
			}
		}
		
		System.out.println("offset -> " + offset);

		indexCount = indexArray.length;

		indexBuffer = BufferUtils.newShortBuffer(indexCount);
		BufferUtils.copy(indexArray, 0, indexBuffer, indexCount);
		indexBuffer.rewind();
	}

	public static void drawSolidPlane(Shader shader, Texture diffuseTexture, Texture alphaTexture) {
		shader.setDiffuseTexture(diffuseTexture);
		shader.setAlphaTexture(alphaTexture);
		
		/*
		Gdx.gl.glEnable(GL20.GL_CULL_FACE); // cull face
		Gdx.gl.glCullFace(GL20.GL_BACK); // cull back face
		Gdx.gl.glFrontFace(GL20.GL_CCW); // GL_CCW for counter clock-wise
		*/

		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);

		Gdx.gl.glDrawElements(GL20.GL_TRIANGLE_STRIP, indexCount, GL20.GL_UNSIGNED_SHORT, indexBuffer);
	}
	
	public static void drawOutlinePlane(Shader shader, Texture diffuseTexture, Texture alphaTexture) {
		shader.setDiffuseTexture(diffuseTexture);
		shader.setAlphaTexture(alphaTexture);
		
		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
		
		Gdx.gl.glLineWidth(5f);

		Gdx.gl.glDrawElements(GL20.GL_LINE_STRIP, indexCount, GL20.GL_UNSIGNED_SHORT, indexBuffer);
	}
}