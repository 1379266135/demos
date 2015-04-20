package com.niu.demos.web;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyWebview extends WebView {
	public ScrollInterface mScrollInterface;

	public MyWebview(Context context) {
		super(context);
	}

	public MyWebview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyWebview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		mScrollInterface.onSChanged(l, t, oldl, oldt);
	}
	
	public void setOnCustomScroolChangeListener(ScrollInterface scrollInterface) {

		this.mScrollInterface = scrollInterface;

	}

	public interface ScrollInterface {

		public void onSChanged(int l, int t, int oldl, int oldt);

	}
}
