package com.ru.tgra.objects;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.PlaneGraphic;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class Tile extends GameObject {

	public Tile(Point3D position, Vector3D scale, Material material, Texture tex) {
		super(position, null, scale, material, tex);
		// TODO Auto-generated constructor stub
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
		ModelMatrix.main.addScale(scale.x, scale.y, scale.z);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		PlaneGraphic.drawSolidPlane(shader, tex, null);
		ModelMatrix.main.popMatrix();

	}

}
