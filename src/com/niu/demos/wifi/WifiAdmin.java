package com.niu.demos.wifi;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Configuration;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

public class WifiAdmin {
	private static final String TAG = "[WifiAdmin]";
	public WifiManager mWifiManager;
	private WifiInfo mWifiInfo;
	private List<ScanResult> mWifiList = null;
	private List<WifiConfiguration> mWifiConfiguration;
	private WifiLock mWifiLock;
	private DhcpInfo dhcpInfo;

	public WifiAdmin(Context context) {
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	public boolean openWifi() {// 打开wifi
		if (!mWifiManager.isWifiEnabled()) {
			Log.i(TAG, "setWifiEnabled.....");
			mWifiManager.setWifiEnabled(true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i(TAG, "setWifiEnabled.....end");
		}
		return mWifiManager.isWifiEnabled();
	}

	public void closeWifi() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	public int checkState() {
		return mWifiManager.getWifiState();
	}

	public void acquireWifiLock() {// 锁定wifiLock
		mWifiLock.acquire();
	}

	public void releaseWifiLock() {// 解锁wifiLock
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	public void creatWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("Test");
	}

	public List<WifiConfiguration> getConfiguration() {
		return mWifiConfiguration;
	}

	public void connectConfiguration(int index) {// 指定配置好的网络进行连接
		if (index > mWifiConfiguration.size()) {
			return;
		}
		mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
				true);
	}

	public void startScan() {// wifi扫描
		boolean scan = mWifiManager.startScan();
		Log.i(TAG, "startScan result:" + scan);
		mWifiList = mWifiManager.getScanResults();
		mWifiConfiguration = mWifiManager.getConfiguredNetworks();

		if (mWifiList != null) {
			Log.i(TAG, "startScan result:" + mWifiList.size());
			Log.i("TAG", "WifiConfiguration result: " + mWifiConfiguration.size());
			for (int i = 0; i < mWifiList.size(); i++) {
				ScanResult result = mWifiList.get(i);
				Log.i(TAG, "startScan result[" + i + "]" + result.SSID + ","
						+ result.BSSID + ", " + result.capabilities);
			}
			for(int i = 0; i < mWifiConfiguration.size(); i++){
				WifiConfiguration config = mWifiConfiguration.get(i);
				Log.i(TAG, "WifiConfiguration result[" + i + "]" + config.SSID + ","
						+ config.BSSID + " , " + config.networkId);
			}
			Log.i(TAG, "startScan result end.");
		} else {
			Log.i(TAG, "startScan result is null.");
		}

	}

	public List<ScanResult> getWifiList() {
		return mWifiList;
	}
	
	public HashMap<String, ArrayList<ScanResult>> getFreeAndPwdWiFi(){
		HashMap<String, ArrayList<ScanResult>> map = new HashMap<String, ArrayList<ScanResult>>();
		if(mWifiList != null && mWifiList.size() > 0){
			ArrayList<ScanResult> freeList = new ArrayList<ScanResult>();
			ArrayList<ScanResult> pwdList = new ArrayList<ScanResult>();
			Collections.sort(mWifiList, new Comparator<ScanResult>() {

				@Override
				public int compare(ScanResult object1, ScanResult object2) {
					return Math.abs(object1.level) > Math.abs(object2.level) ? 1 : -1;
				}
			});
			for (ScanResult result : mWifiList) {
				if(isNeedPwd(result)){
					pwdList.add(result);
				} else {
					freeList.add(result);
				}
			}
			map.put("免费WiFi", freeList);
			map.put("需要密码", pwdList);
		}
		return map;
	}
	
	private boolean isNeedPwd(ScanResult scan) {
		if (scan != null) {
			if (scan.capabilities.contains("WEP")
					|| scan.capabilities.contains("PSK")
					|| scan.capabilities.contains("EAP")) {

				return true;
			}
		}
		return false;
	}

	public StringBuilder lookUpScan() {// 查看扫描结果
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < mWifiList.size(); i++) {
			stringBuilder
					.append("Index_" + new Integer(i + 1).toString() + ":");
			stringBuilder.append((mWifiList.get(i)).toString());
			stringBuilder.append("/n");
		}
		return stringBuilder;
	}

	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	public DhcpInfo getDhcpInfo() {
		return dhcpInfo = mWifiManager.getDhcpInfo();
	}

	public int getIPAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	public int getNetworkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	public WifiInfo getWifiInfo() {
		mWifiInfo = mWifiManager.getConnectionInfo();
		return mWifiInfo;
	}

	public void addNetwork(WifiConfiguration wcg) { // 添加一个网络配置并连接
		int wcgID = mWifiManager.addNetwork(wcg);
		boolean b = mWifiManager.enableNetwork(wcgID, true);
		System.out.println("addNetwork--" + wcgID);
		System.out.println("enableNetwork--" + b);
	}

	public void disconnectWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}
	
    public  int getSecurity(ScanResult result) {
        if (result.capabilities.contains("WEP")) {
            return 1;
        } else if (result.capabilities.contains("PSK")) {
            return 2;
        } else if (result.capabilities.contains("EAP")) {
            return 3;
        }
        return 0;
    }
    
    public int getSecurity(WifiConfiguration config) {
        if (config.allowedKeyManagement.get(KeyMgmt.WPA_PSK)) {
            return 2;
        }
        if (config.allowedKeyManagement.get(KeyMgmt.WPA_EAP) ||
                config.allowedKeyManagement.get(KeyMgmt.IEEE8021X)) {
            return 3;
        }
        return (config.wepKeys[0] != null) ? 1 : 0;
    }

	public WifiConfiguration CreateWifiInfo(String SSID, String Password,
			int Type) {
		Log.i(TAG, "SSID:" + SSID + ",password:" + Password);
		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";

		WifiConfiguration tempConfig = this.IsExsits(SSID);

		if (tempConfig != null) {
			mWifiManager.removeNetwork(tempConfig.networkId);
		} else {
			Log.i(TAG, "IsExsits is null.");
		}

		if (Type == 1) // WIFICIPHER_NOPASS
		{
			Log.i(TAG, "Type =1.");
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 2) // WIFICIPHER_WEP
		{
			Log.i(TAG, "Type =2.");
			config.hiddenSSID = true;
			config.wepKeys[0] = "\"" + Password + "\"";
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers
					.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (Type == 3) // WIFICIPHER_WPA
		{

			Log.i(TAG, "Type =3.");
			config.preSharedKey = "\"" + Password + "\"";

			config.hiddenSSID = true;
			config.allowedAuthAlgorithms
					.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.TKIP);
			// config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedPairwiseCiphers
					.set(WifiConfiguration.PairwiseCipher.CCMP);
			config.status = WifiConfiguration.Status.ENABLED;
		}
		return config;
	}

	private WifiConfiguration IsExsits(String SSID) { // 查看以前是否已经配置过该SSID
		List<WifiConfiguration> existingConfigs = mWifiManager
				.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}
}
