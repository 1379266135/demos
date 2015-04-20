package com.niu.demos.list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.niu.demos.R;
import com.niu.demos.adapter.BaseActivity;
import com.niu.demos.adapter.SimpleBaseAdapter;

public class L_Style_Activity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.l_style_layout);
		ArrayList<String> datas = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			String date = "星期：" + i;
			datas.add(date);
		}
		ListView list = viewFinder.viewFinder(R.id.style_list);
		list.setAdapter(new StyleAdapter(this, datas));
	}
	
	public class StyleAdapter extends SimpleBaseAdapter<String>{

		public StyleAdapter(Context context, List<String> datas) {
			super(context, datas);
		}

		@Override
		public int getItemResourceId() {
			return 0;
		}

		@Override
		public View getItemView(int position, View convertView,
				com.niu.demos.adapter.SimpleBaseAdapter.ViewHolder holder) {
			
			return null;
		}
		
	}
}
