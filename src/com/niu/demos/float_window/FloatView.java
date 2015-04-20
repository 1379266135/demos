package com.niu.demos.float_window;

import android.content.Context;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.niu.demos.R;

public class FloatView extends ViewGroup implements OnTouchListener {

	private WindowManager mWindowManager;
	public boolean isShowing = false;
	public boolean hideIcon = false;
	private WindowManager.LayoutParams mParams;
	private ImageView mFlowIcon;
	private TextView mFlowText;
	private ImageView mFlowLine;
	private Drawable mLogoDrawable;
	private Drawable mLogoLine;

	public FloatView(Context context) {
		super(context);
		setBackgroundResource(R.drawable.comon_flow_bg);
		
		initView(context);
		createWindowManager(context);
	}

	public FloatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		
		mLogoDrawable = getResources().getDrawable(R.drawable.comon_flow_g);
		mFlowIcon = new ImageView(context);
		mFlowIcon.setBackgroundDrawable(mLogoDrawable);
		addView(mFlowIcon);
		
		mLogoLine = getResources().getDrawable(R.drawable.comon_flow_line);
		mFlowLine = new ImageView(context);
		mFlowLine.setBackgroundDrawable(mLogoLine);
		addView(mFlowLine);
		
		mFlowText = new TextView(context);
		mFlowText.setGravity(Gravity.CENTER);
		mFlowText.setText("0 KB/s");
		mFlowText.setTextSize(14);
		mFlowText.setTextColor(getResources().getColor(android.R.color.white));
		addView(mFlowText);
		this.setOnTouchListener(this);
		
	}
	
	public void setFlowIcon(int resId){
		mLogoDrawable = getResources().getDrawable(resId);
		mFlowIcon.setBackgroundDrawable(mLogoDrawable);
	}
	
	public void setFlowText(String text){
		mFlowText.setText(text);
	}
	
	public void hideFlowIcon(){
		mFlowIcon.setVisibility(View.GONE);
		mFlowLine.setVisibility(View.GONE);
		
	}
	
	public void showFlowIcon(){
		mFlowIcon.setVisibility(View.VISIBLE);
		mFlowLine.setVisibility(View.VISIBLE);
	}

	private boolean isMove = false;

	private void createWindowManager(Context context) {
		mWindowManager = (WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		mParams = new WindowManager.LayoutParams();
		mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		mParams.gravity = Gravity.LEFT | Gravity.TOP;
		mParams.x = 0;
		mParams.y = 0;
		mParams.format = PixelFormat.RGBA_8888;
		mParams.width = SizeFitUtil.dip2px(getContext(), 102);
		mParams.height = SizeFitUtil.dip2px(getContext(), 24);

	}

	public void showing() {
		if (mWindowManager != null && !isShowing) {
			mWindowManager.addView(this, mParams);
			isShowing = true;
		}
	}

	public void hideFloatView() {
		if (mWindowManager != null && isShowing) {
			mWindowManager.removeView(this);
			isShowing = false;
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int w = r-l;
		int h = b-t;
		View view = this.getChildAt(0);
	
		view.layout(getPaddingLeft()+6, 									// left
					h/2-mLogoDrawable.getIntrinsicHeight()/2, 				// top
					getPaddingLeft()+mLogoDrawable.getIntrinsicWidth()+6, 	// right
					h/2+mLogoDrawable.getIntrinsicHeight()/2);				// bottom
		
		View viewLine = this.getChildAt(1);
		int height = mLogoLine.getIntrinsicHeight();
		viewLine.layout(getPaddingLeft()+mLogoDrawable.getIntrinsicWidth()+20, 
						h/2 - height/2 - 10, 
						getPaddingLeft()+mLogoDrawable.getIntrinsicWidth()+22, 
						h/2 + height/2 + 10);
		
		View viewtext = this.getChildAt(2);
		if(mFlowIcon.getVisibility() == View.GONE){
			viewtext.layout(w/2 - getTextViewWidth((TextView)viewtext)/2, 
					h/2-getTextViewHeight((TextView)viewtext)/2, 
					r, 
					h/2+getTextViewHeight((TextView)viewtext)/2);
		} else {
			viewtext.layout(getPaddingLeft()+mLogoDrawable.getIntrinsicWidth()+20, 
					h/2-getTextViewHeight((TextView)viewtext)/2, 
					r, 
					h/2+getTextViewHeight((TextView)viewtext)/2);
		}
		
	}

	private int getTextViewWidth(TextView textview){
		TextPaint paint = textview.getPaint();
		  // 得到使用该paint写上text的时候,像素为多少
		  float textLength = paint.measureText(textview.getText().toString());
		  return (int) textLength;   
	}
	
	private int getTextViewHeight(TextView textView){
		TextPaint paint = textView.getPaint();
		paint.setAntiAlias(true);
		FontMetrics fm = paint.getFontMetrics();
		int textHeight = (int) (Math.ceil(fm.descent - fm.ascent) + 2);   
		
		return textHeight;
	}


	float downX = 0;
	float downY = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getX();
			downY = event.getY();
			isMove = false;
			break;

		case MotionEvent.ACTION_MOVE:
			if (isMove) {
				mParams.x = (int) (event.getRawX() - this.getMeasuredWidth() / 2);
				mParams.y = (int) (event.getRawY() - this.getMeasuredHeight() / 2);
				mWindowManager.updateViewLayout(this, mParams);
			} else {
				if (Math.abs(event.getX() - downX) > 100 || Math.abs(event.getY() - downY) > 100) {
					isMove = true;
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			isMove = false;
			break;

		default:
			break;
		}
		return true;
	}

}
