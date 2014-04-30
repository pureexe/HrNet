package com.pureexe.hrnet;

import android.content.Context;
import android.util.DisplayMetrics;

public class PortableUtil {
	public static float getDensity(Context context){
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return metrics.density;
	}
}
