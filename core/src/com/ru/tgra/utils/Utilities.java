package com.ru.tgra.utils;

public class Utilities {
	// maps value from old range (istart - istop) to new range (ostart - ostop)
	static public final float map(float value, float istart, float istop, float ostart, float ostop) {
	    return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	  }
}
