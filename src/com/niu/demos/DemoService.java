package com.niu.demos;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DemoService extends IntentService{

	public DemoService() {
		super("demo");
		Log.i("info", "DemoService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("info", "onHandleIntent");
	}
}
