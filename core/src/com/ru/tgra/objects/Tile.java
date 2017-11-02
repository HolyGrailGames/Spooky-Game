package com.ru.tgra.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.shapes.PlaneGraphic;
import com.ru.tgra.managers.GameManager;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class Tile extends GameObject {

	public Tile(Point3D position, Vector3D scale, Vector3D rotation, Color color, Texture tex) {
		super(position, scale, rotation, color, tex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		ModelMatrix.main.pushMatrix();
		GameManager.shader.setMaterialDiffuse(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.r, 1.0f);
		GameManager.shader.setMaterialSpecular(0, 0, 0, 1);
		ModelMatrix.main.addTranslation(position.x, position.y, position.z);
		ModelMatrix.main.addScale(scale.x, scale.y, scale.z);
		GameManager.shader.setModelMatrix(ModelMatrix.main.getMatrix());
		PlaneGraphic.drawSolidCube(GameManager.shader, tex, null);
		ModelMatrix.main.popMatrix();
		
	}

}
