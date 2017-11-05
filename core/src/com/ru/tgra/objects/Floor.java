package com.ru.tgra.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.ru.tgra.graphics.Material;
import com.ru.tgra.graphics.Shader;
import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Settings;
import com.ru.tgra.utils.Vector3D;


public class Floor extends GameObject {

	ArrayList<Tile> tiles;

	public Floor(Point3D position, Vector3D scale, Material material, Texture tex) {
		super(position, null, scale, material, tex);
		createTiles();
	}

	private void createTiles() {
		tiles = subdivide(Settings.SUBDIVISIONS, Settings.HORIZONTAL, position.x, position.z, scale.x, scale.z);
	}

	private ArrayList<Tile> subdivide(int numDivisionsLeft, String direction, float Px, float Pz, float Sx, float Sz) {
		if (numDivisionsLeft == 0) {
			ArrayList<Tile> list =  new ArrayList<Tile>();
			list.add(new Tile(new Point3D(Px, position.y, Pz), new Vector3D(Sx, scale.y, Sz), null, tex));
			return list;
		}
		else if (direction == Settings.HORIZONTAL) {
			ArrayList<Tile> list = new ArrayList<Tile>(
					subdivide(numDivisionsLeft-1, Settings.VERTICAL, Px-Sx/4, Pz, Sx/2, Sz));
			list.addAll(
					subdivide(numDivisionsLeft-1, Settings.VERTICAL, Px+Sx/4, Pz, Sx/2, Sz));
			return list;
		}
		else {
			ArrayList<Tile> list = new ArrayList<Tile>(
					subdivide(numDivisionsLeft-1, Settings.HORIZONTAL, Px, Pz-Sz/4, Sx, Sz/2));

			list.addAll(
					subdivide(numDivisionsLeft-1, Settings.HORIZONTAL, Px, Pz+Sz/4, Sx, Sz/2));
			return list;
		}
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(Shader shader) {
		for (Tile tile : tiles) {
			tile.display(shader);
		}
	}

}
