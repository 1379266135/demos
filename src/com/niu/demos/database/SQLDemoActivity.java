package com.niu.demos.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.niu.demos.R;

public class SQLDemoActivity extends Activity implements OnClickListener {
	private EventDataSQLHelper helper;
	private Button mButton, mCopy, mShow;
//	private TextView mTextView;
//	private String dbName = "native.db";
	private String gxws = "gongxinweishi.db";
//	private String blackTable = "black";
//	private String whiteTable = "white";
	private SQLiteDatabase db;
	private String pwd = "comon12321";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sql_demo_layout);
		SQLiteDatabase.loadLibs(this);
		Thread t = new Thread(new LoadDataRunnable());
		t.start();
		init();


		// for (int i = 1; i < 100; i++)
		// addEvent("Hello Android Event: " + i, db);
		//
		// db.close();

		// db = helper.getReadableDatabase(pwd);
		//
		// Cursor cursor = getEvents(db);
		// showEvents(cursor);
		//
		// db.close();
	}

	private void addEvent(String title, SQLiteDatabase db) {

//		 ContentValues values = new ContentValues();
//		 values.put(EventDataSQLHelper.TIME, System.currentTimeMillis());
//		 values.put(EventDataSQLHelper.TITLE, title);
//		 db.insert(EventDataSQLHelper.TABLE, null, values);
	}

	private Cursor getCursor(SQLiteDatabase db, String tableName) {

		Cursor cursor = db.query(tableName, null, null, null,null, null, null);

		startManagingCursor(cursor);
		return cursor;
	}

	private void showDatas(Cursor cursor) {
		StringBuilder ret = new StringBuilder("Saved Events:\n\n");
		while (cursor.moveToNext()) {
			long id = cursor.getLong(0);
			String time = cursor.getString(cursor
					.getColumnIndex(EventDataSQLHelper.COL_NUMBER));
//			String title = cursor.getString(cursor
//					.getColumnIndex(EventDataSQLHelper.COL_NAME));
			ret.append(id + ": " + time + "\n");
		}

		Log.i("info", ret.toString());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.closeDataBase();
	}

	private void init() {
		mButton = (Button) findViewById(R.id.load_btn);
		mButton.setOnClickListener(this);

		mCopy = (Button) findViewById(R.id.copy_btn);
		mCopy.setOnClickListener(this);
		
		mShow = (Button) findViewById(R.id.show_btn);
		mShow.setOnClickListener(this);

//		mTextView = (TextView) findViewById(R.id.sqlite_data);
		onClick(mButton);
	}

	private class LoadDataRunnable implements Runnable {

		@Override
		public void run() {
			try {

				loadFile();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	private synchronized void loadFile() {

		File root = this.getCacheDir().getParentFile();
		File dbroot = new File(root.getAbsolutePath() + "/databases");
		if (!dbroot.exists()) {
			dbroot.mkdir();
		}
		File dbgxws = this.getDatabasePath(gxws);
		if (!dbgxws.exists()) {
			GetAssetsFile(this, gxws, dbgxws.getAbsolutePath(), true);
		}

	}

	public static boolean GetAssetsFile(Context context, String filename,
			String tagFile, boolean bReplace) {
		try {
			InputStream in = context.getAssets().open(filename);
			File dir = new File(tagFile);
			if (bReplace && (dir.exists())) {
				dir.delete();
			}
			if (in.available() == 0) {
				return false;
			}
			FileOutputStream out = new FileOutputStream(tagFile);
			int read;
			byte[] buffer = new byte[4096];
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}
			out.close();
			in.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 将数据导入新的数据库
		case R.id.copy_btn:
			db = helper.getReadableDatabase(pwd);
			android.database.sqlite.SQLiteDatabase data = this
					.openOrCreateDatabase(gxws, Context.MODE_PRIVATE,null);
			
			String sql_b = "select number from " + EventDataSQLHelper.TABLE_B;
			Cursor cursorB = data.rawQuery(sql_b, null);
			addDatasToNewDatabase(db, cursorB, EventDataSQLHelper.TABLE_B);

			String sql_w = "select number from " + EventDataSQLHelper.TABLE_W;
			Cursor cursorW = data.rawQuery(sql_w, null);
			addDatasToNewDatabase(db, cursorW, EventDataSQLHelper.TABLE_W);
			data.close();
			db.close();

			break;
		// 创建数据库
		case R.id.load_btn:
			helper = EventDataSQLHelper.getInstance(this);
			// then you can open the database using a password
			db = helper.getWritableDatabase(pwd);
			db.close();
			break;
		// 显示数据
		case R.id.show_btn:
			 db = helper.getReadableDatabase(pwd);
			
			 Cursor cursor = getCursor(db, EventDataSQLHelper.TABLE_B);
			 showDatas(cursor);
			 cursor.close();
			 db.close();
			break;
		default:
			break;
		}
	}

	private void addDatasToNewDatabase(SQLiteDatabase db, Cursor cursor,String tableName) {
		if (cursor != null) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				ContentValues values = new ContentValues();
				
				values.put(EventDataSQLHelper.COL_NUMBER,
							cursor.getString(cursor.getColumnIndexOrThrow(EventDataSQLHelper.COL_NUMBER)));
//				values.put(EventDataSQLHelper.COL_NAME,
//							cursor.getString(cursor.getColumnIndexOrThrow(EventDataSQLHelper.COL_NAME)));
				db.insert(tableName, null, values);
				
				cursor.moveToNext();
			}
			cursor.close();
		}

	}
}
