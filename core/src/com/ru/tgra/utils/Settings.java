package com.ru.tgra.utils;

public class Settings {	
	public static final int GROUND_WIDTH = 25;
	public static final int GROUND_HEIGHT = 25;
	public static final float MOUSE_SENSITIVITY = 10.0f;
	public static final float WALK_SPEED = 10.0f;
	public static final float FOG_START = 0.0f;
	public static final float FOG_END = 40.0f;

	/**
	 * Private constructor to prevent anyone from creating an instance of this class.
	 * This is done because the Settings class is to be used as a static class.
	 */
	private Settings() {}
}
