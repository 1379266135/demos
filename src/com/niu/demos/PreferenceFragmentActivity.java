package com.niu.demos;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

@SuppressLint("NewApi") 
public class PreferenceFragmentActivity extends FragmentActivity{
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.sms_activity_filtersetting);

		getFragmentManager().beginTransaction()
				.replace(R.id.content_fragment, new CheckPreferenceFragment())
				.commit();
	}
}
