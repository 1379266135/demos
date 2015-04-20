package com.niu.demos.cropper;

import android.graphics.Rect;

public enum Edge {
	LEFT,
	RIGHT,
	TOP,
	BOTTOM;
	
	/** 剪辑器区域的最小，大小为40像素 */
    public static final int MIN_CROP_LENGTH_PX = 40;
    private float mCoordinate;
    
    public void adjustCoordinate(float x, float y, Rect imageRect, float imageSnapRadius, float aspectRatio){
    	switch (this) {
		case LEFT:
			 mCoordinate = adjustLeft(x, imageRect, imageSnapRadius, aspectRatio);
			break;

		case TOP:
			
			break;
			
		case RIGHT:
			
			break;
			
		case BOTTOM:
			
			break;
		}
    }
    
    public static float adjustLeft(float x, Rect imageRect, float imageSnapRadius, float aspectRatio){
    	float resultX = x;
    	if(x - imageRect.left < imageSnapRadius){
    		resultX = imageRect.left;
    	} else {
    		float resultXHoriz = Float.POSITIVE_INFINITY;
    		float resultXVertical = Float.POSITIVE_INFINITY;
    		
    		if(x >= Edge.RIGHT.getmCoordinate() - MIN_CROP_LENGTH_PX){
    			resultXHoriz = Edge.RIGHT.getmCoordinate() - MIN_CROP_LENGTH_PX;
    		}
    		if((Edge.RIGHT.getmCoordinate() - x) / aspectRatio <= MIN_CROP_LENGTH_PX){
    			resultXVertical = Edge.RIGHT.getmCoordinate() - (MIN_CROP_LENGTH_PX * aspectRatio);
    		}
    		
    		resultX = Math.min(resultX, Math.min(resultXHoriz, resultXVertical));
    	}
    	
    	return resultX;
    }
    
    private static float adjustTop(float y, Rect imageRect, float imageSnapRadius, float aspectRatio){
    	float resultY = y;
    	if(y - imageRect.top < imageSnapRadius){
    		resultY = imageRect.top;
    	} else {
    		float resultYHoriz = Float.POSITIVE_INFINITY;
    		float resultYVertical = Float.POSITIVE_INFINITY;
    		
    		if(y >= Edge.BOTTOM.getmCoordinate() - MIN_CROP_LENGTH_PX){
    			resultYHoriz = Edge.BOTTOM.getmCoordinate() - MIN_CROP_LENGTH_PX;
    		}
    		if((Edge.BOTTOM.getmCoordinate() - y) * aspectRatio <= MIN_CROP_LENGTH_PX){
    			resultYVertical = Edge.BOTTOM.getmCoordinate() - (MIN_CROP_LENGTH_PX / aspectRatio);
    		}
    		
    		resultY = Math.min(resultY, Math.min(resultYHoriz, resultYVertical));
    	}
    	
    	return resultY;
    }
    
    public void offest(float distance){
    	mCoordinate += distance;
    }
    
	public float getmCoordinate() {
		return mCoordinate;
	}
	public void setmCoordinate(float mCoordinate) {
		this.mCoordinate = mCoordinate;
	}
    
}
