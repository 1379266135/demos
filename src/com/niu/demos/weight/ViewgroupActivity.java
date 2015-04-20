package com.niu.demos.weight;

import java.util.ArrayList;
import java.util.Random;

import com.niu.demos.R;
import com.niu.demos.adapter.BaseActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class ViewgroupActivity extends BaseActivity implements OnItemClickListener,OnRearrangeListener{
	static Random random = new Random();
	private MyViewgroup group;
	ArrayList<String> poem = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		group = viewFinder.viewFinder(R.id.vgv);
		for (int i = 0; i < 6; i++) {
			String str = "测试.." + i;
			ImageView mImageView = new ImageView(this);
			mImageView.setImageBitmap(getThumb(str));
//			Button mButton = new Button(this);
//			mButton.setText("测试.." + i);
//			mButton.setTextColor(getResources().getColor(R.color.golden));
			group.addView(mImageView);
			poem.add(str);
		}

		group.setOnRearrangeListener(this);
		group.setOnItemClickListener(this);
	}
	
	private Bitmap getThumb(String name){
		Bitmap bmp = Bitmap.createBitmap(150, 150, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bmp);
	    Paint paint = new Paint();
	    
	    paint.setColor(Color.rgb(random.nextInt(128), random.nextInt(128), random.nextInt(128)));
	    paint.setTextSize(24);
	    paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	    canvas.drawRect(new Rect(0, 0, 150, 150), paint);
	    paint.setColor(Color.WHITE);
	    paint.setTextAlign(Paint.Align.CENTER);
	    canvas.drawText(name, 75, 75, paint);
	    
		return bmp;
	}

	@Override
	public void onRearrange(int oldIndex, int newIndex) {
//		String word = poem.remove(oldIndex);
//		if (oldIndex < newIndex)
//			poem.add(newIndex, word);
//		else
//			poem.add(newIndex, word);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		group.removeViewAt(position);
//		poem.remove(position);
	}
}
