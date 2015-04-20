package com.niu.demos.preferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class IntentSubPreference extends Preference{
	
	public IntentSubPreference(Context context){
		super(context);
	}
	
	public IntentSubPreference(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	public IntentSubPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.onCreateView(parent);
	}

	@Override
	protected void onBindView(View view) {
		// TODO Auto-generated method stub
		super.onBindView(view);
	}

	
}
