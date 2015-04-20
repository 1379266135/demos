package com.niu.demos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class MyScrollView extends LinearLayout {
	public static final int DURATION = 1000;
	/**
	 * 滚动器，用于控制图片的滚动
	 */
	private Scroller scroller;
	/**
	 * 指示当前View是否处理显示状态
	 */
	private boolean isShow;

	public MyScrollView(Context context) {
		super(context);
		scroller = new Scroller(context);
	}
	
	

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scroller = new Scroller(context);
	}
	
	/**
	 * 当这个View显示的时候执行
	 * 因为这个是ViewGroup，当它显示的时候肯定会有一些子View，这个方法就是用来处理子View怎么显示，如子View的位置、大小等
	 * @param changed 指示ViewGroup的大小或位置是否发生改变
	 * @param l 左位置，相对于父View
	 * @param t 顶位置，相对于父View
	 * @param r 右位置，相对于父View
	 * @param b 底位置，相对于父View
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
		// 设置子视图在窗口顶部边界外，也就是把子View隐藏在上边界外
		getChildAt(0).layout(l, -getHeight(), r, 0);
	}

	/**
	 * 设置是否显示内容
	 * @param isShow
	 */
	public void isShowContent(boolean isShow) {
		this.isShow = isShow;
		int distance = isShow ? -getHeight() : getHeight();
		scroller.startScroll(0, getScrollY(), 0,  distance, DURATION);
		invalidate();
	}
	
	@Override
	public void computeScroll() {
		// 判断滚动器是否滚动完了（即滚动到上面指定的distance的位置了），如果没有则返回true
		if (scroller.computeScrollOffset()) {
			scrollTo(0, scroller.getCurrY());	// 这句代码才是真正让view发生滚动的代码
			
			// 和invalidate（）方法功能一样，但是postInvalidate();是发handler的方式来通知刷新的
			postInvalidate();
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return isShow;
	}
	
}
