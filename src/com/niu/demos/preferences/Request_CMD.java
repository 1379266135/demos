package com.niu.demos.preferences;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class Request_CMD {

	public static final String PATH = "http://sdk.12321.at321.cn/";
	public static final String post_action = "sdk/v1/behavior/?"; // 行为统计

	public static class ErrorCode {
		public static final int CODE_SUCCESS = 0;
		public static final int CODE_FAILED = -1;
		public static final int CODE_JSON_FORMAT_ERROR = -2;
		public static final int CODE_SIGN_ERROR = -22;
	}

	public Request_CMD() {
	}

	/**
	 * 将返回的json数据进行解析，并封装到JavaBean中
	 * 
	 * @param context
	 * @param jsonStr
	 *            返回的json数据
	 * @param beanclass
	 *            自定义的解析后的结果bean
	 * @return
	 */
	public <T> T getJsonToBean(Context context, String jsonStr,
			Class<T> beanclass) {
		RequestNetworkUtil util = new RequestNetworkUtil(context);
		return util.Json2Bean(jsonStr, beanclass);
	}

	public String actionStatistics(Context context, ArrayList<ActionBean> params)
			throws Exception {

		String url = PATH + post_action;
		RequestNetworkUtil util = new RequestNetworkUtil(context);

		JSONObject json = new JSONObject();
		JSONArray arrs = new JSONArray();
		int i = 0;
		for (ActionBean action : params) {
			JSONObject j = new JSONObject();
			j.put("acname", action.getAcname());
			j.put("count", action.getCount());
			j.put("date", action.getDate());
			arrs.put(i, j);
			i++;
		}
		json.put("actionlist", arrs);

		JSONObject element = new JSONObject();
		JSONArray elementArrs = new JSONArray();
		elementArrs.put(json);
		element.put("items", elementArrs);

		if (SmsFilterLog.DEBUG) {
			SmsFilterLog.debugLog(element.toString());
		}

		return util.load_CMD(url, element);
	}

}
