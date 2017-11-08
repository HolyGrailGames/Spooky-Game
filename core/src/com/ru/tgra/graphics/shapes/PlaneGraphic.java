package com.ru.tgra.graphics.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.game.SpookyGame;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.noise.OpenSimplexNoise;
import com.ru.tgra.objects.Terrain;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Utilities;
import com.ru.tgra.utils.Vector3D;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

public class PlaneGraphic {

	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	private static FloatBuffer uvBuffer;
	private static ShortBuffer indexBuffer;

	int SIZE_PER_SIDE; // MAX allowed value is 256
	static final float MIN_POSITION = -0.5f;
	static final float POSITION_RANGE = 1f;

	static int indexCount;
	
	static float minHeight;
	static float maxHeight;
	
	public PlaneGraphic(int tileX, int tileZ, int edgeCount, float minHeight, float maxHeight) {
		PlaneGraphic.minHeight = minHeight;
		PlaneGraphic.maxHeight = maxHeight;
		SIZE_PER_SIDE = edgeCount;
		create(tileX, tileZ);
	}

	public void create(int tileX, int tileZ) {
		final int width = SIZE_PER_SIDE;
		final int height = SIZE_PER_SIDE;

		int colorArraySize = (width+1) * (height+1) * 3;
		int arraySize = (width+1) * (height+1) * 3;
		int uvArraySize = (width+1) * (height+1) * 2;
		final float[] vertexArray = new float[arraySize];
		final float[] uvArray = new float[uvArraySize];
		final float[] normalArray = new float[arraySize];
		final float[] colorArray = new float[colorArraySize];

		int offset = 0;
		int normalOffset = 0;
		int uvOffset = 0;
		int colorOffset = 0;
		
		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;

		boolean bla = true;
		for (int z = 0; z <= height; z++) {
			for (int x = 0; x <= width; x++) {
				float xRatio = x / (float) (width);
				// Build our plane from the top down, so that our triangles are
				// counter-clockwise.
				float zRatio = 1f - (z / (float) (height));
				
				final float u = x / (float)width;
				final float v = z / (float)height;

				final float xPosition = MIN_POSITION + (xRatio * POSITION_RANGE);
				final float yPosition = Terrain.yValues[(width*tileX)+x][(height*tileZ)+z];
				final float zPosition = MIN_POSITION + (zRatio * POSITION_RANGE);
				
				// Position
				vertexArray[offset++] = xPosition;
				vertexArray[offset++] = yPosition;
				vertexArray[offset++] = zPosition;
				
				// UV
				uvArray[uvOffset++] = u;
				uvArray[uvOffset++] = 1-v;
				
				// Normal
				Vector3D normal = calculateNormal((width*tileX)+x, (height*tileZ)+z);
				normalArray[normalOffset++] = normal.x;
				normalArray[normalOffset++] = normal.y;
				normalArray[normalOffset++] = normal.z;
				
				// Color
				
				float xCol = 1.0f;
				float yCol = 1.0f;
				float zCol = 1.0f;
				
				float yRatio = Utilities.map(yPosition, minHeight, maxHeight, 0.0f, 1.0f);
				//System.out.println(yPosition);
				
				if (bla) {
					System.out.println("My y pos: " + yPosition);
					bla = false;
				}
				
				if(yPosition < min) {
					min = yPosition;
				}
				if(yPosition > max) {
					//System.out.println("sdf");
					max = yPosition;
				}
				
				// Water
				if (yRatio <= Settings.WATER_LEVEL) {
					xCol = 0.24f;
					yCol = 0.43f;
					zCol = 0.84f;
					/*
					if (yRatio > Settings.WATER_LEVEL-0.0001) {
						System.out.println("\nyRatio: " + yRatio);
						System.out.println("yPos: " + yPosition);
					}*/
				}
				// Dirt
				else if (yRatio > Settings.WATER_LEVEL && yRatio <= Settings.DIRT_LEVEL) {
					xCol = 0.68f;
					yCol = 0.54f;
					zCol = 0.29f;
				}
				// Grass
				else if (yRatio > Settings.DIRT_LEVEL && yRatio <= Settings.GRASS_LEVEL) {
					xCol = 0.50f;
					yCol = 0.69f;
					zCol = 0.25f;
				}
				// Rocks
				else if (yRatio > Settings.GRASS_LEVEL && yRatio <= Settings.ROCK_LEVEL) {
					xCol = 0.57f;
					yCol = 0.55f;
					zCol = 0.52f;
				}
				// Snow
				else {
					xCol = 0.9f;
					yCol = 0.9f;
					zCol = 0.9f;
				}
				colorArray[colorOffset++] = xCol;
				colorArray[colorOffset++] = yCol;
				colorArray[colorOffset++] = zCol;
				//colorArray[colorOffset++] = 1.0f;
			}
		}
		
		//System.out.println("min: " + min);
		//System.out.println("max: " + max);

		vertexBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(vertexArray, 0, vertexBuffer, arraySize);
		vertexBuffer.rewind();
		
		normalBuffer = BufferUtils.newFloatBuffer(arraySize);
		BufferUtils.copy(normalArray, 0, normalBuffer, arraySize);
		normalBuffer.rewind();
		
		colorBuffer = BufferUtils.newFloatBuffer(colorArraySize);
		BufferUtils.copy(colorArray, 0, colorBuffer, colorArraySize);
		colorBuffer.rewind();
		
		uvBuffer = BufferUtils.newFloatBuffer(uvArraySize);
		BufferUtils.copy(uvArray, 0, uvBuffer, uvArraySize);
		uvBuffer.rewind();
		
		/*** INDEX ARRAY GENERATION ***/
		final int numStripsRequired = height;
		final int numDegensRequired = 4 * (numStripsRequired);
		final int verticesPerStrip = 2 * width;

		final short[] indexArray = new short[(verticesPerStrip * numStripsRequired) + numDegensRequired];

		offset = 0;
		for (int z = 0; z < height; z++) {
			// Degenerate first index
			indexArray[offset++] = (short) ((z) * (width) + z);
			for (int x = 0; x < width+1; x++){
				// One part of the strip
				indexArray[offset++] = (short)((z+0) * (width+1) + x);
				indexArray[offset++] = (short)((z+1) * (width+1) + x);
			}
			// Degenerate last index
			indexArray[offset++] = (short)((z+1) * (width+1) + (width));
		}
		
		indexCount = indexArray.length;

		indexBuffer = BufferUtils.newShortBuffer(indexCount);
		BufferUtils.copy(indexArray, 0, indexBuffer, indexCount);
		indexBuffer.rewind();
	}
	
