package com.niu.demos.float_window;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.niu.demos.R;

public class FlowWindow implements OnTouchListener {
	private WindowManager mWindowManager;
	private LayoutParams mWindowParams;
	public boolean isShowing = false;
	private static FlowWindow mDeatopWindow = null;
	private Context mContext;

	private ImageView mFlowIcon;
	private TextView mFlowText;
	private ImageView mFlowLine;
	private Drawable mLogoDrawable;
	private MyViewGroup flowView;

	public static FlowWindow getInstance(Context context) {
		if (mDeatopWindow == null) {
			mDeatopWindow = new FlowWindow(context.getApplicationContext());
		}
		return mDeatopWindow;
	}

	private FlowWindow(Context context) {
		mContext = context;
		mWindowManager = (WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
		mWindowParams.x = 0;
		mWindowParams.y = 0;
		mWindowParams.format = PixelFormat.RGBA_8888;
		mWindowParams.width = SizeFitUtil.dip2px(context, 102);
		mWindowParams.height = SizeFitUtil.dip2px(context, 24);
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		flowView = (MyViewGroup) mInflater.inflate(R.layout.comon_flow, null);
		mFlowIcon = (ImageView) flowView.findViewById(R.id.comon_flow_icon);
		mFlowText = (TextView) flowView.findViewById(R.id.comon_flow_text);
		mFlowLine = (ImageView) flowView.findViewById(R.id.comon_flow_line);
		flowView.setOnTouchListener(this);
	}

	/** 展示悬浮框 */
	public void showFlowWindow() {
		if (!isShowing) {
			mWindowManager.addView(flowView, mWindowParams);
			isShowing = true;
		}

	}

	/** 隐藏悬浮框 */
	public void hiddenFlowWindow() {
		if (isShowing) {
			mWindowManager.removeView(flowView);
			isShowing = false;
		}
	}

	public void setFlowIcon(int resId) {
		mLogoDrawable = mContext.getResources().getDrawable(resId);
		mFlowIcon.setBackgroundDrawable(mLogoDrawable);
	}

	public void setFlowText(String text) {
		mFlowText.setText(text);
	}

	public void hideFlowIcon() {
		mFlowIcon.setVisibility(View.GONE);
		mFlowLine.setVisibility(View.GONE);

	}

	public void showFlowIcon() {
		mFlowIcon.setVisibility(View.VISIBLE);
		mFlowLine.setVisibility(View.VISIBLE);
	}

	private boolean isMove = false;
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
				mWindowParams.x = (int) (event.getRawX() - flowView
						.getMeasuredWidth() / 2);
				mWindowParams.y = (int) (event.getRawY() - flowView
						.getMeasuredHeight() / 2);
				mWindowManager.updateViewLayout(flowView, mWindowParams);
			} else {
				if (Math.abs(event.getX() - downX) > 100
						|| Math.abs(event.getY() - downY) > 100) {
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
