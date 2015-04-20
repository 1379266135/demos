package com.niu.demos.float_window;

import java.lang.reflect.Field;

import android.content.Context;

import com.niu.demos.R;

/**
 * 
 * @author Syleee
 * 
 */
public class SizeFitUtil {

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getStatusBarHeight(Context context) {

		int statusBarHeight = 0;

		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = context.getResources().getDimensionPixelSize(
						x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}
	
	public static String formatSpeedSize(Context context, long number) {
		if (context == null) {
			return "";
		}

		float result = number;
		int suffix = R.string.byteShort;
		if (result > 900) {
			suffix = R.string.kilobyteShort;
			result = result / 1024;
		}
		if (result > 900) {
			suffix = R.string.megabyteShort;
			result = result / 1024;
		}
		if (result > 900) {
			suffix = R.string.gigabyteShort;
			result = result / 1024;
		}
		if (result > 900) {
			suffix = R.string.terabyteShort;
			result = result / 1024;
		}
		if (result > 900) {
			suffix = R.string.petabyteShort;
			result = result / 1024;
		}
		String value;
		if (result < 1) {
			value = String.format("%.2f", result);
		} else if (result < 10) {
			value = String.format("%.0f", result);

		} else if (result < 100) {
			value = String.format("%.1f", result);
		} else {
			value = String.format("%.0f", result);
		}

		return context.getResources().getString(R.string.fileSizeSuffix, value,
				context.getString(suffix));
	}
}
