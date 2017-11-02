package com.ru.tgra.graphics.shapes.g3djmodel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.BufferUtils;
import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.ru.tgra.graphics.Material;

public class G3DJModelLoader {

	public static MeshModel loadG3DJFromFile(String fileName, boolean buildSphericalUVMap)
	{
		MeshModel model = new MeshModel();

		JsonObject graph = (JsonObject)JsonReader.jsonToMaps(Gdx.files.internal("models/" + fileName).readString());
		//System.out.println(graph);
		short[] version = getShortArray(graph, "version");
		for(int number : version)
		{
			//System.out.println(number);
		}

		String id = getString(graph, "id");
		//System.out.println(id);

		Object[] meshes = getObjectArray(graph, "meshes");
		for(Object meshDesc : meshes)
		{
			Mesh mesh = new Mesh();
			//System.out.println(meshDesc);

			String[] attributes = getStringArray(meshDesc, "attributes");
			if(!attributes[0].equals("POSITION") || !attributes[1].equals("NORMAL"))
			{
				System.out.println("POSITION and/or NORMAL missing or not in correct order");
				return null;
			}
			int vertexFill = 0;
			for(int attributeNum = 2; attributeNum < attributes.length; attributeNum++)
			{
				//System.out.println(attributes[attributeNum]);
				if(attributes[attributeNum].equals("COLOR")) { vertexFill += 4; }
				if(attributes[attributeNum].equals("COLORPACKED")) { vertexFill += 1; }
				if(attributes[attributeNum].equals("TANGENT")) { vertexFill += 3; }
				if(attributes[attributeNum].equals("BINORMAL")) { vertexFill += 3; }
				if(attributes[attributeNum].equals("TEXCOORD")) { vertexFill += 2; }
				if(attributes[attributeNum].equals("BLENDWEIGHT")) { vertexFill += 2; }
			}

			float[] vertices = getFloatArray(meshDesc, "vertices");
			int vertexCount = vertices.length / (6 + vertexFill);
			mesh.vertices = BufferUtils.newFloatBuffer(vertexCount * 3);
			mesh.vertices.rewind();
			mesh.normals = BufferUtils.newFloatBuffer(vertexCount * 3);
			mesh.normals.rewind();
			int bufferIndex = 0;
			for(int v = 0; v < vertexCount; v++)
			{
				for(int i = 0; i < 3; i++) {
					mesh.vertices.put(vertices[bufferIndex++]);
				}
				for(int i = 0; i < 3; i++) {
					mesh.normals.put(vertices[bufferIndex++]);
				}
				for(int i = 0; i < vertexFill; i++) {
					bufferIndex++;
				}
			}
			mesh.vertices.rewind();
			mesh.normals.rewind();
			//System.out.println(vertices.length);

			Object[] parts = getObjectArray(meshDesc, "parts");
			for(Object partDesc : parts)
			{
				MeshPart part = new MeshPart();
				//System.out.println(partDesc);

				String meshpartid = getString(partDesc, "id");
				part.id = meshpartid;
				//System.out.println(meshpartid);

				String meshtype = getString(partDesc, "type");
				part.type = meshtype;
				//System.out.println(meshtype);

				short[] indices = getShortArray(partDesc, "indices");
				part.indices = BufferUtils.newShortBuffer(indices.length);
				part.indices.put(indices);
				part.indices.rewind();
				//System.out.println(indices.length);
				
				part.mesh = mesh;
				model.parts.add(part);
			}
			model.meshes.add(mesh);
		}
		Object[] materials = (Object[])(((JsonObject)graph.get("materials")).getArray());
		for(Object materialDesc : materials)
		{
			Material material = new Material();
			//System.out.println(materialDesc);

			String materialid = getString(materialDesc, "id");
			material.id = materialid;
			//System.out.println(materialid);

			float[] ambient = getFloatArray(materialDesc, "ambient");
			if(ambient != null)
			{
				material.ambient.r = ambient[0];
				material.ambient.g = ambient[1];
				material.ambient.b = ambient[2];
			}

			float[] diffuse = getFloatArray(materialDesc, "diffuse");
			if(diffuse != null)
			{
				material.diffuse.r = diffuse[0];
				material.diffuse.g = diffuse[1];
				material.diffuse.b = diffuse[2];
			}

			float[] specular = getFloatArray(materialDesc, "specular");
			if(specular != null)
			{
				material.specular.r = specular[0];
				material.specular.g = specular[1];
				material.specular.b = specular[2];
			}

			float[] emission = getFloatArray(materialDesc, "emissive");
			if(emission != null)
			{
				material.emission.r = emission[0];
				material.emission.g = emission[1];
				material.emission.b = emission[2];
			}

			float opacity = getFloat(materialDesc, "opacity");
			material.opacity = opacity;

			float shininess = getFloat(materialDesc, "shininess");
			material.shininess = shininess;

			model.materials.add(material);
		}
		Object[] nodes = getObjectArray(graph, "nodes");
		for(Object nodeDesc : nodes)
		{
			MeshModelNode node = new MeshModelNode();
			//System.out.println(nodeDesc);

			String nodeid = getString(nodeDesc, "id");
			node.id = nodeid;
			//System.out.println(nodeid);

			float[] rotation = getFloatArray(nodeDesc, "rotation");
			if(rotation != null)
			{
				node.rotation.x = rotation[0];
				node.rotation.y = rotation[1];
				node.rotation.z = rotation[2];
				node.rotation.w = rotation[3];
			}

			float[] scale = getFloatArray(nodeDesc, "scale");
			if(scale != null)
			{
				node.scale.x = scale[0];
				node.scale.y = scale[1];
				node.scale.z = scale[2];
			}

			float[] translation = getFloatArray(nodeDesc, "translation");
			if(translation != null)
			{
				node.translation.x = translation[0];
				node.translation.y = translation[1];
				node.translation.z = translation[2];
			}
			Object[] nodeParts = getObjectArray(nodeDesc, "parts");

			for(Object nodePartDesc : nodeParts)
			{
				//System.out.println(nodePartDesc);

				MeshModelNodePart nodePart = new MeshModelNodePart();
				String meshpartid = getString(nodePartDesc, "meshpartid");
				String materialid = getString(nodePartDesc, "materialid");

				for(MeshPart part : model.parts)
				{
					if(part.id.equals(meshpartid))
					{
						nodePart.part = part;
						break;
					}
				}
				for(Material material : model.materials)
				{
					if(material.id.equals(materialid))
					{
						nodePart.material = material;
						break;
					}
				}
				node.parts.add(nodePart);
			}

			model.nodes.add(node);
		}
		if(buildSphericalUVMap) {
			for(Mesh mesh : model.meshes) {
			
				mesh.buildSphericalUVMap();
			}
			
		}
		
		return model;
	}

