package com.ru.tgra.utils;

import com.badlogic.gdx.graphics.Color;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.noise.NoiseAlgorithm;

public class Settings {
	/*** MOVEMENT ***/
	public static final float MOUSE_SENSITIVITY = 4.0f;
	public static final float WALK_SPEED = 6.0f;

	/*** FOG ***/
	public static final float FOG_START = 10.0f;
	public static final float FOG_END = 50.0f;
	public static final Color FOG_COLOR = new Color(0.8f, 0.8f, 0.8f, 1.0f);

	/*** CAMERA ***/
	public static final float FOV = 55.0f;
	
	/*** TERRAIN ***/
	//public static final NoiseAlgorithm NOISE_ALG = NoiseAlgorithm.OPEN_SIMPLEX_NOISE;
	public static final NoiseAlgorithm NOISE_ALG = NoiseAlgorithm.DIAMOND_SQUARE;
	
	// DIAMOND SQUARE SETTINGS
	public static final int TERRAIN_DSQUARE_SIZE = 4;		// The terrain will be size*size tiles large
															// size must be a power of 2 (ex. 4,8,16,32,...)
	
	public static final int TERRAIN_DSQUARE_TILE_SIZE = 64;	// tile size must be a power of 2, max value is 128
															// A higher tile size means more triangles are drawn, and thus more details in terrain
	
	public static final float TERRAIN_DSQUARE_CORNER_HEIGHT = -10.0f;	// Corners of terrain will be seeded with this value
	public static final float TERRAIN_DSQUARE_RANGE = 10f; 	// Terrain will be generated in range (-range to range)
															// The higher this number, the more rugged the landscape becomes
	
	public static final float TERRAIN_DSQUARE_SCALE = 10.0f;	// The scale of the entire terrain
	
	
	// OPEN SIMPLEX NOISE SETTINGS
	public static final int   TERRAIN_SIMPLEX_SIZE = 8;			// Number of tiles
	public static final int   TERRAIN_SIMPLEX_TILE_SIZE = 32;		// Size of tiles, each tile has (size^2)*2 triangles drawn
	public static final float TERRAIN_SIMPLEX_MIN_HEIGHT = -3.0f;	// Minimum height of the terrain
	public static final float TERRAIN_SIMPLEX_MAX_HEIGHT = 3f;		// Maximum height of the terrain
	public static final float TERRAIN_SIMPLEX_STEEPNESS = 0.02f;	// Higher value means more steepness
	public static final float TERRAIN_SIMPLEX_SCALE = 5;
	
	
	// TERRAIN COLORS
	public static final float WATER_LEVEL = 0.33f;
	public static final float DIRT_LEVEL = 0.37f;
	public static final float GRASS_LEVEL = 0.40f;
	public static final float ROCK_LEVEL = 0.75f;
	

	/*** LIGHTS ***/
	// FLASHLIGHT
	public static final Color FL_COLOR = Color.WHITE;
	public static final float FL_SPOTEXP = 5.0f;
	public static final float FL_CONSTATT = 1.0f;
	public static final float FL_LINATT = 0.0f;
	public static final float FL_QUADATT = 0.0f;
	
	/*** FIREFLIES ***/
	public static final int FIREFLY_COUNT = 1000;

	/*** MATERIALS ***/
	public static final Material TERRAIN_MATERIAL = new Material(
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Diffuse
			new Color(0.15f, 0.15f, 0.15f, 1.0f),	// Specular
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Emission
			50.0f,	// Shininess
			1.0f	// Opacity
	);
	
	public static final Material WATER_MATERIAL = new Material(
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Diffuse
			new Color(0.8f, 0.8f, 0.8f, 1.0f),	// Specular
			new Color(0.0f, 0.0f, 0.5f, 1.0f),	// Emission
			128.0f,	// Shininess
			0.8f	// Opacity
	);
	
	public static final Material TEST_MATERIAL = new Material(
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Diffuse
			new Color(0.2f, 0.2f, 0.2f, 1.0f),	// Specular
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Emission
			128.0f,	// Shininess
			1.0f	// Opacity
	);
	

	/**
	 * Private constructor to prevent anyone from creating an instance of this class.
	 * This is done because the Settings class is to be used as a static class.
	 */
	private Settings() {}
}
