
#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_uv;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_eyePosition;

uniform vec4 u_globalAmbient;

uniform vec4 u_lightPosition;

uniform vec4 u_spotDirection;
uniform float u_spotExponent;

uniform float u_constantAttenuation;
uniform float u_linearAttenuation;
uniform float u_quadraticAttenuation;

uniform vec4 u_lightColor;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform float u_materialShininess;

uniform vec4 u_materialEmission;

varying vec4 v_mainColor;
varying vec4 v_specColor;
varying vec2 v_uv;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;
	
	//global coordinates




	//Lighting
	vec4 v = u_eyePosition - position; //direction to the camera

	//for each light
	vec4 s = u_lightPosition - position; //direction to the light
	
	vec4 h = s + v;

	float length_s = length(s);
	
	float lambert = max(0.0, dot(normal, s) / (length(normal) * length(s)));
	float phong = max(0.0, dot(normal, h) / (length(normal) * length(h)));

	vec4 diffuseColor = lambert * u_lightColor * u_materialDiffuse;
	vec4 specularColor = pow(phong, u_materialShininess) * u_lightColor * u_materialSpecular;

	float attenuation = 1.0;
	if(u_spotExponent != 0.0)
	{
		float spotAttenuation = max(0.0, dot(-s, u_spotDirection) / (length_s * length(u_spotDirection)));
		spotAttenuation = pow(spotAttenuation, u_spotExponent);
		attenuation *= spotAttenuation;
	}
	attenuation *= 1.0 / (u_constantAttenuation + length_s * u_linearAttenuation + pow(length_s, 2) * u_quadraticAttenuation);
		
	vec4 light1CalcColor = attenuation * diffuseColor;
	vec4 light1SpecColor = attenuation * specularColor;
	// end for each light
	
	
	
	v_mainColor = u_globalAmbient * u_materialDiffuse + u_materialEmission + light1CalcColor;
	v_specColor = light1SpecColor;
	
	v_uv = a_uv;



	position = u_viewMatrix * position;
	//normal = u_viewMatrix * normal;

	//eye coordinates

	//v_color = max(0, (dot(normal, vec4(0,0,1,0)) / length(normal))) * u_materialDiffuse;
	//v_color = max(0, (dot(normal, normalize(vec4(-position.x, -position.y, -position.z, 0))) / length(normal))) * u_materialDiffuse;

	gl_Position = u_projectionMatrix * position;
}