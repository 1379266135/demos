package com.niu.demos.web;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.niu.demos.R;
import com.niu.demos.adapter.BaseActivity;
import com.niu.demos.web.MyWebview.ScrollInterface;

public class WebviewActivity extends BaseActivity{
	private MyWebview web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_layout);
		web = (MyWebview) findViewById(R.id.layout_web);
		web.getSettings().setJavaScriptEnabled(true);
		web.loadUrl("http://www.baidu.com");
		web.setOnCustomScroolChangeListener(new ScrollInterface() {
			
			@Override
			public void onSChanged(int l, int t, int oldl, int oldt) {
				// TODO Auto-generated method stub
				
			}
		});
		web.setOnTouchListener(new OnTouchListener() {
			float moveY = -1;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					moveY = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					
					if((web.getContentHeight() * web.getScale()) - (web.getHeight()+web.getScrollY()) == 0){  
					       return true;
					}  
					break;
				default:
					break;
				}
				
				return false ;
			}
		});
	}
	
}
