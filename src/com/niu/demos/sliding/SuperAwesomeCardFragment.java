package com.niu.demos.sliding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.niu.demos.R;

public class SuperAwesomeCardFragment extends Fragment{
	
	private static final String ARG_POSITION = "position";

	private int position;
	
	public static SuperAwesomeCardFragment newInstance(int position){
		SuperAwesomeCardFragment fragment = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout layout = new FrameLayout(getActivity());
		layout.setLayoutParams(params);
		
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		TextView text = new TextView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		text.setLayoutParams(params);
		text.setGravity(Gravity.CENTER);
		text.setBackgroundResource(R.drawable.background_card);
		text.setText("CARD" + (position + 1));
		layout.addView(text);
		return layout;
	}
}
