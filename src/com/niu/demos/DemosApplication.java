package com.niu.demos;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Application;
import android.content.Context;

public class DemosApplication extends Application{

	public static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
	    SQLiteDatabase.loadLibs(this);
	}
}
