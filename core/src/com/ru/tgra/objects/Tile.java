package com.ru.tgra.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.game.SpookyGame;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.NewPlaneGraphic;
import com.ru.tgra.graphics.shapes.PlaneGraphic;
import com.ru.tgra.managers.GameManager;
import com.ru.tgra.utils.OpenSimplexNoise;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class Tile extends GameObject {
	
	public NewPlaneGraphic plane;

	public Tile(int tileX, int tileZ, int edgeCount, Point3D position, Material material, Texture tex) {
		super(position, null, null, material, tex);
		plane = new NewPlaneGraphic(tileX, tileZ, edgeCount);
	}
	

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(Shader shader) {
		ModelMatrix.main.pushMatrix();
		if (this.material != null) {
			shader.setMaterial(this.material);
		}

		ModelMatrix.main.addTranslation(position.x, position.y, position.z);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		if (GameManager.wireframe) {
			plane.drawOutlinePlane(shader, tex, null);
		}
		else {
			plane.drawSolidPlane(shader, tex, null);
		}
		ModelMatrix.main.popMatrix();
	}

}
