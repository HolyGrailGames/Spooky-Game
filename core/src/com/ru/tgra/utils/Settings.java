package com.ru.tgra.utils;
import com.badlogic.gdx.graphics.Color;
import com.ru.tgra.graphics.Material;

public class Settings {
	/*** MOVEMENT ***/
	public static final float MOUSE_SENSITIVITY = 7.0f;
	public static final float WALK_SPEED = 10.0f;
	
	/*** FOG ***/
	public static final float FOG_START = -30.0f;
	public static final float FOG_END = 60.0f;
	public static final Color FOG_COLOR = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	
	/*** PLANE CALCULATIONS ***/
	public static final int SUBDIVISIONS = 6;
	public static final String HORIZONTAL = "HORIZONTAL";
	public static final String VERTICAL = "VERTICAL";
	public static final float GROUND_WIDTH = 25.0f;
	public static final float GROUND_HEIGHT = 25.0f;
	
	// CAMERA
	public static final float FOV = 55.0f;
	
	/*** LIGHTS ***/
	// TODO: add light stuff
	
	/*** MATERIALS ***/
	public static final Material WALL_MATERIAL = new Material(
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Diffuse
			new Color(0.0f, 0.0f, 0.0f, 1.0f),	// Specular
			new Color(0.1f, 0.1f, 0.1f, 1.0f),	// Emission
			50.f	// Shininess
		);

	/**
	 * Private constructor to prevent anyone from creating an instance of this class.
	 * This is done because the Settings class is to be used as a static class.
	 */
	private Settings() {}
}
