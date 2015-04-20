package com.niu.demos.images.runnable;

import java.util.List;

import org.apache.http.HttpMessage;

import com.niu.demos.images.Top;
import com.niu.demos.images.http.GetChannelApi;
import com.niu.demos.images.http.HttpSetting;

public class GetChannelRunnable extends BaseRunnable{

	private long mTopId;
	
	public GetChannelRunnable(long topId){
		mTopId = topId;
	}
	
	@Override
	public void run() {
		obtainMessage(HandlerMessage.MSG_PREPARE);
		try {
			GetChannelApi api = new GetChannelApi(mTopId);
			api.handleHttpGet();
			List<Top> tops = api.getResult();
			obtainMessage(HandlerMessage.MSG_SUCCESS, tops);
		} catch (Exception e) {
			obtainMessage(HandlerMessage.MSG_FAILURE);
			e.printStackTrace();
		}
	}

}
