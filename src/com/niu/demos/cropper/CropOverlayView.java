package com.niu.demos.cropper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class CropOverlayView extends View {
    private static final int SNAP_RADIUS_DP = 6;
    private static final float DEFAULT_SHOW_GUIDELINES_LIMIT = 100;
    
    /** 边界角大小 */
    private static final float DEFAULT_CORNER_THICKNESS_DP = PaintUtil.getCornerThickness();
    /** 边界线大小 */
    private static final float DEFAULT_LINE_THICKNESS_DP = PaintUtil.getLineThickness();
    
    private static final float DEFAULT_CORNER_OFFSET_DP = (DEFAULT_CORNER_THICKNESS_DP / 2) - 
    														(DEFAULT_LINE_THICKNESS_DP / 2);
    
    private static final float DEFAULT_CORNER_EXTENSION_DP = DEFAULT_CORNER_THICKNESS_DP / 2
                                                             + DEFAULT_CORNER_OFFSET_DP;
    
    private static final float DEFAULT_CORNER_LENGTH_DP = 20;
    
    //-------------------- 网格线 枚举属性 -------------------------
    /** 不显示网格 */
    private static final int GUIDELINES_OFF = 0; 
    /** 触摸后显示网格 */
    private static final int GUIDELINES_ON_TOUCH = 1;
    /** 总是显示网格 */
    private static final int GUIDELINES_ON = 2;
    //------------------------ end ----------------------------
    
    private float mCornerExtension;
    private float mCornerOffset;
    private float mCornerLength;
	
	/** 触摸区域的半径 */
    private float mHandleRadius;
    
    private float mSnapRadius;
    /** 剪辑器描边画笔 */
    private Paint mBorderPaint;
    /** 网格画笔 当按住剪辑器时，画出剪辑窗口中的网格 */
    private Paint mGuidelinePaint;
    /** 四个尖角画笔， 用于画出四个角落 */
    private Paint mCornerPaint;
    /** */
    private Paint mBackgroundPaint;
    /** 网格线的数量 */
    private int mGuidelines;
    
    private Rect mBitmapRect;
    
    private Handle mPressedHandle;
	
	public CropOverlayView(Context context){
		super(context);
		init(context);
	}

	public CropOverlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		
		mHandleRadius = HandleUtil.getTargetRadius(context);
		mSnapRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP	, 
												SNAP_RADIUS_DP, 
												displayMetrics);
		
		mBorderPaint = PaintUtil.newBorderPaint(context);
		mGuidelinePaint = PaintUtil.newGuidelinePaint();
		mCornerPaint = PaintUtil.newCornerPaint(context);
		mBackgroundPaint = PaintUtil.newBackgroundPaint(context);
		
		mCornerOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
													DEFAULT_CORNER_OFFSET_DP,
													displayMetrics);
		
		mCornerExtension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
														DEFAULT_CORNER_EXTENSION_DP,
														displayMetrics);
		
		mCornerLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
													DEFAULT_CORNER_LENGTH_DP,
													displayMetrics);
		
		mGuidelines = CropImageView.DEFAULT_GUIDELINES;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(!isEnabled()){
			return false;
		}
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			break;
			
		case MotionEvent.ACTION_UP:
			
			break;
		}
		return false;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBackground(canvas, mBitmapRect);
	}
	
	/** 绘制背景区域 
    -------------------------------------
    |                top                |
    -------------------------------------
    |      |                    |       |
    |      |                    |       |
    | left |                    | right |
    |      |                    |       |
    |      |                    |       |
    -------------------------------------
    |              bottom               |
    -------------------------------------
   */
    private void drawBackground(Canvas canvas, Rect bitmapRect) {
		float left = Edge.LEFT.getmCoordinate();
		float right = Edge.RIGHT.getmCoordinate();
		float top = Edge.TOP.getmCoordinate();
		float bottom = Edge.BOTTOM.getmCoordinate();
		
		canvas.drawRect(bitmapRect.left, bitmapRect.top, bitmapRect.right, top, mBackgroundPaint);
		canvas.drawRect(bitmapRect.left, bottom, bitmapRect.right, bitmapRect.bottom, mBackgroundPaint);
		canvas.drawRect(bitmapRect.left, top, right, bottom, mBackgroundPaint);
		canvas.drawRect(left, top, bitmapRect.right, bottom, mBackgroundPaint);
    }
    
    /** 是否显示网格线 */
    public static boolean showGuidelines() {
    	if((Math.abs(Edge.LEFT.getmCoordinate() - Edge.RIGHT.getmCoordinate())) < DEFAULT_SHOW_GUIDELINES_LIMIT ||
    		(Math.abs(Edge.TOP.getmCoordinate()-Edge.BOTTOM.getmCoordinate())) < DEFAULT_SHOW_GUIDELINES_LIMIT){
    		return false;
    	}
    	return true;
    }

	
}
