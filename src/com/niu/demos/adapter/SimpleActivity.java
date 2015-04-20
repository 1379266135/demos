package com.niu.demos.adapter;

import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.niu.demos.R;

public class SimpleActivity extends BaseActivity implements LoaderCallbacks<List<ESms>>{
	private SmsLoaderAsync loader;
	private SmsAdapter mAdapter;
	private ListView mListView;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg != null){
				switch (msg.what) {
				case 1:
					Log.i("info", "message ---> " + ((ESms)msg.obj).getBody());
					break;

				default:
					break;
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_layout);
		mListView = viewFinder.viewFinder(R.id.list);
		this.getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<List<ESms>> onCreateLoader(int arg0, Bundle arg1) {
		loader = new SmsLoaderAsync(this);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<List<ESms>> smsLoader, List<ESms> smsList) {
		Log.i("info", "result = " + smsList.size());
		if (smsList != null) {
			mAdapter = new SmsAdapter(this, smsList, mHandler);
			mListView.setAdapter(mAdapter);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<ESms>> arg0) {
		if(mAdapter != null){
			mAdapter.clearData();
		}
	}
}
