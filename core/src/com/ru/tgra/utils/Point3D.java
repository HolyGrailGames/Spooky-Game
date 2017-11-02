package com.ru.tgra.utils;

public class Point3D {

	public float x;
	public float y;
	public float z;

	public Point3D()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Point3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(Vector3D v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public Point3D subtract(Point3D p)
	{
		return new Point3D(x-p.x, y-p.y, z-p.z);
	}

	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void translate(float dx, float dy, float dz) {
		x += dx;
		y += dy;
		z += dz;
	}
	
	
}
