package com.niu.demos.preferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

public final class SettingUtil {
	
	/**
	 * 获取当前网络类型
	 * @param context
	 * @return
	 */
	public static String getCurrentNetType(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if(activeNetworkInfo != null && activeNetworkInfo.isAvailable()){
			switch (activeNetworkInfo.getType()) {
			case ConnectivityManager.TYPE_WIFI:
				return "wifi";
			case ConnectivityManager.TYPE_MOBILE:
				
				switch (activeNetworkInfo.getSubtype()) {
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
					return "2";

				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_B:

					return "3";

				case TelephonyManager.NETWORK_TYPE_LTE:
					return "4";

				default:
					break;
				}

			default:
				break;
			}
			return activeNetworkInfo.getTypeName();
		}
		return "";

	}

	public static final String getPhoneNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String number = tm.getLine1Number();

		if (number == null) {
			return "";
		}
		return number;
	}

	public static final String getPhoneIMSI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = tm.getSubscriberId();
		if (imsi != null)
			return imsi;
		return "8888888888";
	}

	public static boolean isServiceRunning(Context context, String className) {

		boolean isRunning = false;

		ActivityManager activityManager =

		(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		if (!(serviceList.size() > 0)) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}

		}
		return isRunning;

	}

	public static long getNowDate() {

		Long sysTime = System.currentTimeMillis();

		return sysTime;
	}


	public static String getMd5(String str) {

		if (str == null || str.trim().length() < 1) {
			return "";
		}

		try {

			MessageDigest md5 = MessageDigest.getInstance("MD5");

			byte[] strBytes = str.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(strBytes);

			StringBuffer result = new StringBuffer();
			for (byte b : md5Bytes) {
				result.append(Integer.toHexString((b & 0xf0) >>> 4));
				result.append(Integer.toHexString(b & 0x0f));
			}

			return result.toString();
		} catch (Exception e) {

			e.printStackTrace();
			return "";
		}

	}

	public static String getKey() {
		String id = UUID.randomUUID().toString();
		return id;
	}

	public static String getImei(Context context) {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		String ImeiId = tm.getDeviceId();
		return ImeiId;
	}

	public static String getVersion(Context context) {
		return "1.2.6003";
	}

	public static String phonemodel() {

		StringBuilder string = new StringBuilder();

		String phonemodel = string.append(Build.MODEL).toString();
		return phonemodel;

	}

	public static String getChannel(Context context) {

		String channel = "jifeng";

	/*	try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			int channelcode = info.metaData.getInt(FilterConstant.SMS_FILTER_CHANNEL);
			channel = String.format("%010d", channelcode);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}*/
		return channel;
	}


	public static int getVersionCode(Context context) {

		int versionCode = -1;

		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);

			versionCode = packInfo.versionCode;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return versionCode;
	}

	public static String getVersionName(Context context) {

		String versionName = "";

		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);

			versionName = packInfo.versionName;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return versionName;
	}
	
	public static String getSystemVersion(){
		return android.os.Build.VERSION.RELEASE;// 系统版本
	}
	
	
	public static int getSystemSDKINT(){
		return android.os.Build.VERSION.SDK_INT;
	}
	
	
	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toLowerCase();
	}
	
	private static String removeHeadLines(String log){
		log=log.substring(log.indexOf("endflag=head_end")+16, log.length());
		return log;
	}
	
	public static String getMD5ByFile(File file){
		try {
			FileInputStream fis=new FileInputStream(file);
			InputStreamReader isReader = new InputStreamReader(fis);
			BufferedReader bufReader = new BufferedReader(isReader);
			StringBuilder readed=new StringBuilder();
			String line=null;
			while (true) {
				line = bufReader.readLine();
				if (line == null) {
					break;
				} else {
					readed.append(line+"\n");
				}
			}
			if (!readed.toString().contains("endflag=head_end")) {
				return null;
			}
			String removehead=removeHeadLines(readed.toString());
			return getMD5Str(removehead);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	
	}

}
