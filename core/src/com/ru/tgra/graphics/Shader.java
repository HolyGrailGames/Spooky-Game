package com.ru.tgra.graphics;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Shader {

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;
	private int normalLoc;
	private int uvLoc;
	private int colorLoc;
	
	private int modelMatrixLoc;
	private int viewMatrixLoc;
	private int projectionMatrixLoc;

	private boolean usesDiffuseTexture = false;
	private int usesDiffuseTexLoc;
	private int diffuseTextureLoc;

	private boolean usesAlphaTexture = false;
	private int usesAlphaTexLoc;
	private int alphaTextureLoc;
	
	private boolean usesEmissionTexture = false;
	private int usesEmissionTexLoc;
	private int emissionTextureLoc;
	
	private int eyePosLoc;

	private int globalAmbLoc;
	private int lightPosLoc;
	private int lightOnLoc;

	private int spotDirLoc;
	private int spotExpLoc;
	private int constantAttLoc;
	private int linearAttLoc;
	private int quadraticAttLoc;
	
	private int lightColorLoc;
	private int matDifLoc;
	private int matSpecLoc;
	private int matShineLoc;
	private int matOpacityLoc;
	private int matEmissionLoc;
	
	private int fogStartLoc;
	private int fogEndLoc;
	private int fogColorLoc;

	public Shader()
	{
		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/fragmentLighting3D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/fragmentLighting3D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		System.out.println("Vertex shader compile messages:");
		System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
		System.out.println("Fragment shader compile messages:");
		System.out.println(Gdx.gl.glGetShaderInfoLog(fragmentShaderID));

		renderingProgramID = Gdx.gl.glCreateProgram();

		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
		Gdx.gl.glEnableVertexAttribArray(normalLoc);

		uvLoc					= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_uv");
		Gdx.gl.glEnableVertexAttribArray(uvLoc);
		
		colorLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_color");
		Gdx.gl.glEnableVertexAttribArray(colorLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
		projectionMatrixLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		usesDiffuseTexLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesDiffuseTexture");
		diffuseTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_diffuseTexture");

		usesAlphaTexLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesAlphaTexture");
		alphaTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_alphaTexture");
		
		usesEmissionTexLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_usesEmissionTexture");
		emissionTextureLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_emissionTexture");
		
		eyePosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_eyePosition");

		globalAmbLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_globalAmbient");

		lightPosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition");
		lightOnLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightOn");

		spotDirLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_spotDirection");
		spotExpLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_spotExponent");
		constantAttLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_constantAttenuation");
		linearAttLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_linearAttenuation");
		quadraticAttLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_quadraticAttenuation");
	
		lightColorLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightColor");
		matDifLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
		matSpecLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialSpecular");
		matShineLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialShininess");
		matOpacityLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialOpacity");
		
		fogStartLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_fogStart");
		fogEndLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_fogEnd");
		fogColorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_fogColor");
		
		matEmissionLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialEmission");

		Gdx.gl.glUseProgram(renderingProgramID);
	}

	public void setTextures(Texture diffuseTex, Texture alphaTex, Texture emissionTex)
	{
		if(diffuseTex == null)
		{
			Gdx.gl.glUniform1f(usesDiffuseTexLoc, 0.0f);
			usesDiffuseTexture = false;
		}
		else
		{
			diffuseTex.bind(1);
			Gdx.gl.glUniform1i(diffuseTextureLoc, 1);
			Gdx.gl.glUniform1f(usesDiffuseTexLoc, 1.0f);
			usesDiffuseTexture = true;

			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
		}
		
		if(alphaTex == null)
		{
			Gdx.gl.glUniform1f(usesAlphaTexLoc, 0.0f);
			usesAlphaTexture = false;
		}
		else
		{
			alphaTex.bind(0);
			Gdx.gl.glUniform1i(alphaTextureLoc, 0);
			Gdx.gl.glUniform1f(usesAlphaTexLoc, 1.0f);
			usesAlphaTexture = true;
		}
		
		if(alphaTex == null)
		{
			Gdx.gl.glUniform1f(usesEmissionTexLoc, 0.0f);
			usesEmissionTexture = false;
		}
		else
		{
			alphaTex.bind(2);
			Gdx.gl.glUniform1i(emissionTextureLoc, 2);
			Gdx.gl.glUniform1f(usesEmissionTexLoc, 1.0f);
			usesEmissionTexture = true;
			
			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
		}
		
	}
	/*
	public void setDiffuseTexture(Texture tex)
	{
		if(tex == null)
		{
			Gdx.gl.glUniform1f(usesDiffuseTexLoc, 0.0f);
			usesDiffuseTexture = false;
		}
		else
		{
			tex.bind(1);
			Gdx.gl.glUniform1i(diffuseTextureLoc, 1);
			Gdx.gl.glUniform1f(usesDiffuseTexLoc, 1.0f);
			usesDiffuseTexture = true;

			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
		}
	}
	
	
	public void setAlphaTexture(Texture tex)
	{
		if(tex == null)
		{
			Gdx.gl.glUniform1f(usesAlphaTexLoc, 0.0f);
			usesAlphaTexture = false;
		}
		else
		{
			tex.bind(0);
			Gdx.gl.glUniform1i(alphaTextureLoc, 0);
			Gdx.gl.glUniform1f(usesAlphaTexLoc, 1.0f);
			usesAlphaTexture = true;
		}
	}
	
	public void setEmissionTexture(Texture tex)
	{
		if(tex == null)
		{
			Gdx.gl.glUniform1f(usesEmissionTexLoc, 0.0f);
			usesEmissionTexture = false;
		}
		else
		{
			tex.bind(2);
			Gdx.gl.glUniform1i(emissionTextureLoc, 2);
			Gdx.gl.glUniform1f(usesEmissionTexLoc, 1.0f);
			usesEmissionTexture = true;
			
			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
			Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
		}
	}
	 */
	public boolean usesTextures()
	{
		return (usesDiffuseTexture || usesAlphaTexture || usesEmissionTexture/* || usesSpecularTexture ... etc.*/);
	}


	public void setEyePosition(float x, float y, float z, float w)
	{
		Gdx.gl.glUniform4f(eyePosLoc, x, y, z, w);
	}
	public void setGlobalAmbient(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(globalAmbLoc, r, g, b, a);
	}
	public void setLightPosition(float x, float y, float z, float w)
	{
		Gdx.gl.glUniform4f(lightPosLoc, x, y, z, w);
	}

	public void setSpotDirection(float x, float y, float z, float w)
	{
		Gdx.gl.glUniform4f(spotDirLoc, x, y, z, w);
	}
	public void setSpotExponent(float exp)
	{
		Gdx.gl.glUniform1f(spotExpLoc, exp);
	}
	public void setConstantAttenuation(float att)
	{
		Gdx.gl.glUniform1f(constantAttLoc, att);
	}
	public void setLinearAttenuation(float att)
	{
		Gdx.gl.glUniform1f(linearAttLoc, att);
	}
	public void setQuadraticAttenuation(float att)
	{
		Gdx.gl.glUniform1f(quadraticAttLoc, att);
	}
	
	public void setLightOn(float on)
	{
		Gdx.gl.glUniform1f(lightOnLoc, on);
	}

	public void setLightColor(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(lightColorLoc, r, g, b, a);
	}
	public void setMaterialDiffuse(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(matDifLoc, r, g, b, a);
	}
	public void setMaterialSpecular(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(matSpecLoc, r, g, b, a);
	}
	public void setShininess(float shine)
	{
		Gdx.gl.glUniform1f(matShineLoc, shine);
	}
	
	public void setOpacity(float opacity)
	{
		Gdx.gl.glUniform1f(matOpacityLoc, opacity);
	}
	public void setMaterialEmission(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(matEmissionLoc, r, g, b, a);
	}


	public int getVertexPointer()
	{
		return positionLoc;
	}
	public int getNormalPointer()
	{
		return normalLoc;
	}
	public int getUVPointer()
	{
		return uvLoc;
	}
	public int getColorPointer()
	{
		return colorLoc;
	}

	public void setModelMatrix(FloatBuffer matrix)
	{
		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
	}
	public void setViewMatrix(FloatBuffer matrix)
	{
		Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
	}
	public void setProjectionMatrix(FloatBuffer matrix)
	{
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
	}
	
	public void setFogStart(float start)
	{
		Gdx.gl.glUniform1f(fogStartLoc, start);
	}
	public void setFogEnd(float end)
	{
		Gdx.gl.glUniform1f(fogEndLoc, end);
	}
	public void setFogColor(Color color)
	{
		Gdx.gl.glUniform4f(fogColorLoc, color.r, color.g, color.b, color.a);
	}

	public void setLight(Light light) {
		setLightOn(1.0f);
		setLightPosition(light.position.x, light.position.y, light.position.z, 1.0f);
		setSpotDirection(light.direction.x, light.direction.y, light.direction.z, 0.0f);
		setLightColor(light.color.r, light.color.g, light.color.b, light.color.a);

		setSpotExponent(light.spotExponent);
		setConstantAttenuation(light.constantAttenuation);
		setLinearAttenuation(light.linearAttenuation);
		setQuadraticAttenuation(light.quadraticAttenuation);
	}

	public void setMaterial(Material material)
	{
		setMaterialDiffuse(material.diffuse.r, material.diffuse.g,material.diffuse.b, material.diffuse.a);
		setMaterialSpecular(material.specular.r, material.specular.g,material.specular.b, material.specular.a);
		setMaterialEmission(material.emission.r, material.emission.g,material.emission.b, material.emission.a);
		setShininess(material.shininess);
		setOpacity(material.opacity);
	}
}
