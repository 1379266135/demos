package com.niu.demos.weight;

import com.comon.extlib.smsfilter.bgo.SFService;
import com.niu.demos.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder holder;
	private Paint mPaint;
	private int textX = 10;
	private int textY = 10;
	private Thread mThread;
	private boolean flag;
	private Canvas mCanvas;
	private int screenH;
	private int screenW;
	private Bitmap mBitmap;
	private int bitmapX, bitmapY;

	public MySurfaceView(Context context) {
		super(context);
		holder = this.getHolder();
		holder.addCallback(this);
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		setFocusable(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenH = this.getHeight();
		screenW = this.getWidth();
		flag = true;
		mThread = new Thread(this);
		mBitmap = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.wave);
		bitmapX = this.getWidth() + mBitmap.getWidth();
		bitmapY = this.getHeight() + mBitmap.getHeight();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void myDraw() {
		try {
			mCanvas = holder.lockCanvas();
			if (mCanvas != null) {
				mCanvas.drawRGB(0, 0, 0);
				mCanvas.drawBitmap(mBitmap, bitmapX, bitmapY, mPaint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mCanvas != null) {
				holder.unlockCanvasAndPost(mCanvas);
			}
		}
	}

	private void logic() {
		bitmapX += 5;
		if (bitmapX == this.getWidth()) {
			bitmapX = 0;
		}
	}

}
