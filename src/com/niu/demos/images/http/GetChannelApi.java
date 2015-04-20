package com.niu.demos.images.http;

import java.util.List;

import org.json.JSONArray;

import com.niu.demos.images.Top;

public class GetChannelApi extends HttpGetResponse<List<Top>>{

	@Override
	public void setResult(String response) throws Exception {
		this.result = Top.formatArray(new JSONArray(response));
	}
	
	public GetChannelApi(long topId){
		String uri = "http://ws.getcai.com/api/2/ch/popular/";
		if(topId > 0){
			uri = uri + Long.toString(topId) + "/";
		}
		setUrl(uri);
	}

}
