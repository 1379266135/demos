package com.niu.demos.notify;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RemoteViews;

public class MyAccesstService extends AccessibilityService{

	@Override
	protected void onServiceConnected() {
		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
		info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
		info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
		info.notificationTimeout = 100;
		setServiceInfo(null);
	}
	
	

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if(event.getParcelableData() instanceof Notification){
			onInterrupt();
			Notification noti = (Notification) event.getParcelableData();
			RemoteViews views = noti.contentView;
//			view.apply(this, );

//            Intent i = new Intent(this, NotificationViewActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.putExtra("Layout", views);
//            startActivity(i);
		}
	}

	@Override
	public void onInterrupt() {
		Log.i("info", "MyAccessibility  onInterrupt .... ");
//		NotificationManager mg = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Object service = getSystemService(Context.NOTIFICATION_SERVICE);  
        Class<?> statusbarManager;
        Method expand = null;
//        Method disable = null;
		try {
			statusbarManager = Class.forName("com.android.server.NotificationManagerService");
	        if (service != null) {  
	        	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
	        		Field[] f = statusbarManager.getDeclaredFields();
	        		for (Field field : f) {
	        			Log.i("info", "属性名称：  " +  field.getName());
//	        			Object o = field.get(field.getName());
					}
	        		Method[] m = statusbarManager.getMethods();
	        		for (Method method : m) {
						Log.i("info", "方法名称：  " +  method.getName());
					}
	        		expand = statusbarManager.getMethod("onClearAllNotifications");
//	        		expand = statusbarManager.getMethod("removeIcon", String.class);
//	        		disable = statusbarManager.getMethod("expandSettingsPanel");
	        	} else {
	        		expand = statusbarManager.getMethod("expand");
	        	}
	            expand.setAccessible(true);
//	            disable.setAccessible(true);
//	            disable.invoke(service);
	            expand.invoke(service);  
	        } 
		} catch (Exception e) {
			e.printStackTrace();
		}  

//		mg.cancelAll();
	}

}