	private static Object[] getObjectArray(Object jsonObject, String key)
	{
		if(((JsonObject)jsonObject).get(key) == null) return null;
		return (Object[])(((JsonObject)(((JsonObject)jsonObject).get(key))).getArray());
	}

	private static String getString(Object jsonObject, String key)
	{
		if(((JsonObject)jsonObject).get(key) == null) return null;
		return ((String)((JsonObject)jsonObject).get(key));
	}

	private static String[] getStringArray(Object jsonObject, String key)
	{
		Object[] obArray = getObjectArray(jsonObject, key);
		if(obArray == null) return null;
		String[] stringArray = new String[obArray.length];
		int i = 0;
		for(Object str : obArray)
		{
			stringArray[i++] = (String)str;
		}
		return stringArray;
	}

	private static float getFloat(Object jsonObject, String key)
	{
		if(((JsonObject)jsonObject).get(key) == null) return 0.0f;
		try {
			return ((Double)((JsonObject)jsonObject).get(key)).floatValue();
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		try {
			return ((Long)((JsonObject)jsonObject).get(key)).floatValue();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0f;
	}

	private static float[] getFloatArray(Object jsonObject, String key)
	{
		Object[] obArray = getObjectArray(jsonObject, key);
		if(obArray == null) return null;
		float[] floatArray = new float[obArray.length];
		int i = 0;
		for(Object f : obArray)
		{
			floatArray[i++] = ((Double)f).floatValue();
		}
		return floatArray;
	}

	private static int getInt(Object jsonObject, String key)
	{
		if(((JsonObject)jsonObject).get(key) == null) return 0;
		return ((Long)((JsonObject)jsonObject).get(key)).intValue();
	}

	private static short[] getShortArray(Object jsonObject, String key)
	{
		Object[] obArray = getObjectArray(jsonObject, key);
		if(obArray == null) return null;
		short[] shortArray = new short[obArray.length];
		int i = 0;
		for(Object f : obArray)
		{
			shortArray[i++] = ((Long)f).shortValue();
		}
		return shortArray;
	}
}
