package com.niu.demos.notify;

import com.niu.demos.R;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.widget.RemoteViews;

public class NotificationListener extends NotificationListenerService {
	public int layoutId = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onNotificationPosted(StatusBarNotification sbn) {
		
//		cancelNotification(sbn.getPackageName(), sbn.getTag(), sbn.getId());
		StatusBarNotification[] statusbar = getActiveNotifications();
		
		String packageName = sbn.getPackageName();
		
		int resId = sbn.getId();
		Notification n = sbn.getNotification();
		RemoteViews views = n.contentView;
		RemoteViews v = new RemoteViews(packageName, R.layout.notify_view_layout);
		n.contentView = v;
//		views.addView(views.getLayoutId(), v);
		if(views == null){
			views = n.bigContentView;
		}
		
		if(views == null){
			views = n.tickerView;
		}
		
//		SharedPreferences pre = getSharedPreferences("Notify", Context.MODE_PRIVATE);
//		Editor editor = pre.edit();
//		
//		String tag = sbn.getTag();
//		Log.d("info", "通知栏包名： " + packageName);
//		Log.d("info", "通知栏ID： " + resId);
//		Log.d("info", "通知栏View： " + views.getLayoutId());
//		Log.d("info", "通知栏Tag： " + tag);
//		editor.putInt("LayoutId", views.getLayoutId());
		
        if (n!=null){
//            Bundle extras = mNotification.extras;
//
//            Intent intent = new Intent(NotificationFilterActivity.INTENT_ACTION_NOTIFICATION);
//            intent.putExtras(mNotification.extras);
//            sendBroadcast(intent);
//
//            Notification.Action[] mActions=mNotification.actions;
//            if (mActions!=null){
//                for (Notification.Action mAction:mActions){
//                    int icon=mAction.icon;
//                    CharSequence actionTitle=mAction.title;
//                    PendingIntent pendingIntent=mAction.actionIntent;
//                }
//            }
//            Intent view = new Intent(this, NotificationViewActivity.class);
//            view.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            view.putExtra("Layout", views);
//            startActivity(view);
        }
		
	}

	@Override
	public void onNotificationRemoved(StatusBarNotification sbn) {
		
	}
}
