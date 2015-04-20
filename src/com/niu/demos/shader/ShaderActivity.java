package com.niu.demos.shader;

import android.app.Activity;
import android.os.Bundle;

public class ShaderActivity extends Activity {
	private BitmapShaderView shaderView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shaderView = new BitmapShaderView(this);
		setContentView(shaderView);
	}
}
