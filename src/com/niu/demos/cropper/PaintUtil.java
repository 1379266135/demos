package com.niu.demos.cropper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

public class PaintUtil {
	
    private static final String SEMI_TRANSPARENT = "#AAFFFFFF";
    private static final int DEFAULT_CORNER_COLOR = Color.WHITE;
    private static final String DEFAULT_BACKGROUND_COLOR_ID = "#B0000000";
    
    private static final float DEFAULT_GUIDELINE_THICKNESS_PX = 1;
    private static final float DEFAULT_LINE_THICKNESS_DP = 3;
    private static final float DEFAULT_CORNER_THICKNESS_DP = 5;
    
    /**
     * 创建一个剪辑器边界画笔
     * @param context
     * @return
     */
	public static Paint newBorderPaint(Context context){
		// 单位转换， 将数值：3  转为 3dip
		final float lineThickessPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
																DEFAULT_LINE_THICKNESS_DP, 
																context.getResources().getDisplayMetrics());
		final Paint borderPaint = new Paint();
		borderPaint.setColor(Color.parseColor(SEMI_TRANSPARENT));
		borderPaint.setStrokeWidth(lineThickessPx);
		borderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		
		return borderPaint;
	}
	
	public static Paint newGuidelinePaint(){
		final Paint guidelinePaint = new Paint();
		guidelinePaint.setStrokeWidth(DEFAULT_GUIDELINE_THICKNESS_PX);
		guidelinePaint.setColor(Color.parseColor(SEMI_TRANSPARENT));
		return guidelinePaint;
	}
	
	/**
	 * 四个尖角的画笔
	 * @param context
	 * @return
	 */
	public static Paint newCornerPaint(Context context){
		final float lineThickessPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
																DEFAULT_CORNER_THICKNESS_DP,
																context.getResources().getDisplayMetrics());
		final Paint cornerPaint = new Paint();
		cornerPaint.setStrokeWidth(lineThickessPx);
		cornerPaint.setStyle(Paint.Style.STROKE);
		cornerPaint.setColor(DEFAULT_CORNER_COLOR);
		return cornerPaint;
	}
	
	public static Paint newBackgroundPaint(Context context){
		final Paint backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.parseColor(DEFAULT_BACKGROUND_COLOR_ID));
		return backgroundPaint;
	}
	
    /**
     * Returns the value of the corner thickness
     * 
     * @return Float equivalent to the corner thickness
     */
    public static float getCornerThickness() {
        return DEFAULT_CORNER_THICKNESS_DP;
    }

    /**
     * Returns the value of the line thickness of the border
     * 
     * @return Float equivalent to the line thickness
     */
    public static float getLineThickness() {
        return DEFAULT_LINE_THICKNESS_DP;
    }
}
