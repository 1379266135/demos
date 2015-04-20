package com.niu.demos.preferences;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * 用于网络请求
 * 
 * @author Administrator
 * 
 */
public class RequestNetworkUtil {
	private Context mContex;

	public RequestNetworkUtil(Context context) {
		this.mContex = context;
	}

	public String load_CMD(String url, JSONObject json) throws Exception {

		String requestParam = MergeparamsByJson(json).replaceAll("\n", "");

		String urlStr = DataEntry.urlJiaMi(requestParam, url); // 请求头中的参数

		return connectByPost(urlStr, requestParam);
	}

	/**
	 * 获取固定参数
	 * 
	 * @return
	 * @throws JSONException
	 */
	private JSONObject getMustParams(JSONObject json) throws JSONException {

		String product = Build.MODEL;
		int sdkinit = SettingUtil.getSystemSDKINT();

		json.put("i", SettingUtil.getImei(mContex));
		json.put("v", SettingUtil.getVersion(mContex));
		json.put("s", sdkinit);
		json.put("p", product);
		json.put("c", SettingUtil.getChannel(mContex));

		return json;
	}

	/**
	 * 直接通过json拼接参数
	 * 
	 * @param paramJson
	 *            由调用者创建的json，其中封装了需要传递的接口参数
	 * @return
	 * @throws Exception
	 */
	private String MergeparamsByJson(JSONObject paramJson) throws Exception {
		return EncrypDES.getInstance().Encrytor(
				getMustParams(paramJson).toString());
	}

	/**
	 * 解析返回的Json数据，并封装到JavaBean中
	 * 
	 * @param jsonStr
	 * @param beanclass
	 * @return
	 */
	public <T> T Json2Bean(String jsonStr, Class<T> beanclass) {
		if (jsonStr == null) {
			return null;
		}

		JSONObject json = null;
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getJsonObjectToBean(json, beanclass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> T getJsonObjectToBean(JSONObject obj, Class<T> beanclass) {
		HashMap<String, Method> methodNames = new HashMap<String, Method>();
		T bb = null;
		try {
			bb = beanclass.newInstance();
			Method[] methods = beanclass.getDeclaredMethods();
			for (int j = 0; j < methods.length; j++) {
				String methodname = methods[j].getName();
				if (methodname.contains("set")) {
					methodNames.put(
							tofirstLowerCase(methodname.substring(3,
									methodname.length())), methods[j]);
				}
			}
			// methodNames.remove("pagememolist");
			for (Entry<String, Method> entry : methodNames.entrySet()) {
				if ((obj.has(entry.getKey()) || obj.has(tofirstUpperCase(entry
						.getKey())))) {
					String fildname = entry.getKey();
					Object obje = obj.opt(entry.getKey());
					if (obje == null) {
						obje = obj.opt(tofirstUpperCase(entry.getKey()));
						fildname = tofirstUpperCase(entry.getKey());
					}

					if (obje == null || obje == JSONObject.NULL) {
						continue;
					}
					if (obje instanceof JSONArray) {
						JSONArray arr = (JSONArray) obje;
						Field field = beanclass.getDeclaredField(fildname);
						ParameterizedType ptype = (ParameterizedType) field
								.getGenericType();
						Type[] type = ptype.getActualTypeArguments();
						Class subbeanclass = (Class) type[0];
						ArrayList sublist = new ArrayList();
						for (int i = 0; i < arr.length(); i++) {
							Object subobj = arr.get(i);
							if (subobj instanceof JSONObject) {
								Object subbean = getJsonObjectToBean(
										(JSONObject) subobj, subbeanclass);
								sublist.add(subbean);
							} else {
								sublist.add(subobj);
							}
						}
						entry.getValue().invoke(bb, sublist);
					} else if (obje instanceof JSONObject) {
						Field field = beanclass.getDeclaredField(fildname);
						Type type = field.getGenericType();
						Class subbeanclass = (Class) type;
						Object subobj = getJsonObjectToBean((JSONObject) obje,
								subbeanclass);
						entry.getValue().invoke(bb, subobj);
					} else {
						try {
							entry.getValue().invoke(bb, obje);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bb;
	}

	public String tofirstLowerCase(String str) {
		if (str != null && str.length() > 0) {
			return str.substring(0, 1).toLowerCase()
					+ str.substring(1, str.length());
		} else {
			return str;
		}
	}

	public String tofirstUpperCase(String str) {

		if (str != null && str.length() > 0) {
			return str.substring(0, 1).toUpperCase()
					+ str.substring(1, str.length());
		} else if (str != null && str.length() == 0) {
			return str.toUpperCase();
		} else {
			return str;
		}

	}

	private String connectByPost(String url, String postParams)
			throws Exception {

		String resultStr = "";
		String encoding = "UTF-8";
		OutputStream outStream = null;
		HttpURLConnection conn = null;

		ConnectivityManager connectivityManager = (ConnectivityManager) mContex
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
			throw new Error("网络不可用。");
		}

		try {

			String pramsP = "n=" + URLEncoder.encode(postParams, "UTF-8");

			URL Url = new URL(url);
			conn = (HttpURLConnection) Url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(15 * 1000);
			conn.setReadTimeout(5 * 1000);

			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream(), "utf-8");

			out.write(pramsP);
			out.flush();
			out.close();

			if (conn.getResponseCode() == 200) {
				InputStream inStream = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						inStream));
				StringBuilder resultBuilder = new StringBuilder();
				String tmp = "";
				while ((tmp = br.readLine()) != null) {

					resultBuilder.append(tmp);
				}

				resultStr = resultBuilder.toString();
				if (SmsFilterLog.DEBUG) {
					SmsFilterLog.debugLog("return json data=   " + resultStr);
				}


				resultStr = new String(EncrypDES.getInstance()
						.Decryptor(resultStr).getBytes(), "GBK");
				if(SmsFilterLog.DEBUG){
					SmsFilterLog.debugLog("Decode json data=   " + resultStr);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return resultStr;

	}

}
