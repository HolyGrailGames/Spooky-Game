package com.ru.tgra.utils;

import com.badlogic.gdx.graphics.Color;
import com.ru.tgra.graphics.Material;

public class Settings {
	/*** MOVEMENT ***/
	public static final float MOUSE_SENSITIVITY = 7.0f;
	public static final float WALK_SPEED = 10.0f;

	/*** FOG ***/
	public static final float FOG_START = 0.0f;
	public static final float FOG_END = 70.0f;
	public static final Color FOG_COLOR = new Color(0.3f, 0.3f, 0.3f, 1.0f);

	/*** CAMERA ***/
	public static final float FOV = 55.0f;
	
	/*** TERRAIN ***/
	public static final NoiseAlgorithm NOISE_ALG = NoiseAlgorithm.DIAMOND_SQUARE;
	
	// DIAMOND SQUARE SETTINGS
	public static final int TERRAIN_DSQUARE_SIZE = 16;		// size must be a power of 2 (ex 2^3 = 8)
	public static final int TERRAIN_DSQUARE_TILE_SIZE = 16;	// tile size must be a power of 2
	public static final float TERRAIN_DSQUARE_CORNER_HEIGHT = -10.0f;	// Corners of terrain will be seeded with this value
	public static final float TERRAIN_DSQUARE_RANGE = 20f; 	// Terrain will be generated in range (-range to range)
															// The higher this number, the more rugged the landscape becomes
	public static final float TERRAIN_DSQUARE_SCALE = 5.0f;	// 
	
	// SIMPLEX NOISE SETTINGS
	public static final float TERRAIN_MIN_HEIGHT = -1.0f;
	public static final float TERRAIN_MAX_HEIGHT = 1f;
	public static final float TERRAIN_STEEPNESS = 0.01f;	// Higher value means more steepness
	public static final int   TERRAIN_SIZE = 8;				// Number of tiles
	public static final int   TERRAIN_TILE_SIZE = 32;		// Size of tiles, each tile has (size^2)*2 triangles drawn
	public static final float TERRAIN_SCALE = 5;

	/*** LIGHTS ***/
	// FLASHLIGHT
	public static final Color FL_COLOR = Color.WHITE;
	public static final float FL_SPOTEXP = 10.0f;
	public static final float FL_CONSTATT = 1.0f;
	public static final float FL_LINATT = 0.0f;
	public static final float FL_QUADATT = 0.0f;
	
	/*** FIREFLIES ***/
	public static final int FIREFLY_COUNT = 10;
	



	/*** MATERIALS ***/
	public static final Material TEST_MATERIAL = new Material(
			new Color(0.7f, 0.7f, 0.7f, 1.0f),	// Diffuse
			new Color(0.1f, 0.1f, 0.1f, 1.0f),	// Specular
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Emission
			128.0f	// Shininess
	);
	
	public static final Material FL_MATERIAL = new Material(
			new Color(1.0f, 1.0f, 0.0f, 1.0f),	// Diffuse
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Specular
			new Color(0.5f, 0.5f, 0.5f, 1.0f),	// Emission
			128.0f	// Shininess
	);

	public static final Material WALL_MATERIAL = new Material(
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Diffuse
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Specular
			new Color(0.5f, 0.5f, 0.5f, 1.0f),	// Emission
			128.0f	// Shininess
	);

	public static final Material TILE_MATERIAL = new Material(
			new Color(0.5f, 0.5f, 0.5f, 1.0f),	// Diffuse
			new Color(0.7f, 0.7f, 0.7f, 1.0f),	// Specular
			new Color(0.1f, 0.1f, 0.1f, 1.0f),	// Emission
			120.0f	// Shininess
	);



	/**
	 * Private constructor to prevent anyone from creating an instance of this class.
	 * This is done because the Settings class is to be used as a static class.
	 */
	private Settings() {}
}
