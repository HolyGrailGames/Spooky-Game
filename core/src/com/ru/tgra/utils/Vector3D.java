package com.ru.tgra.utils;

public class Vector3D {

	public float x;
	public float y;
	public float z;

	public Vector3D()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3D(Vector3D v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void scale(float S)
	{
		x *= S;
		y *= S;
		z *= S;
	}
	
	public static Vector3D scale(Vector3D v, float S)
	{
		return new Vector3D(v.x * S, v.y * S, v.z * S);
	}

	public void add(Vector3D v2)
	{
		x += v2.x;
		y += v2.y;
		z += v2.z;
	}

	public float dot(Vector3D v2)
	{
		return x*v2.x + y*v2.y + z*v2.z;
	}

	public float dotSelf()
	{
		return x*x + y*y + z*z;
	}

	public float length()
	{
		return (float)Math.sqrt(dotSelf());
	}

	public static float length(Vector3D v) {
		return (float)Math.sqrt(v.dot(v));
	}

	public void normalize()
	{
		float len = length();
		x = x / len;
		y = y / len;
		z = z / len;
	}

	public static Vector3D up() {
		return new Vector3D(0,1,0);
	}

	public void zero() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3D cross(Vector3D v2)
	{
		return new Vector3D(y*v2.z - z*v2.y, z*v2.x - x*v2.z, x*v2.y - y*v2.x);
	}

	public static Vector3D difference(Point3D P2, Point3D P1)
	{
		return new Vector3D(P2.x-P1.x, P2.y-P1.y, P2.z-P1.z);
	}
}
