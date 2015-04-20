package com.niu.demos.float_window;

import com.niu.demos.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver{
	private final int CHANGE_TYPE_WIFI = 4;
	private final int CHANGE_TYPE_G = 5;
	private final int HIDE_VIEW = 6;
	private final int SHOW_VIEW = 7;
	private FloatView mFloatView;
	private Context mContext;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HIDE_VIEW:
				mFloatView.hideFloatView();
				mFloatView.hideFlowIcon();
				mFloatView.showing();

				break;
				
			case CHANGE_TYPE_WIFI:
				mFloatView.setFlowIcon(R.drawable.comon_flow_wifi);
				break;
				
			case CHANGE_TYPE_G:
				mFloatView.setFlowIcon(R.drawable.comon_flow_g);
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
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = manager.getActiveNetworkInfo();
		Log.d("info", "接到广播了 ：" + (activeInfo == null));
		if(activeInfo == null){
			mHandler.sendEmptyMessage(HIDE_VIEW);
			return ;
		}
		Log.d("info", "接收到的广播 ：" + activeInfo.getTypeName());
		switch (activeInfo.getType()) {
		case ConnectivityManager.TYPE_WIFI:
			mHandler.sendEmptyMessage(CHANGE_TYPE_WIFI);
			break;
		case ConnectivityManager.TYPE_MOBILE:
			mHandler.sendEmptyMessage(CHANGE_TYPE_G);
			break;
		default:
			break;
		}
		
		
		boolean success = false;
		State state = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState(); // 获取网络连接状态
		if (State.CONNECTED == state) { // 判断是否正在使用WIFI网络
			success = true;
			mHandler.sendEmptyMessage(CHANGE_TYPE_WIFI);
		}

		state = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState(); // 获取网络连接状态
		if (State.CONNECTED != state) { // 判断是否正在使用GPRS网络
			success = true;
			mHandler.sendEmptyMessage(CHANGE_TYPE_G);
		}

		if (!success) {
			Toast.makeText(mContext, "网络连接已中断",Toast.LENGTH_LONG).show();
			mHandler.sendEmptyMessage(HIDE_VIEW);
		}
	}

}
