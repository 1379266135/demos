package com.niu.demos.images.runnable;

import android.os.Handler;
import android.widget.Toast;

public abstract class BaseRunnable implements Runnable {

	private Handler mHandler;

	public Handler getHanlder() {
		return mHandler;
	}

	public void setHandler(Handler targetHandler) {
		mHandler = targetHandler;
	}

	public void obtainMessage(int what) {
		if (mHandler != null) {
			mHandler.obtainMessage(what).sendToTarget();
		}
	}

	public void obtainMessage(int what, Object object) {
		if (mHandler != null) {
			mHandler.obtainMessage(what, object).sendToTarget();
		}
	}
}
