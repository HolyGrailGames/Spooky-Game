#version 330

#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_diffuseTexture;

uniform float u_usesDiffuseTexture;

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

uniform vec4 u_materialEmission;

varying vec2 v_uv;
varying vec4 v_normal;
varying vec4 v_s;
varying vec4 v_h;

void main()
{
	vec4 materialDiffuse;
	if(u_usesDiffuseTexture == 1.0)
	{
		materialDiffuse = texture2D(u_diffuseTexture, v_uv);  //also * u_materialDiffuse ??? up to you.
	}
	else
	{
		materialDiffuse = u_materialDiffuse;
	}

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
	
	

	//gl_FragColor = u_globalAmbient + u_materialEmission + light1CalcColor;
	gl_FragColor = u_globalAmbient * materialDiffuse + u_materialEmission + light1CalcColor;
	//gl_FragColor.a = 0.5;
}