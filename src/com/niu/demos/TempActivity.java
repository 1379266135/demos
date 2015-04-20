package com.niu.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TempActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp);
		findViewById(R.id.temp_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.temp_button:
			Intent mIntent = new Intent(this, DemosActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
			finish();
			break;

		default:
			break;
		}
	}
}
