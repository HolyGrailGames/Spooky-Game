package com.ru.tgra.motion;

import com.ru.tgra.utils.Point3D;

public class LinearMotion {
	private Point3D p1;
	private Point3D p2;
	private float startTime;
	private float endTime;
	
	
	public LinearMotion(Point3D p1, Point3D p2, float startTime, float endTime)
	{
		this.p1 = p1;
		this.p2 = p2;
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
			out_position.x = p2.x;
			out_position.y = p2.y;
			out_position.z = p2.z;
		}
		else 
		{
			float t = (currentTime - startTime) / (endTime - startTime);
			out_position.x = (1.0f - t) * p1.x + t * p2.x;
			out_position.y = (1.0f - t) * p1.y + t * p2.y;
			out_position.z = (1.0f - t) * p1.z + t * p2.z;
		}
		
	}
	
}
