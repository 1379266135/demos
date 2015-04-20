package com.niu.demos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SmsLoaderAsync extends AsyncTaskLoader<List<ESms>>{
	private List<ESms> list;
	private Context mContext;

	public SmsLoaderAsync(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	protected void onStartLoading() {
		Log.i("info","UnFilterSmsLoader onStartLoading...");
	     if (list != null) {
	            // If we currently have a result available, deliver it
	            // immediately.
	            deliverResult(list);
	        }

	        if (takeContentChanged() || list == null ) {
	            // If the data has changed since the last time it was loaded
	            // or is not currently available, start a load.
	            forceLoad();
	        }
	}
	
	@Override
	public List<ESms> loadInBackground() {
		Log.i("info","UnFilterSmsLoader loadInBackground...");
		Cursor cursor = null;
		try {
			String[] projection = { "_id", "address", "body", "date", "person" };
			Uri smsUri = Uri.parse("content://sms/inbox");
			cursor = mContext.getContentResolver().query(smsUri, projection, null, null, null);
			
			if (cursor != null && cursor.getCount() > 0) {
				list = new ArrayList<ESms>();
				cursor.moveToFirst();
				while (!cursor.isAfterLast()) {
					long id = cursor.getLong(cursor.getColumnIndex("_id"));
					String number = cursor.getString(cursor.getColumnIndex("address"));
					String smscontent = cursor.getString(cursor.getColumnIndex("body"));
					long datetime = cursor.getLong(cursor.getColumnIndex("date"));

					ESms sms = new ESms();
					sms.setId(id);
					sms.setAddr(number);
					sms.setBody(smscontent);
					sms.setDate(datetime);
					list.add(sms);
					
					cursor.moveToNext();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(cursor != null){
				cursor.close();
				cursor = null;
			}
		}
		
		
		return list;
	}
	
	@Override
	public void deliverResult(List<ESms> data) {
		Log.i("info","UnFilterSmsLoader deliverResult...");
		if(isReset()){
			if(list != null){
				onReleaseResources(data);
			}
		}
		
		List<ESms> oldList = list;
		list = data;
		if(isStarted()){
			super.deliverResult(data);
		}
		
		if(oldList != null){
			onReleaseResources(oldList);
		}
	}
	
	@Override
	protected void onStopLoading() {
		Log.i("info","UnFilterSmsLoader onStopLoading...");
		cancelLoad();
	}
	
	@Override
	public void onCanceled(List<ESms> data) {
		Log.i("info","UnFilterSmsLoader onCanceled...");
		if(list != null){
			onReleaseResources(list);
		}
	}
	
	protected void onReleaseResources(List<ESms> data){
		Log.i("info","UnFilterSmsLoader onReleaseResources...");
	}

}
