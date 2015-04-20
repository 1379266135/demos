package com.niu.demos.preferences;

import org.json.JSONObject;

import android.content.Context;

public class BaseRequest {

	private Context mContext;
	private JSONObject mJsonObject;

	public BaseRequest(Context context) {

		mContext = context.getApplicationContext();

		mJsonObject = new JSONObject();

		getPublicJsonObject();

	}

	private void getPublicJsonObject() {

//		String imei = SettingUtil.getImei(mContext);
//		String version = SettingUtil.getVersion(mContext);
//		String channel = SettingUtil.getChannel(mContext);
//		String product = Build.MODEL;
//		//String sysVer = SettingUtil.getSystemVersion();
//		int sdkinit = SettingUtil.getSystemSDKINT();
//		try {
//			mJsonObject.put("i", imei);
//			mJsonObject.put("v", version);
//			mJsonObject.put("c", channel);
//			mJsonObject.put("p", product);
//			//mJsonObject.put("w", channel);
//			mJsonObject.put("s", sdkinit);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

	}

	protected JSONObject getJsonObject() {
		return mJsonObject;
	}

	protected Context getContext() {
		return mContext;
	}

}
