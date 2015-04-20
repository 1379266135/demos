package com.niu.demos.sliding;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter{

	private ArrayList<String> pagerTitles;
	
	public MyPagerAdapter(FragmentManager fm, ArrayList<String> titles) {
		super(fm);
		this.pagerTitles = titles;
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = SuperAwesomeCardFragment.newInstance(arg0);
		return fragment;
	}

	@Override
	public int getCount() {
		if(pagerTitles != null && pagerTitles.size() > 0){
			return pagerTitles.size();
		}
		return 0;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return pagerTitles.get(position);
	}

}
