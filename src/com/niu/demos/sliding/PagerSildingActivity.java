package com.niu.demos.sliding;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.niu.demos.R;

public class PagerSildingActivity extends FragmentActivity {
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter mAdapter;
	private int currentColor = 0xff666666;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.pager_silding_layout);
		initView();
		tabs.setIndicatorColor(currentColor);
	}
	
	private void initView(){
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		String[] title = getResources().getStringArray(R.array.titles);
		ArrayList<String> titles = new ArrayList<String>();
		Collections.addAll(titles, title);
		mAdapter = new MyPagerAdapter(getSupportFragmentManager(), titles);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
																getResources().getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setAdapter(mAdapter);
		tabs.setViewPager(pager);
	}

}
