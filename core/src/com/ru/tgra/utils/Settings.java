package com.ru.tgra.utils;
import com.badlogic.gdx.graphics.Color;

public class Settings {	
	public static final float GROUND_WIDTH = 25.0f;
	public static final float GROUND_HEIGHT = 25.0f;
	public static final float MOUSE_SENSITIVITY = 10.0f;
	public static final float WALK_SPEED = 10.0f;
	
	public static final float FOG_START = 0.0f;
	public static final float FOG_END = 40.0f;
	public static final Color FOG_COLOR = new Color(0.1f, 0.1f, 0.1f, 1.0f);
	
	public static final int SUBDIVISIONS = 10;
	
	public static final String HORIZONTAL = "HORIZONTAL";
	public static final String VERTICAL = "VERTICAL";

	/**
	 * Private constructor to prevent anyone from creating an instance of this class.
	 * This is done because the Settings class is to be used as a static class.
	 */
	private Settings() {}
}
