package com.ru.tgra.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.utils.OpenSimplexNoise;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Utilities;
import com.ru.tgra.utils.Vector3D;

public class Terrain {

	private Point3D position;
	private List<Tile> tiles;
	public static float[][] yValues;
	private int size;
	private int tileEdgeCount;
	public static int totalSize;
	private OpenSimplexNoise noise;
	private Texture tex;
	
	// Size is the number of tiles
	// tileEdgeCount is the number of edges on one tile
	public Terrain(Point3D position, int size, int tileEdgeCount, Texture tex) {
		this.position = position;
		this.size = size;
		this.tileEdgeCount = tileEdgeCount;
		this.totalSize = (size*tileEdgeCount)+1;
		yValues = new float[totalSize][totalSize];
		this.tiles = new ArrayList<Tile>();
		this.noise = new OpenSimplexNoise(System.currentTimeMillis());
		this.tex = tex;
		
		generateNoise();
		create();
	}
	
	private void generateNoise() {
		float zOff = 0;
		for(int z = 0; z < totalSize; z++) {
			float xOff = 0;
			for(int x = 0; x < totalSize; x++) {
				yValues[x][z] = Utilities.map((float)noise.eval(xOff, zOff), -1, 1, Settings.TERRAIN_MIN_HEIGHT, Settings.TERRAIN_MAX_HEIGHT);
				xOff+=Settings.TERRAIN_STEEPNESS;
			}
			zOff+=Settings.TERRAIN_STEEPNESS;
		}
	}
	
	private void create() {
		for(int z = 0; z < size; z++) {
			for(int x = 0; x < size; x++) {
				
				float xPos = x-((size-1)/2.0f)+position.x;
				float zPos = ((size-1)/2.0f)+position.z-z;
				
				tiles.add(new Tile(
						x,z, tileEdgeCount,
						new Point3D(xPos, 0, zPos),
						Settings.TEST_MATERIAL, 
						tex));
			}
		}
	}
	
	public void display(Shader shader) {
		ModelMatrix.main.pushMatrix();
		float s = Settings.TERRAIN_SCALE;
		ModelMatrix.main.addScale(s, 1, s);
		for(Tile tile : tiles) {
			tile.display(shader);
		}
		ModelMatrix.main.popMatrix();
	}
}
