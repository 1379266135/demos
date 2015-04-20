package com.niu.demos.images;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Top implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String TOP_ID = "pic_id";
	public static final String PIC_NAME = "pic_name";

	private long mTopId;
	private String picName;

	public long getmTopId() {
		return mTopId;
	}

	public void setmTopId(long mTopId) {
		this.mTopId = mTopId;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public static List<Top> formatArray(JSONArray jsonArray) throws JSONException{
		List<Top> tops = new ArrayList<Top>();
		for (int i = 0; i < jsonArray.length(); i ++) {
			Top top = new Top();
			JSONObject json = jsonArray.getJSONObject(i);
			if(!json.isNull(TOP_ID)){
				top.mTopId = json.getLong(TOP_ID);
			}
			if(!json.isNull(PIC_NAME)){
				top.picName = json.getString(PIC_NAME);
			}
			tops.add(top);
		}
		return tops;
	}
}
