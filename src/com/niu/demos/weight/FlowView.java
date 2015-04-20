package com.niu.demos.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FlowView extends RelativeLayout implements OnTouchListener{
	
	private WindowManager mWindowManager;
	private boolean isShowing = false;
	private WindowManager.LayoutParams mParams;
	private RelativeLayout mFlowLayout;
	private ImageView mFlowIcon;
	private TextView mFlowText;
	private ImageView mFlowLine;

	public FlowView(Context context) {
		super(context);
		
	}
	public FlowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	public FlowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
