package com.niu.demos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GooglePullRefresh extends Activity implements OnRefreshListener, OnItemClickListener{

	private SwipeRefreshLayout refreshLayout;
	private ListView mListView;
	private ArrayList<ItemInfo> arrs;
	private int count = 1;
	private ItemAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pull_refresh_layout);
		arrs = new ArrayList<ItemInfo>();
		for (int i = 0; i < 20; i++) {
			ItemInfo info = new ItemInfo();
			info.setName("今天是-->" + i);
			arrs.add(info);
		}
		initView();
		mListView.setAdapter(mAdapter);
		
		refreshLayout.setOnRefreshListener(this);
		
		refreshLayout.setColorScheme(android.R.color.holo_red_light, 
										android.R.color.holo_green_light, 
										android.R.color.holo_blue_bright, 
										android.R.color.holo_orange_light);
	}
	
	private void initView(){
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
		mListView = (ListView) findViewById(R.id.refresh_list);
		mListView.setOnItemClickListener(this);
		mAdapter = new ItemAdapter(this, arrs);
	}
	

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				refreshLayout.setRefreshing(false);
				count++;
				
				ItemInfo info = new ItemInfo();
				info.setName("这里是刷新-->" + count);
				arrs.add(info);				
				mAdapter.notifyDataSetChanged();
			}
		}, 500);
		
	}
	
	class ItemAdapter extends BaseAdapter{
		private ArrayList<ItemInfo> lists;
		private Context mContext;
		public ItemAdapter(Context context, ArrayList<ItemInfo> source){
			lists = source;
			mContext = context;
		}

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			return lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if(convertView == null){
				view = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
			} else {
				view = convertView;
			}
			TextView text = (TextView) view.findViewById(android.R.id.text1);
			ItemInfo info = lists.get(position);
			text.setText(info.getName());
			return view;
		}
		
	}
	
	class ItemInfo {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent mIntent = new Intent(this, TempActivity.class);
		startActivity(mIntent);
		finish();
	}
}
