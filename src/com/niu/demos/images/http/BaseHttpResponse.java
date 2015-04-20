package com.niu.demos.images.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.util.Log;

public abstract class BaseHttpResponse<T> {

	protected T result;

	private Context mContext;
	private String mUrl;
	private DefaultHttpClient mHttpClient;

	public T getResult() {
		return result;
	}

	public abstract void setResult(String response) throws Exception;

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
	}

	public DefaultHttpClient getHttpClient() {
		return mHttpClient;
	}

	public void setHttpClient() {
		this.mHttpClient = getThreadSafeHttpClient();
	}
	
	private DefaultHttpClient getThreadSafeHttpClient(){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		ClientConnectionManager conn = httpClient.getConnectionManager();
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, HttpSetting.HTTP_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, HttpSetting.HTTP_SO_TIMEOUT);
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, conn.getSchemeRegistry()), params);
		
		return httpClient;
	}
	
	protected String getResponse(HttpResponse response) throws Exception{
		HttpEntity entity = response.getEntity();
		String result = getResponseFromEntity(entity);
		 Log.i("info", "service response data: " + result);
		 
		 int responseCode = response.getStatusLine().getStatusCode();
		 String error = responseCode +":"+result;
		 switch (responseCode) {
		case 200:
			break;
		default:
			throw new Exception(error);
		}
		return result;
	}
	
	private String getResponseFromEntity(HttpEntity entity) throws Exception{
		StringBuffer buffer = new StringBuffer();
		if(entity != null){
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"),8192);
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
		}
		return buffer.toString();
	}

}
