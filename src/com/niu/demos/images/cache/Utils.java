package com.niu.demos.images.cache;

import android.os.Build;

public class Utils {

	private Utils(){}
	
	public static boolean hasGingerbread(){
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}
	
	public static boolean hasHoneycombMR1(){
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}
}
