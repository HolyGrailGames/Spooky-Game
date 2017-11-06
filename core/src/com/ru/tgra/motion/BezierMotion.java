package com.ru.tgra.motion;

import com.ru.tgra.utils.Point3D;

public class BezierMotion {
	private Point3D p1;
	private Point3D p2;

	private Point3D p3;
	private Point3D p4;
	private float startTime;
	private float endTime;
	
	
	public BezierMotion(Point3D p1, Point3D p2, Point3D p3, Point3D p4, float startTime, float endTime)
	{
		this.p1 = p1;
		this.p2 = p2;

		this.p3 = p3;
		this.p4 = p4;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public void getCurrentPosition(float currentTime, Point3D out_position)
	{
		if (currentTime < startTime)
		{
			out_position.x = p1.x;
			out_position.y = p1.y;
			out_position.z = p1.z;
		}
		else if (currentTime > endTime)
		{
			out_position.x = p4.x;
			out_position.y = p4.y;
			out_position.z = p4.z;
		}
		else 
		{
			float t = (currentTime - startTime) / (endTime - startTime);
			out_position.x = (1.0f - t) * (1.0f - t) * (1.0f - t) * p1.x + 3 * (1.0f - t) * t * p2.x + 3 * (1.0f - t) * t * t * p3.x + t*t*t * p4.x;
			out_position.y = (1.0f - t) * (1.0f - t) * (1.0f - t) * p1.y + 3 * (1.0f - t) * t * p2.y + 3 * (1.0f - t) * t * t * p3.y + t*t*t * p4.y;
			out_position.z = (1.0f - t) * (1.0f - t) * (1.0f - t) * p1.z + 3 * (1.0f - t) * t * p2.z + 3 * (1.0f - t) * t * t * p3.z + t*t*t * p4.z;
		}
		
	}
	
}