	private Vector3D calculateNormal(int x, int z) {
		if (x < 1 || x >= Terrain.totalSize-1  || z < 1 || z >= Terrain.totalSize-1) {
			return Vector3D.up();
		}
		float heightL = Terrain.yValues[x-1][z];
		float heightR = Terrain.yValues[x+1][z];
		float heightU = Terrain.yValues[x][z-1];
		float heightD = Terrain.yValues[x][z+1];
		Vector3D normal = new Vector3D(heightL-heightR, 2.0f, heightD-heightU);
		normal.normalize();
		return normal;
	}

	public void drawSolidPlane(Shader shader, Texture diffuseTexture, Texture alphaTexture) {
		shader.setTextures(diffuseTexture, alphaTexture, null);
		/*
		Gdx.gl.glEnable(GL20.GL_CULL_FACE); // cull face
		Gdx.gl.glCullFace(GL20.GL_BACK); // cull back face
		Gdx.gl.glFrontFace(GL20.GL_CCW); // GL_CCW for counter clock-wise
		*/
		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getColorPointer(), 3, GL20.GL_FLOAT, false, 0, colorBuffer);

		Gdx.gl.glDrawElements(GL20.GL_TRIANGLE_STRIP, indexCount, GL20.GL_UNSIGNED_SHORT, indexBuffer);
	}
	
	public void drawOutlinePlane(Shader shader, Texture diffuseTexture, Texture alphaTexture) {
		shader.setTextures(diffuseTexture, alphaTexture, null);
		
		Gdx.gl.glVertexAttribPointer(shader.getVertexPointer(), 3, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getNormalPointer(), 3, GL20.GL_FLOAT, false, 0, normalBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getUVPointer(), 2, GL20.GL_FLOAT, false, 0, uvBuffer);
		Gdx.gl.glVertexAttribPointer(shader.getColorPointer(), 3, GL20.GL_FLOAT, false, 0, colorBuffer);
		
		//Gdx.gl.glLineWidth(5f);
		
		Gdx.gl.glDrawElements(GL20.GL_LINES, indexCount, GL20.GL_UNSIGNED_SHORT, indexBuffer);
	}
}