package com.niu.demos;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.BitmapFactory.Options;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.niu.demos.adapter.BaseActivity;
import com.niu.demos.database.SQLDemoActivity;
import com.niu.demos.fileobserver.SDcardActivity;
import com.niu.demos.float_window.FloatingWindowService;
import com.niu.demos.notify.MyAccesstService;
import com.niu.demos.notify.NotificationFilterActivity;
import com.niu.demos.notify.SendNotificationActivity;
import com.niu.demos.shader.ProgressPieActivity;
import com.niu.demos.sliding.PagerSildingActivity;
import com.niu.demos.web.WebviewActivity;
import com.niu.demos.weight.ViewgroupActivity;
import com.niu.demos.wifi.SettingWifiActivity;
import com.niu.demos.wifi.WifiActivity;

public class MainActivity extends BaseActivity implements OnClickListener {

	private Button mButton, mWeb, mfloating, myViewgroup, mObserver, mWifi, setting_wifi, mShader, mPager, mweb, mNotify;
	private Button mPic;
	private MyScrollView mLayout;
	private boolean isShow;
	private int i = 0;
	private ArrayList<Bean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Options mOptions = new Options();
		mOptions.inPurgeable = true;
		mOptions.inInputShareable = true;
		
		File SDFile = Environment.getExternalStorageDirectory();
		File file = new File(SDFile.getAbsolutePath());
		file.mkdir();
		Log.i("info", "文件数量 Name ： " + file.getName());
		Log.i("info", "文件数量 Parent ： " + file.getParent());
		Log.i("info", "文件数量 ParentFile ： " + file.getParentFile());
		Log.i("info", "文件数量 list： " + file.list());
		Log.i("info", "文件数量 listFiles： " + file.listFiles());
		if (file.listFiles() != null) {
			for (File f : file.listFiles()) {
				Log.i("info", "listRoots name : " + f.getName());
			}
		}
		Log.i("info", "文件数量 listRoots()： " + file.listRoots());

		setContentView(R.layout.activity_main);
		
//		String value = "com.niu.demos/com.niu.demos.notify.NotificationListener";
//		Settings.Secure.putString(getContentResolver(), "enabled_notification_listeners", value);
//		putString(getContentResolver(), Settings.Secure.CONTENT_URI, "enabled_notification_listeners", value);
		
//		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		manager.notifyAll();
		
		viewFinder.viewFinder(R.id.database).setOnClickListener(this);
		
		mPager = viewFinder.viewFinder(R.id.pager);
		mPager.setOnClickListener(this);
		
		mButton = (Button) findViewById(R.id.btn_scroll);
		mButton.setOnClickListener(this);
		mWeb = (Button) findViewById(R.id.start_web);
		mWeb.setOnClickListener(this);
		
		mShader = viewFinder.viewFinder(R.id.shader);
		mShader.setOnClickListener(this);
		
		mObserver = viewFinder.viewFinder(R.id.start_observer);
		mObserver.setOnClickListener(this);

		mfloating = viewFinder.viewFinder(R.id.start_floating);
		mfloating.setOnClickListener(this);

		myViewgroup = viewFinder.viewFinder(R.id.start_list);
		myViewgroup.setOnClickListener(this);
		
		mWifi = viewFinder.viewFinder(R.id.start_wifi);
		mWifi.setOnClickListener(this);
		
		setting_wifi = viewFinder.viewFinder(R.id.setting_wifi);
		setting_wifi.setOnClickListener(this);
		
		mweb = viewFinder.viewFinder(R.id.web_layout);
		mweb.setOnClickListener(this);
		
		findViewById(R.id.noti_layout).setOnClickListener(this);
		
		mNotify = (Button) findViewById(R.id.notification_layout);
		mNotify.setOnClickListener(this);

		mLayout = (MyScrollView) findViewById(R.id.scroll_layout);
		list = new ArrayList<MainActivity.Bean>();
		for (int i = 0; i < 10; i++) {
			Bean bean = new Bean();
			bean.acname = "点击" + i;
			bean.count = "数量" + i;
			bean.date = "时间" + i;
			list.add(bean);
		}
		
		mPic = (Button)findViewById(R.id.select_pic);
		mPic.setOnClickListener(this);

	}

	class Bean {
		String count;
		String acname;
		String date;
	}

	private boolean putString(ContentResolver resolver, Uri uri, String name,
			String value) {

/*		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		if (!locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {

			Intent gpsIntent = new Intent();
			gpsIntent.setClassName("com.android.settings","com.android.settings.widget.SettingsAppWidgetProvider");
			gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
			gpsIntent.setData(Uri.parse("custom:3"));
			try {
				PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
			} catch (CanceledException e) {
				e.printStackTrace();
			}

		}*/

		// The database will take care of replacing duplicates.
		try {
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("value", value);
			resolver.insert(uri, values);
			return true;
		} catch (SQLException e) {
			Log.w("info", "Can't set key " + name + " in " + uri, e);
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_scroll:
			// Intent intent = new Intent(this, DemoService.class);
			// startService(intent);
			
			if (i > 0) {
				isShow = false;
				i--;
			} else {
				i++;
				isShow = true;
			}

			mLayout.isShowContent(isShow);
			if (isShow) {
				isShow = !isShow;
			} else {
				new Thread() {
					public void run() {
						try {
							Thread.sleep(500);
						} catch (Exception e) {
							e.printStackTrace();
						}
						runOnUiThread(new Runnable() {
							public void run() {
								isShow = !isShow;
							}
						});
					};
				}.start();
			}
//			Intent intent = new Intent(this, GooglePullRefresh.class);
//			startActivity(intent);
//			finish();
			break;
		case R.id.start_web:
			Intent mIntent = new Intent(this, DemoWebviewActivity.class);
			startActivity(mIntent);
			break;

		case R.id.start_floating:
			Intent floatIntent = new Intent(this, FloatingWindowService.class);
			startService(floatIntent);
			// finish();
			break;

		case R.id.start_list:
			Intent viewgroupIntent = new Intent(this, ViewgroupActivity.class);
			startActivity(viewgroupIntent);
			break;
			
		case R.id.start_observer:
			Intent observer = new Intent(this, SDcardActivity.class);
			startActivity(observer);
			break;
			
		case R.id.start_wifi:
			Intent wifi = new Intent(this, WifiActivity.class);
			startActivity(wifi);
			break;
			
		case R.id.setting_wifi:
			Intent setting = new Intent(this, SettingWifiActivity.class);
			startActivity(setting);
			break;
			
		case R.id.shader:
			Intent shader = new Intent(this, ProgressPieActivity.class);
			startActivity(shader);
			break;
			
		case R.id.pager:
			Intent pager = new Intent(this, PagerSildingActivity.class);
			startActivity(pager);
			break;
		case R.id.database:
			Intent database = new Intent(this, SQLDemoActivity.class);
			startActivity(database);
			break;
			
		case R.id.web_layout:
			Intent web = new Intent(this, WebviewActivity.class);
			startActivity(web);
			break;
			
		case R.id.noti_layout:
			Intent noti = new Intent(this, NotificationFilterActivity.class);
//			noti.setAction("")
			startActivity(noti);
			break;
		case R.id.notification_layout:
			Intent i = new Intent(this, SendNotificationActivity.class);
			startActivity(i);
//			Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//			startActivity(intent);
			break;
			
		case R.id.select_pic:
			Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
			innerIntent.setType("image/*");
			Intent wrapperIntent = Intent.createChooser(innerIntent, null);

            startActivityForResult(wrapperIntent, 100);
			break;
		default:
			break;

		}
	}

}
