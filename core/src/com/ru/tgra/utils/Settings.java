package com.ru.tgra.utils;

import com.badlogic.gdx.graphics.Color;
import com.ru.tgra.graphics.Material;

public class Settings {
	/*** MOVEMENT ***/
	public static final float MOUSE_SENSITIVITY = 7.0f;
	public static final float WALK_SPEED = 3.0f;

	/*** FOG ***/
	public static final float FOG_START = 0.0f;
	public static final float FOG_END = 10.0f;
	public static final Color FOG_COLOR = new Color(0.3f, 0.3f, 0.3f, 1.0f);

	/*** PLANE CALCULATIONS ***/
	public static final int SUBDIVISIONS = 8;
	public static final String HORIZONTAL = "HORIZONTAL";
	public static final String VERTICAL = "VERTICAL";
	public static final float GROUND_WIDTH = 25.0f;
	public static final float GROUND_HEIGHT = 25.0f;

	// CAMERA
	public static final float FOV = 55.0f;

	/*** LIGHTS ***/
	// FLASHLIGHT
	public static final Color FL_COLOR = Color.WHITE;
	public static final float FL_SPOTEXP = 20.0f;
	public static final float FL_CONSTATT = 1.0f;
	public static final float FL_LINATT = 0.0f;
	public static final float FL_QUADATT = 0.0f;



	/*** MATERIALS ***/
	public static final Material TEST_MATERIAL = new Material(
			new Color(0.7f, 0.7f, 0.7f, 1.0f),	// Diffuse
			new Color(0.8f, 0.8f, 0.8f, 1.0f),	// Specular
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Emission
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
