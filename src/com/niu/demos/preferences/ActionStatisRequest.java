package com.niu.demos.preferences;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class ActionStatisRequest extends BaseRequest {

	public ActionStatisRequest(Context context) {
		super(context);

	}

	public ReportBean toRequest(List<ActionBean> params) {

		ReportBean bean = null;

		RequestNetworkUtil util = new RequestNetworkUtil(getContext());

		String url = Request_CMD.PATH + Request_CMD.post_action;
		String respone;
		try {
			
			
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

			JSONArray elementArrs = new JSONArray();
			elementArrs.put(json);
			
			JSONObject jo = getJsonObject();
			jo.put("items", elementArrs);
			respone = util.load_CMD(url, jo);

			if (SmsFilterLog.DEBUG) {
				SmsFilterLog.debugLog("ActionStatisRequest url:" + url);
				SmsFilterLog.debugLog("ActionStatisRequest http respone:"
						+ respone);
			}

			bean = resoluRespone(respone);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bean;
	}

	private ReportBean resoluRespone(String respone) {
		Request_CMD cmd = new Request_CMD();
		return cmd
				.getJsonToBean(getContext(), respone, ReportBean.class);
	}

}
