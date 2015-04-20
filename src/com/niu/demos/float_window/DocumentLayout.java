package com.niu.demos.float_window;

import com.niu.demos.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DocumentLayout extends ViewGroup{
	private View documentBg;

	public DocumentLayout(Context context) {
		super(context);
		initView();
	}
	
	@SuppressLint("ResourceAsColor") 
	private void initView(){
		documentBg = new View(getContext());
		documentBg.setBackgroundColor(getResources().getColor(R.color.suite_no));
		addView(documentBg);
		
		Button mButton = new Button(getContext());
		mButton.setText("悬浮框");
		addView(mButton);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
	}

}
