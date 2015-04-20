package com.niu.demos.preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class SmsFilterLog {

	public static boolean DEBUG = true;

	public static void debugLog(String logStr) {

		printLog("SmsFilterLib", logStr);

	}

	private static void printLog(String tag, String msg) {
		Log.d(tag, msg);
	}

	public static void saveLogToFile(String msg) {

		String logPath = getRootPath();
		if (logPath == null || logPath.length() <= 0) {
			return;
		}

		try {

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

			String time = formatter.format(new Date());
			String fileName = "suite-support_" + time + ".txt";
			File dir = new File(logPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(logPath + fileName);

			StringBuilder sb = new StringBuilder("suite-support:\r\n");
			sb.append(msg);

			fos.write(sb.toString().getBytes());
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void saveLogToFile(String fileName, String conent) {

		String logPath = getRootPath() + "/" + "apk.txt";
		if (logPath == null || logPath.length() <= 0) {
			return;
		}
		try {
			File dir = new File(logPath);
			
			RandomAccessFile randomFile = new RandomAccessFile(logPath, "rw");

			long fileLength = randomFile.length();
			
			randomFile.seek(fileLength);
			randomFile.writeBytes(conent + "\n");
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String getRootPath() {
		String logpath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			if (Environment.getExternalStorageDirectory().canWrite()) {
				logpath = Environment.getExternalStorageDirectory().getPath()
						+ File.separator + "suite-support_log/";
			}
		}
		return logpath;
	}

}
