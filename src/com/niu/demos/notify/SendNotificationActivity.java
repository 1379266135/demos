package com.niu.demos.notify;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RemoteViews;

import com.niu.demos.DemosActivity;
import com.niu.demos.R;

public class SendNotificationActivity extends Activity{
	NotificationManager manager;
//	MyReceiver receiver;
	Builder builder;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_notification_layout);
		
//		receiver = new MyReceiver();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction("com.niu.demos");
//		registerReceiver(receiver, filter);
		
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(this);
		builder.setAutoCancel(true);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker(".....");
		
//		RemoteViews view = new RemoteViews(getPackageName(), R.layout.notify_view_layout);
//		builder.setContent(view);
		
//		Intent btnIntent = new Intent("com.niu.demos");
//		PendingIntent btnReceiver = PendingIntent.getBroadcast(this, 0, btnIntent, 0);
//		view.setOnClickPendingIntent(R.id.notify_btn, btnReceiver);
		
		Intent i = new Intent(this, DemosActivity.class);
		i.setAction(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);     
		
		PendingIntent pending = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pending);
//		manager.notify(0, builder.build());
//		n.setLatestEventInfo(this, "test", "this is new message", pending);
		
		findViewById(R.id.btn_send).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.notify(0, builder.build());
			}
		});
	}
	
//	class MyReceiver extends BroadcastReceiver{
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if("com.niu.demos".equals(action)){
//				Log.i("info", "这里是广播： " + action);
//			}
//		}
//		
//	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		if(receiver != null){
//			unregisterReceiver(receiver);
//		}
	}
}
