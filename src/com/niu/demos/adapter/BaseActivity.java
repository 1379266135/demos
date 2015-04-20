package com.niu.demos.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity{
	public ViewFinder viewFinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewFinder = new ViewFinder(this);
	}
	
	public class ViewFinder{
		private View view;
		private Activity context;
		
		public ViewFinder(View v){
			this.view = v;
		}
		
		public ViewFinder(Activity context){
			this.context = context;
		}
		
		@SuppressWarnings("unchecked")
		public final <E extends View> E viewFinder(int resId){
			if(null == view){
				return (E) context.findViewById(resId);
			}
			return (E)view.findViewById(resId);
		}
	}

}
