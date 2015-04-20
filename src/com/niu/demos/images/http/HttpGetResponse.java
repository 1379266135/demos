package com.niu.demos.images.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

public abstract class HttpGetResponse<T> extends BaseHttpResponse<T>{

	public void handleHttpGet() throws Exception{
		setHttpClient();
		try {
			HttpGet httpGet = new HttpGet(getUrl());
			httpGet.addHeader("Accept", "application/json");
			HttpResponse response = getHttpClient().execute(httpGet);
			String result = getResponse(response);
			setResult(result);
		} catch (Exception e) {
			throw e;
		} finally{
			getHttpClient().getConnectionManager().shutdown();
		}
	}
}
