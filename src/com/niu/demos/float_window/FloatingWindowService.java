package com.niu.demos.float_window;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.niu.demos.R;

public class FloatingWindowService extends Service {
	// /** 浮动窗口布局 */
	// ImageView mFloatLayout;
	// /** 窗口加载器 */
	// WindowManager mWindowManager;
	// /** 布局参数 */
	// WindowManager.LayoutParams mParams;
	//
	private final int OPEN_WINDOW = 0;
	private final int CLOSE_WINDOW = 1;
	private final int CHANGE_TEXT = 3;
	private final int CHANGE_TYPE_WIFI = 4;
	private final int CHANGE_TYPE_G = 5;
	private final int HIDE_VIEW = 6;
	private final int SHOW_VIEW = 7;
	private FloatView mFloatView;
	private BroadcastReceiver mReceiver;
	private IntentFilter mFilter;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case OPEN_WINDOW:
				showFloatView();
				break;
			case CLOSE_WINDOW:
				hideFloatView();
				break;

			case HIDE_VIEW:
				mFloatView.hideFlowIcon();
				break;

			case CHANGE_TYPE_WIFI:
				mFloatView.showFlowIcon();
				mFloatView.setFlowIcon(R.drawable.comon_flow_wifi);
				break;

			case CHANGE_TYPE_G:
				mFloatView.showFlowIcon();
				mFloatView.setFlowIcon(R.drawable.comon_flow_g);
				break;

			case CHANGE_TEXT:
				mFloatView.setFlowText("6201 KB/s");
				break;

			case SHOW_VIEW:
				mFloatView.showFlowIcon();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mFloatView = new FloatView(getApplicationContext());
		mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mReceiver = new NetworkReceiver(); 
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mHandler.sendEmptyMessage(OPEN_WINDOW);
		return START_STICKY;
	}

	private void hideFloatView() {
		mHandler.removeMessages(CLOSE_WINDOW);
		mFloatView.hideFloatView();
	}

	private void showFloatView() {

		mFloatView.showing();
		registerReceiver(mReceiver, mFilter);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("info", "service is destroy");
		unregisterReceiver(mReceiver);
	}
	
	private class NetworkReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			boolean success = false;
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeInfo = manager.getActiveNetworkInfo();
			Log.d("info", "接到广播了 ：" + (activeInfo == null));
			if (activeInfo == null || !activeInfo.isAvailable()) {

				Log.d("info", "network is closed...");
				if (mFloatView.isShowing) {
					mHandler.sendEmptyMessageDelayed(HIDE_VIEW, 1000);
				}

			} else {
				Log.d("info", "接收到的广播 ：" + activeInfo.getTypeName());
				State state = manager.getNetworkInfo(
						ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
				if (State.CONNECTED == state) { // 判断是否正在使用WIFI网络
					success = true;
					mHandler.sendEmptyMessageDelayed(CHANGE_TYPE_WIFI, 1000);
				} else {
					state = manager.getNetworkInfo(
							ConnectivityManager.TYPE_MOBILE).getState(); // 获取网络连接状态
					if (State.CONNECTED == state) { // 判断是否正在使用GPRS网络
						success = true;
						mHandler.sendEmptyMessageDelayed(CHANGE_TYPE_G,1000);
					}
				}

			}

			if (!success) {
				Toast.makeText(getApplicationContext(), "网络连接已中断",Toast.LENGTH_LONG).show();
			}
		}
		
	}

}
