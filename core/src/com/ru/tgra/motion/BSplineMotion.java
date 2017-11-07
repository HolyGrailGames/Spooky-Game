package com.ru.tgra.motion;

import java.util.ArrayList;

import com.ru.tgra.utils.Point3D;
import com.ru.tgra.utils.Vector3D;

public class BSplineMotion implements Motion {
	private Point3D pStart;
	private Point3D pEnd;

	private float startTime;
	private float endTime;
	private float timePerMotion;
	
	private int motionCount;
	
	private ArrayList<Point3D> controlPoints;
	private ArrayList<Point3D> reverseControlPoints;
	private ArrayList<BezierMotion> motions;
	
	private boolean forward = true;
	
	public BSplineMotion(ArrayList<Point3D> controlPoints, float startTime, float endTime)
	{
		if (controlPoints.size() < 4)
		{
			motions = null;
			return;
		}
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.motionCount = (controlPoints.size() / 2) - 1;
		this.timePerMotion = (endTime - startTime) / (float)motionCount;
		
		this.controlPoints = controlPoints;
		this.reverseControlPoints = new ArrayList<Point3D>();
		initializeReverseControlPoints();
		
		motions = new ArrayList<BezierMotion>();
		initializeMotions();
	}
	
	public void getCurrentPosition(float currentTime, Point3D out_position)
	{
		if (currentTime < startTime)
		{
			out_position.x = pStart.x;
			out_position.y = pStart.y;
			out_position.z = pStart.z;
		}
		else if (currentTime > endTime)
		{
			out_position.x = pEnd.x;
			out_position.y = pEnd.y;
			out_position.z = pEnd.z;
			
			if (forward == true)
			{
				forward = false;
				startTime = (int)currentTime;
				endTime = (int)(currentTime + endTime);
				timePerMotion = (endTime - startTime) / (float)motionCount;
				initializeReverseMotions();
			}
			else
			{
				forward = true;
				startTime = (int)currentTime;
				endTime = (int)(currentTime + endTime);
				timePerMotion = (endTime - startTime) / (float)motionCount;
				initializeMotions();
			}

		}
		else 
		{
			for (BezierMotion motion : motions) 
			{
				if (motion.startTime < currentTime && currentTime < motion.endTime)
				{
					motion.getCurrentPosition(currentTime, out_position);
					break;
				}
			}
		}
	}
	
	private void initializeReverseControlPoints()
	{
		for (int i = controlPoints.size() - 1; i >= 0; i--)
		{
			reverseControlPoints.add(controlPoints.get(i));
		}
	}
	
	private void initializeMotions()
	{
		motions.clear();
		Point3D p1 = controlPoints.get(0);
		Point3D p2 = controlPoints.get(1);
		Point3D p3 = controlPoints.get(2);
		Point3D p4 = controlPoints.get(3);
		
		BezierMotion motion = new BezierMotion	(p1, p2, p3, p4, startTime, startTime + timePerMotion);
		motions.add(motion);
		
		pStart = p1;
		
		
		for (int i = 1; i < motionCount; i++) 
		{
			p1 = p4;
			p2 = p1;
			p2.add(Vector3D.difference(p4, p3));
			
			p3 = controlPoints.get((i + 1) * 2);
			p4 = controlPoints.get((i + 1) * 2 + 1);
			
			motion = new BezierMotion	(p1, p2, p3, p4, startTime + timePerMotion * i, startTime + timePerMotion * (i + 1));
			motions.add(motion);
		}
		
		pEnd = p4;
	}
	
	private void initializeReverseMotions()
	{
		motions.clear();
		Point3D p1 = reverseControlPoints.get(0);
		Point3D p2 = reverseControlPoints.get(1);
		Point3D p3 = reverseControlPoints.get(2);
		Point3D p4 = reverseControlPoints.get(3);
		
		BezierMotion motion = new BezierMotion	(p1, p2, p3, p4, startTime, startTime + timePerMotion);
		motions.add(motion);
		
		pStart = p1;
		
		for (int i = 1; i < motionCount; i++) 
		{
			p1 = p4;
			p2 = p1;
			p2.add(Vector3D.difference(p4, p3));
			
			p3 = reverseControlPoints.get((i + 1) * 2);
			p4 = reverseControlPoints.get((i + 1) * 2 + 1);
			
			motion = new BezierMotion	(p1, p2, p3, p4, startTime + timePerMotion * i, startTime + timePerMotion * (i + 1));
			motions.add(motion);
		}
		
		pEnd = p4;
	}
}
