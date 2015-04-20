package com.niu.demos.float_window;

import android.app.Activity;
import android.os.Bundle;

public class DocumentClassificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DocumentLayout(this));
	}
}
