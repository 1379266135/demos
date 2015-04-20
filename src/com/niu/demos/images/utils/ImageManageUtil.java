package com.niu.demos.images.utils;

import com.niu.demos.DemosApplication;

public class ImageManageUtil {

    private static final int IMAGE_AVATAR_WIDTH_DP = 36;

    public static final int IMAGE_WIDTH = 612;
    public static final int MIDDLE_IMAGE_WIDTH = 305;
    public static final int SMALL_IMAGE_WIDTH = 150;
    public static final int IMAGE_SAVE_WIDTH = IMAGE_WIDTH * 2;
    public static final int IMAGE_ME_SAVE_QUALITY = 80;
    public static final int IMAGE_PICTURES_SAVE_QUALITY = 95;
    
    public static int getScreenWidth(){
    	return DemosApplication.mContext.getResources().getDisplayMetrics().widthPixels;
    }
    
    public static int getScreenHeight(){
    	return DemosApplication.mContext.getResources().getDisplayMetrics().heightPixels;
    }
    
    public static final int dip2pix(float dip){
    	final float scale = DemosApplication.mContext.getResources().getDisplayMetrics().density;
    	return (int)(dip * scale + 0.5f);
    }
}
