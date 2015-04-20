package com.niu.demos.cropper;

import android.content.Context;
import android.util.TypedValue;

public class HandleUtil {
	
    private static final int TARGET_RADIUS_DP = 24;

	public static float getTargetRadius(Context context){
		final float targetRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
																TARGET_RADIUS_DP, 
																context.getResources().getDisplayMetrics());
		return targetRadius;
	}
}
