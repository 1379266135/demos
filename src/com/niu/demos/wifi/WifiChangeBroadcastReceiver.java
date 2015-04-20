package com.niu.demos.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiChangeBroadcastReceiver extends BroadcastReceiver{
	private Context mContext;
	private WifiManager mWifiManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		mWifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
        if (!mWifiManager.isWifiEnabled()) {
//            mScanner.pause();
            return;
        }

//        if (state == DetailedState.OBTAINING_IPADDR) {
//            mScanner.pause();
//        } else {
//            mScanner.resume();
//        }

		getWifiInfo();
	}
	
	private void getWifiInfo() {

        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        if (wifiInfo.getBSSID() != null) {
        	//wifi名称
            String ssid = wifiInfo.getSSID();
            //wifi信号强度
            int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
            Log.i("info", "BroadCastReceiver = " + signalLevel);
            //wifi速度
            int speed = wifiInfo.getLinkSpeed();
            //wifi速度单位
            String units = WifiInfo.LINK_SPEED_UNITS;
            System.out.println("ssid="+ssid+",signalLevel="+signalLevel+",speed="+speed+",units="+units);
        }
   }
	
}
