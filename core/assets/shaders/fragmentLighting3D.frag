#version 330

#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_diffuseTexture;
uniform sampler2D u_alphaTexture;
uniform sampler2D u_emissionTexture;

uniform float u_usesDiffuseTexture;
uniform float u_usesAlphaTexture;
uniform float u_usesEmissionTexture;

uniform vec4 u_globalAmbient;

uniform vec4 u_lightColor;

uniform vec4 u_spotDirection;
uniform float u_spotExponent;

uniform float u_constantAttenuation;
uniform float u_linearAttenuation;
uniform float u_quadraticAttenuation;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform float u_materialShininess;
uniform float u_materialOpacity;
uniform vec4 u_materialEmission;

// FOG stuff

uniform float u_fogStart;
uniform float u_fogEnd;
uniform vec4 u_fogColor;

varying float v_distance;

varying vec2 v_uv;
varying vec4 v_normal;
varying vec4 v_s;
varying vec4 v_h;
varying vec3 v_color;

void main()
{
	vec4 materialDiffuse;
	if(u_usesDiffuseTexture == 1.0)
	{
		materialDiffuse = texture2D(u_diffuseTexture, v_uv);// *vec4(v_color.r, v_color.g, v_color.b, 1.0); //also * u_materialDiffuse ??? up to you.
	}
	else
	{
		//materialDiffuse = u_materialDiffuse;
		materialDiffuse = vec4(v_color.r, v_color.g, v_color.b, u_materialOpacity);
	}
	
	vec4 materialEmission;
	if(u_usesEmissionTexture == 1.0)
	{
		materialEmission = texture2D(u_emissionTexture, v_uv); // * u_materialEmission;  Also * u_materialEmission ??? up to you. 		
	}
	else
	{
		materialEmission = u_materialEmission;
	}
	
	if(u_usesAlphaTexture == 1.0)
	{
		materialDiffuse.a = texture2D(u_alphaTexture, v_uv).r;
	}

	materialDiffuse.a *= materialEmission.a;

	/*
	if(materialDiffuse.a < 0.1) 
	{
		discard;
	}
	*/

	vec4 materialSpecular = u_materialSpecular;

	//Lighting
	
	float length_s = length(v_s);
	
	float lambert = max(0.0, dot(v_normal, v_s) / (length(v_normal) * length_s));
	float phong = max(0.0, dot(v_normal, v_h) / (length(v_normal) * length(v_h)));

	vec4 diffuseColor = lambert * u_lightColor * materialDiffuse;

	vec4 specularColor = pow(phong, u_materialShininess) * u_lightColor * materialSpecular;

	float attenuation = 1.0;
	if(u_spotExponent != 0.0)
	{
		float spotAttenuation = max(0.0, dot(-v_s, u_spotDirection) / (length_s * length(u_spotDirection)));
		spotAttenuation = pow(spotAttenuation, u_spotExponent);
		attenuation *= spotAttenuation;
	}
	attenuation *= 1.0 / (u_constantAttenuation + length_s * u_linearAttenuation + pow(length_s, 2.0) * u_quadraticAttenuation);
		
	vec4 light1CalcColor = attenuation * (diffuseColor + specularColor);

	// end for each light
	
	vec4 finalObjectColor = u_globalAmbient * materialDiffuse + materialEmission + light1CalcColor;
	
	// FOG stuff
	if(v_distance < u_fogStart) 
	{
		gl_FragColor = finalObjectColor;
	}
	else if(v_distance > u_fogEnd) 
	{	// It's possible to optimize this by having this if check at the top of
		// the shader and just have if(v_distance < u_fogStart)
		gl_FragColor = u_fogColor;
	}
	else 
	{
		// Possible to optimize this by setting the fog width somewhere so we don't 
		// need to calculate this every time
		float fogRatio = (v_distance - u_fogStart) / (u_fogEnd - u_fogStart);
		gl_FragColor = (1 - fogRatio) * finalObjectColor + fogRatio * u_fogColor;
		
	}
	gl_FragColor.a = u_materialOpacity;
	
}