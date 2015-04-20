package com.niu.demos.fileobserver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.os.FileObserver;
import android.util.Log;

public class SDCardObserver extends FileObserver {
	public static final int CHANGES_ONLY = CREATE | DELETE | CLOSE_WRITE
			| MOVE_SELF | MOVED_FROM | MOVED_TO;
	private List<SingleFileObserver> mObservers;
	private String mPath;
	private int mMask;

	public SDCardObserver(String path) {
		this(path, ALL_EVENTS);
		Log.i("info", "监听路径： " + path);
	}

	public SDCardObserver(String path, int mask) {
		super(path, mask);
		this.mPath = path;
		this.mMask = mask;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void startWatching() {
		if (mObservers != null) {
			return;
		}
		mObservers = new ArrayList<SingleFileObserver>();
		Stack stack = new Stack();
		stack.push(mPath);
		while (!stack.isEmpty()) {
			String parent = (String) stack.pop();
			mObservers.add(new SingleFileObserver(parent, mMask));
			File file = new File(parent);
			File[] files = file.listFiles();
			if (null == files) {
				continue;
			}
			for (File f : files) {
				if (f.isDirectory() && !f.getName().equals(".")
						&& !f.getName().equals("..")) {
					stack.push(f.getPath());
				}
			}
		}

		for (SingleFileObserver sfo : mObservers) {
			sfo.startWatching();
		}
	}

	@Override
	public void stopWatching() {
		if (mObservers == null) {
			return;
		}
		for (SingleFileObserver sfo : mObservers) {
			sfo.stopWatching();
		}
		mObservers.clear();
		mObservers = null;
	}

	@Override
	public void onEvent(int event, String path) {
		switch (event) {
		case FileObserver.ACCESS:
			Log.i("info", "ACCESS: " + path);
			break;
		case FileObserver.ATTRIB:
			Log.i("info", "ATTRIB: " + path);
			break;
		case FileObserver.CLOSE_NOWRITE:
			Log.i("info", "CLOSE_NOWRITE: " + path);
			break;
		case FileObserver.CLOSE_WRITE:
			Log.i("info", "CLOSE_WRITE: " + path);
			break;
		case FileObserver.CREATE:
			Log.i("info", "CREATE: " + path);
			break;
		case FileObserver.DELETE:
			Log.i("info", "DELETE: " + path);
			break;
		case FileObserver.DELETE_SELF:
			Log.i("info", "DELETE_SELF: " + path);
			break;
		case FileObserver.MODIFY:
			Log.i("info", "MODIFY: " + path);
			break;
		case FileObserver.MOVE_SELF:
			Log.i("info", "MOVE_SELF: " + path);
			break;
		case FileObserver.MOVED_FROM:
			Log.i("info", "MOVED_FROM: " + path);
			break;
		case FileObserver.MOVED_TO:
			Log.i("info", "MOVED_TO: " + path);
			break;
		case FileObserver.OPEN:
			Log.i("info", "OPEN: " + path);
			break;
		default:
			Log.i("info", "DEFAULT(" + event + "): " + path);
			break;
		}

	}

	class SingleFileObserver extends FileObserver {
		String path;

		public SingleFileObserver(String path) {
			this(path, ALL_EVENTS);
		}

		public SingleFileObserver(String path, int mask) {
			super(path, mask);
			this.path = path;
		}

		@Override
		public void onEvent(int event, String path) {
			String newPath = this.path + "/" + path;
			SDCardObserver.this.onEvent(event, newPath);
		}

	}

}
