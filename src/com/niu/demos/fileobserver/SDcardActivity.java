package com.niu.demos.fileobserver;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import com.niu.demos.adapter.BaseActivity;

public class SDcardActivity extends BaseActivity {
	private FileObserver mFileObserver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (mFileObserver == null) {
//			mFileObserver = new SDCardObserver(Environment
//					.getExternalStorageDirectory().getPath());
//			mFileObserver.startWatching();
//		}
//		
		String obj = new String("niu bin bin ");
		WeakReference<String> weak = new WeakReference<String>(obj);
		obj = null;
		Log.i("info", "weakReference  result: " + weak.get());
		System.gc();
		Log.i("info", "weakReference GC result: " + weak.get());
		
		
		SoftReference<Person>[] soft = new SoftReference[100];
		for (int i = 0; i < soft.length; i++) {
			soft[i] = new SoftReference<SDcardActivity.Person>(new Person("张", i));
		}
		Log.i("info", "softReference result--1 : " + soft[1].get());
		Log.i("info", "softReference result--4 : " + soft[4].get());
		System.gc();
		System.runFinalization();
		Log.i("info", "softReference GC result--1 : " + soft[1].get());
		Log.i("info", "softReference GC result--4 : " + soft[4].get());
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		String path = Environment.getExternalStorageDirectory().getPath();
		File file = new File(path + "/" + "niubinbin");
		if(!file.exists()){
			file.mkdirs();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mFileObserver != null) {
			mFileObserver.stopWatching();
		}
	}
	
	class Person {
		private String name;
		private int age;
		
		public Person(String name, int age){
			this.name = name;
			this.age = age;
		}
		
		@Override
		public String toString() {
			return name + " / " + age;
		}
	}

//    private final class AppDirObserver extends FileObserver {
//        public AppDirObserver(String path, int mask, boolean isrom) {
//            super(path, mask);
//            mRootDir = path;
//            mIsRom = isrom;
//        }
//
//        public void onEvent(int event, String path) {
//            String removedPackage = null;
//            int removedUid = -1;
//            String addedPackage = null;
//            int addedUid = -1;
//
//            // TODO post a message to the handler to obtain serial ordering
////            synchronized (mInstallLock) {
//                String fullPathStr = null;
//                File fullPath = null;
//                if (path != null) {
//                    fullPath = new File(mRootDir, path);
//                    fullPathStr = fullPath.getPath();
//                }
//
////                if (DEBUG_APP_DIR_OBSERVER)
////                    Log.v(TAG, "File " + fullPathStr + " changed: " + Integer.toHexString(event));
//
//                if (!isPackageFilename(path)) {
//                    if (DEBUG_APP_DIR_OBSERVER)
//                        Log.v(TAG, "Ignoring change of non-package file: " + fullPathStr);
//                    return;
//                }
//
//                // Ignore packages that are being installed or
//                // have just been installed.
//                if (ignoreCodePath(fullPathStr)) {
//                    return;
//                }
//                PackageParser.Package p = null;
//                // reader
//                synchronized (mPackages) {
//                    p = mAppDirs.get(fullPathStr);
//                }
//                if ((event&REMOVE_EVENTS) != 0) {
//                    if (p != null) {
//                        removePackageLI(p, true);
//                        removedPackage = p.applicationInfo.packageName;
//                        removedUid = p.applicationInfo.uid;
//                    }
//                }
//
//                if ((event&ADD_EVENTS) != 0) {
//                    if (p == null) {
//                        p = scanPackageLI(fullPath,
//                                (mIsRom ? PackageParser.PARSE_IS_SYSTEM
//                                        | PackageParser.PARSE_IS_SYSTEM_DIR: 0) |
//                                PackageParser.PARSE_CHATTY |
//                                PackageParser.PARSE_MUST_BE_APK,
//                                SCAN_MONITOR | SCAN_NO_PATHS | SCAN_UPDATE_TIME,
//                                System.currentTimeMillis());
//                        if (p != null) {
//                            /*
//                             * TODO this seems dangerous as the package may have
//                             * changed since we last acquired the mPackages
//                             * lock.
//                             */
//                            // writer
//                            synchronized (mPackages) {
//                                updatePermissionsLPw(p.packageName, p,
//                                        p.permissions.size() > 0, false, false);
//                            }
//                            addedPackage = p.applicationInfo.packageName;
//                            addedUid = p.applicationInfo.uid;
//                        }
//                    }
//                }
//
//                // reader
//                synchronized (mPackages) {
//                    mSettings.writeLPr();
//                }
////            }
//
//            if (removedPackage != null) {
//                Bundle extras = new Bundle(1);
//                extras.putInt(Intent.EXTRA_UID, removedUid);
//                extras.putBoolean(Intent.EXTRA_DATA_REMOVED, false);
//                sendPackageBroadcast(Intent.ACTION_PACKAGE_REMOVED, removedPackage,
//                        extras, null, null);
//            }
//            if (addedPackage != null) {
//                Bundle extras = new Bundle(1);
//                extras.putInt(Intent.EXTRA_UID, addedUid);
//                sendPackageBroadcast(Intent.ACTION_PACKAGE_ADDED, addedPackage,
//                        extras, null, null);
//            }
//        }
//
//        private final String mRootDir;
//        private final boolean mIsRom;
//    }
}
