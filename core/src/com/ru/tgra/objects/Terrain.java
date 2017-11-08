package com.ru.tgra.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.noise.DiamondSquare;
import com.ru.tgra.noise.NoiseAlgorithm;
import com.ru.tgra.noise.OpenSimplexNoise;
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
	private DiamondSquare dSq;
	private Texture tex;
	private NoiseAlgorithm alg;
	private float minHeight;
	private float maxHeight;
	private Material material;
	
	// Size is the number of tiles
	// tileEdgeCount is the number of edges on one tile
	public Terrain(Point3D position, Texture tex, Material material, NoiseAlgorithm alg, boolean flat) {
		this.position = position;
		this.tex = tex;
		this.tiles = new ArrayList<Tile>();
		this.alg = alg;
		this.material = material;
		
		if (alg == NoiseAlgorithm.OPEN_SIMPLEX_NOISE) {
			System.out.println("Generating Terrain with Open Simplex Noise...");
			
			this.size = Settings.TERRAIN_SIMPLEX_SIZE;
			this.tileEdgeCount = Settings.TERRAIN_SIMPLEX_TILE_SIZE;
			this.minHeight = Settings.TERRAIN_SIMPLEX_MIN_HEIGHT;
			this.maxHeight = Settings.TERRAIN_SIMPLEX_MAX_HEIGHT;
			
			totalSize = (Settings.TERRAIN_SIMPLEX_SIZE*Settings.TERRAIN_SIMPLEX_TILE_SIZE)+1;
			
			this.noise = new OpenSimplexNoise(System.currentTimeMillis());
			if (!flat) {
				generateNoise();
			}
			else {
				generateFlatYValues();
			}
			
		}
		else if (alg == NoiseAlgorithm.DIAMOND_SQUARE) {
			System.out.println("Generating Terrain with Diamond Square...");
			
			this.size = Settings.TERRAIN_DSQUARE_SIZE;
			this.tileEdgeCount = Settings.TERRAIN_DSQUARE_TILE_SIZE;
			totalSize = (Settings.TERRAIN_DSQUARE_SIZE*Settings.TERRAIN_DSQUARE_TILE_SIZE)+1;
			this.minHeight = -Settings.TERRAIN_DSQUARE_RANGE+Settings.TERRAIN_DSQUARE_CORNER_HEIGHT;
			this.maxHeight = Settings.TERRAIN_DSQUARE_RANGE+Settings.TERRAIN_DSQUARE_CORNER_HEIGHT;
			
			this.dSq = new DiamondSquare((Settings.TERRAIN_DSQUARE_SIZE*Settings.TERRAIN_DSQUARE_TILE_SIZE)+1,
					Settings.TERRAIN_DSQUARE_CORNER_HEIGHT, Settings.TERRAIN_DSQUARE_RANGE,
					System.currentTimeMillis());
			if (!flat) {
				generateDiamondSquareNoise();
			} else {
				generateFlatYValues();
			}
			
		}
		
		create();
	}
	
	private void generateNoise() {
		yValues = new float[totalSize][totalSize];
		float zOff = 0;
		for(int z = 0; z < totalSize; z++) {
			float xOff = 0;
			for(int x = 0; x < totalSize; x++) {
				yValues[x][z] = Utilities.map((float)noise.eval(xOff, zOff), -1, 1, Settings.TERRAIN_SIMPLEX_MIN_HEIGHT, Settings.TERRAIN_SIMPLEX_MAX_HEIGHT);
				xOff+=Settings.TERRAIN_SIMPLEX_STEEPNESS;
			}
			zOff+=Settings.TERRAIN_SIMPLEX_STEEPNESS;
		}
	}
	
	private void generateFlatYValues() {
		yValues = new float[totalSize][totalSize];
		float height = minHeight + ((maxHeight - minHeight) * (Settings.WATER_LEVEL));
		System.out.println(height);
		for(int z = 0; z < totalSize; z++) {
			for(int x = 0; x < totalSize; x++) {
				yValues[x][z] = height;
			}
		}
	}
	
	private void generateDiamondSquareNoise() {
		yValues = this.dSq.generateNoise();
	}
	
	private void create() {
		for(int z = 0; z < size; z++) {
			for(int x = 0; x < size; x++) {
				
				float xPos = x-((size-1)/2.0f)+position.x;
				float zPos = ((size-1)/2.0f)+position.z-z;
				
				tiles.add(new Tile(
						x,z, tileEdgeCount,
						new Point3D(xPos, 0, zPos),
						material, 
						tex,
						this.minHeight,
						this.maxHeight));
			}
		}
	}
	
	public void display(Shader shader) {
		ModelMatrix.main.pushMatrix();
		float s = (this.alg == NoiseAlgorithm.OPEN_SIMPLEX_NOISE) ? Settings.TERRAIN_SIMPLEX_SCALE : Settings.TERRAIN_DSQUARE_SCALE;
		
		ModelMatrix.main.addScale(s, 1, s);
		for(Tile tile : tiles) {
			tile.display(shader);
		}
		ModelMatrix.main.popMatrix();
	}
}
