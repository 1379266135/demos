package com.niu.demos.images.utils;

import java.util.Locale;

import android.widget.ImageView;

public class DisplayImagaUtil {
	
	public static void showSmallImage(String fileName, ImageView imageView, boolean isAddMemoryCache){
		String url = "http://pic.getcai.com/pic/%d/%s.jpg/";
		url = String.format(Locale.US, url, ImageManageUtil.SMALL_IMAGE_WIDTH, fileName);
		
	}
	
	public static void showLargeImage(String fileName, ImageView imageView, boolean isAddMemoryCache){
		
	}
}
