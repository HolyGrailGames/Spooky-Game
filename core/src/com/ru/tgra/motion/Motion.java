package com.ru.tgra.motion;

import com.ru.tgra.utils.Point3D;

public interface Motion {
	public void getCurrentPosition(float currentTime, Point3D out_position);
}
