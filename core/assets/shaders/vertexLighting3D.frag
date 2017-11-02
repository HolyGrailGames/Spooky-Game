
#ifdef GL_ES
precision mediump float;
#endif

uniform float u_usesDiffuseTexture;
uniform sampler2D u_diffuseTexture;

varying vec4 v_mainColor;
varying vec4 v_specColor;
varying vec2 v_uv;

void main()
{
	if(u_usesDiffuseTexture == 1.0)
	{
		gl_FragColor = v_mainColor * texture2D(u_diffuseTexture, v_uv) + v_specColor;
	}
	else
	{
		gl_FragColor = v_mainColor + v_specColor;
	}
}