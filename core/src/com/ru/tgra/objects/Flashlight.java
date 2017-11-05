package com.ru.tgra.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Light;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;

public class Flashlight extends GameObject {
	private Light light;
	private boolean on;

	public Flashlight(Point3D position, Vector3D direction) {
		super(position, direction);
		this.on = true;
		this.light = new Light(
				//0,
				position,
				direction,
				Settings.FL_COLOR,
				Settings.FL_SPOTEXP,
				Settings.FL_CONSTATT,
				Settings.FL_LINATT,
				Settings.FL_QUADATT);
	}

	public void toggle() {
		on = !on;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void display(Shader shader) {
		if (on) {
			light.display(shader);
		}
		else {
			shader.setLightOn(0.0f);
			// something?
		}
	}
}
