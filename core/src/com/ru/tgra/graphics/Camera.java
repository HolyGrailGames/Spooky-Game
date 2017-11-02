package com.ru.tgra.graphics;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.BufferUtils;

public class Camera {

	public Point3D eye;
	public Vector3D u;
	public Vector3D v;
	public Vector3D n;

	boolean orthographic;

	float left;
	float right;
	float bottom;
	float top;
	float near;
	float far;

	private FloatBuffer matrixBuffer;
	
	public Camera()
	{
		matrixBuffer = BufferUtils.newFloatBuffer(16);

		eye = new Point3D();
		u = new Vector3D(1,0,0);
		v = new Vector3D(0,1,0);
		n = new Vector3D(0,0,1);

		orthographic = true;

		this.left = -1;
		this.right = 1;
		this.bottom = -1;
		this.top = 1;
		this.near = -1;
		this.far = 1;
	}

	public void look(Point3D eye, Point3D center, Vector3D up) {

		this.eye.set(eye.x, eye.y, eye.z);
		n = Vector3D.difference(eye, center);
		u = up.cross(n);
		n.normalize();
		u.normalize();
		v = n.cross(u);

	}

	public void setEye(float x, float y, float z)
	{
		eye.set(x, y, z);
	}

	public void slide(float delU, float delV, float delN)
	{
		eye.x += delU*u.x + delV*v.x + delN*n.x;
		eye.y += delU*u.y + delV*v.y + delN*n.y;
		eye.z += delU*u.z + delV*v.z + delN*n.z;
	}

	public void roll(float angle)
	{
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		Vector3D t = new Vector3D(u.x, u.y, u.z);

		u.set(t.x * c - v.x * s, t.y * c - v.y * s, t.z * c - v.z * s);
		v.set(t.x * s + v.x * c, t.y * s + v.y * c, t.z * s + v.z * c);
		
	}

	public void yaw(float angle)
	{
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = -(float)Math.sin(radians);
		Vector3D t = new Vector3D(u.x, u.y, u.z);

		u.set(t.x * c - n.x * s, t.y * c - n.y * s, t.z * c - n.z * s);
		n.set(t.x * s + n.x * c, t.y * s + n.y * c, t.z * s + n.z * c);
		
	}

	public void pitch(float angle)
	{
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		Vector3D t = new Vector3D(n.x, n.y, n.z);

		n.set(t.x * c - v.x * s, t.y * c - v.y * s, t.z * c - v.z * s);
		v.set(t.x * s + v.x * c, t.y * s + v.y * c, t.z * s + v.z * c);
		
	}

	public void walkForward(float del)
	{
		eye.x -= del*n.x;
		//eye.y += del*n.y;
		eye.z -= del*n.z;
	}

	public void rotateY(float angle)
	{
		float radians = angle * (float)Math.PI / 180.0f;
		float c = (float)Math.cos(radians);
		float s = -(float)Math.sin(radians);

		u.set(c * u.x - s * u.z, u.y, s * u.x + c * u.z);
		v.set(c * v.x - s * v.z, v.y, s * v.x + c * v.z);
		n.set(c * n.x - s * n.z, n.y, s * n.x + c * n.z);
		
	}

	public void orthographicProjection(float left, float right, float bottom, float top, float near, float far) {
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.top = top;
		this.near = near;
		this.far = far;
		orthographic = true;
	}

	public void perspectiveProjection(float fov, float ratio, float near, float far) {
		this.top = near * (float)Math.tan(((double)fov / 2.0) * Math.PI / 180.0);  //N*tan(fov/2)
		this.bottom = -top;
		this.right = ratio * top;
		this.left = -right;
		this.near = near;
		this.far = far;
		
		orthographic = false;
	}

	public FloatBuffer getViewMatrix()
	{
		float[] pm = new float[16];

		Vector3D minusEye = new Vector3D(-eye.x, -eye.y, -eye.z);
		
		pm[0] = u.x; pm[4] = u.y; pm[8] = u.z; pm[12] = minusEye.dot(u);
		pm[1] = v.x; pm[5] = v.y; pm[9] = v.z; pm[13] = minusEye.dot(v);
		pm[2] = n.x; pm[6] = n.y; pm[10] = n.z; pm[14] = minusEye.dot(n);
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		
		return matrixBuffer;
	}

	public FloatBuffer getProjectionMatrix()
	{
		float[] pm = new float[16];

		if(orthographic)
		{
			pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left) / (right - left);
			pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom) / (top - bottom);
			pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 2.0f / (near - far); pm[14] = (near + far) / (near - far);
			pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;
		}
		else
		{
			pm[0] = (2.0f * near) / (right - left); pm[4] = 0.0f; pm[8] = (right + left) / (right - left); pm[12] = 0.0f;
			pm[1] = 0.0f; pm[5] = (2.0f * near) / (top - bottom); pm[9] = (top + bottom) / (top - bottom); pm[13] = 0.0f;
			pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = -(far + near) / (far - near); pm[14] = -(2.0f * far * near) / (far - near);
			pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = -1.0f; pm[15] = 0.0f;
		}

		matrixBuffer = BufferUtils.newFloatBuffer(16);
		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		
		return matrixBuffer;
	}

	
}
