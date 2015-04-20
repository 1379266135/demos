package com.niu.demos.notify;

import com.niu.demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class NotificationViewActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		SharedPreferences pre = getSharedPreferences("Notify", Context.MODE_PRIVATE);
//		int viewId = pre.getInt("LayoutId", -1);
		Bundle b = getIntent().getExtras();
		RemoteViews views = (RemoteViews)b.get("Layout");
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.notify_view_layout);
		views.addView(views.getLayoutId(), view);
		View v = views.apply(this, null);
		setContentView(v);
	}
}
