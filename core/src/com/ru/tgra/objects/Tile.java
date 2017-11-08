package com.ru.tgra.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.game.SpookyGame;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.graphics.ModelMatrix;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.graphics.shapes.PlaneGraphic;
import com.ru.tgra.managers.GameManager;
import com.ru.tgra.noise.OpenSimplexNoise;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class Tile extends GameObject {
	
	public PlaneGraphic plane;

	public Tile(int tileX, int tileZ, int edgeCount, Point3D position, Material material, Texture tex, float minHeight, float maxHeight) {
		super(position, null, null, material, tex);
		plane = new PlaneGraphic(tileX, tileZ, edgeCount, minHeight, maxHeight);
	}
	

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(Shader shader) {
		ModelMatrix.main.pushMatrix();
		if(material.opacity != 1.0f) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			//Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
			//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		}
		
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
