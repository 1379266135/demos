package com.niu.demos;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class DemoWebviewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_webview);
		WebView view = (WebView) findViewById(R.id.web_view);
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl("file:///android_asset/sms_filter_about.html");
	}
}
