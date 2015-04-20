package com.niu.demos.images.cache;

import java.io.File;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.util.LruCache;

public class ImageCache {
	// 内存缓存大小为 5M
	private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 5; // 5MB
	// 磁盘缓存大小为 10M
	private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
	
	// Compression settings when writing images to disk cache
	private static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.JPEG;
	// 压缩质量
	private static final int DEFAULT_COMPRESS_QUALITY = 70;
	private static final int DISK_CACHE_INDEX = 0;
	// 缓存常量
	private static final boolean DEFAULT_MEM_CACHE_ENABLED = true; // 内存缓存是否可用
	private static final boolean DEFAULT_DISK_CACHE_ENABLED = true; // 磁盘缓存是否可用
	private static final boolean DEFAULT_CLEAR_DISK_CACHE_ON_START = false; // 清除磁盘缓存
	private static final boolean DEFAULT_INIT_DISK_CACHE_ON_CREATE = false; // 创建磁盘缓存
	
	private DiskLruCache mDiskLruCache;
	private LruCache<String, Bitmap> mMemoryCache;
	private ImageCacheParams mImageCacheParams;
	private boolean mDiskCacheStarting = true;
	private final Object mDiskCacheLock = new Object();
	
	public ImageCache(ImageCacheParams params, boolean checkJournal){
		
	}
	
	public void init(ImageCacheParams cacheParams, boolean checkJournal){
		mImageCacheParams = cacheParams;
		if(mImageCacheParams.memoryCacheEnabled){
			mMemoryCache = new LruCache<String, Bitmap>(mImageCacheParams.memCacheSize){
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return getBitmapSize(bitmap);
				}
			};

			if(mImageCacheParams.initDiskCacheOnCreate){
				initDiskCache(checkJournal);
			}
		}
	}
	
	public static class ImageCacheParams{
		public int memCacheSize = DEFAULT_MEM_CACHE_SIZE;
		public int diskCacheSize = DEFAULT_DISK_CACHE_SIZE;
		public File diskCacheDir;
		public CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
		public int compressQuality = DEFAULT_COMPRESS_QUALITY;
		public boolean memoryCacheEnabled = DEFAULT_MEM_CACHE_ENABLED;
		public boolean diskCacheEnabled = DEFAULT_DISK_CACHE_ENABLED;
		public boolean clearDiskCacheOnStart = DEFAULT_CLEAR_DISK_CACHE_ON_START;
		public boolean initDiskCacheOnCreate = DEFAULT_INIT_DISK_CACHE_ON_CREATE;
		
		public ImageCacheParams(Context context, String uniqueName){
			diskCacheDir = getDiskCacheDir(context, uniqueName);
		}
		
		public ImageCacheParams(File diskCacheDir){
			this.diskCacheDir = diskCacheDir;
		}
		
		/** 设置内存缓存占比 */
		public void setMemoryCacheSizePercent(Context context, float percent){
			if(percent < 0.05f || percent > 0.8f){
				throw new IllegalArgumentException(
						"setMemCacheSizePercent - percent must be "
								+ "between 0.05 and 0.8 (inclusive)");
			}
			this.memCacheSize = Math.round(percent*getMemoryClass(context)*1024*1024);
		}
		
		private static int getMemoryClass(Context context){
			return ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		}
	}
	
	/** 创建一个缓存文件 */
	public static File getDiskCacheDir(Context context, String uniqueName){
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) 
								|| isExternalStorageRemovable() ? getDiskCacheDir(context, uniqueName).getPath()
																	: context.getCacheDir().getPath();
		return new File(cachePath + File.separator + uniqueName);
	}
	
	/** 判断sdk版本 */
	public static boolean isExternalStorageRemovable(){
		if(Utils.hasGingerbread()){
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}
	
	public static int getBitmapSize(Bitmap bitmap){
		if(Utils.hasHoneycombMR1()){
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
	public void initDiskCache(boolean checkJournal){
		synchronized (mDiskCacheLock) {
			if(mDiskLruCache == null || mDiskLruCache.isClosed()){
				File diskCacheDir = mImageCacheParams.diskCacheDir;
				if(diskCacheDir != null && mImageCacheParams.diskCacheEnabled){
					if(!diskCacheDir.exists()){
						diskCacheDir.mkdirs();
					}
					if((double) getUsableSpace(diskCacheDir) > 0.5*1024*1024){
						try {
//							mDiskLruCache = DiskLruCache.open(diskCacheDir, , valueCount, maxSize, checkJournal)
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
		}
	}
	
	/** 获取可用空间 */
	public static long getUsableSpace(File path){
		if(Utils.hasGingerbread()){
			return path.getUsableSpace();
		}
		//  Android.os下的StatFs类主要用来获取文件系统的状态，能够获取sd卡的大小和剩余空间，获取系统内部空间也就是/system的大小和剩余空间等等。
		final StatFs stats = new StatFs(path.getPath());
		return stats.getBlockSize() * (long)stats.getAvailableBlocks();
	}
}
