#version 330

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

uniform vec4 u_lightPosition;

varying vec2 v_uv;
varying vec4 v_normal;
varying vec4 v_s;
varying vec4 v_h;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;
	
	//global coordinates




	//preparation for lighting
	
	v_normal = normal;

	v_s = normalize(u_lightPosition - position); //direction to the light
	vec4 v = normalize(u_eyePosition - position); //direction to the camera
	
	v_h = v_s + v;





	position = u_viewMatrix * position;
	//eye coordinates

	v_uv = a_uv;
	gl_Position = u_projectionMatrix * position;
	//clip coordinates
}