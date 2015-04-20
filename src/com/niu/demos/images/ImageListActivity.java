package com.niu.demos.images;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ListView;

import com.niu.demos.R;
import com.niu.demos.adapter.BaseActivity;
import com.niu.demos.images.runnable.GetChannelRunnable;
import com.niu.demos.images.runnable.HandlerMessage;

public class ImageListActivity extends BaseActivity{
	private ListView mListView;
    private boolean isLoading = false;
    private boolean noMore = false;
    private long lastTopId = -1;
    
    private List<Top> mTops = new ArrayList<Top>();
    private PiTopAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.id.Imageview_image);
		initView();
	}
	
	private void initView(){
		mListView = viewFinder.viewFinder(R.id.lv_top);
		mListView.setOnScrollListener(mScrollListener);
		
		getTops();
	}
	
	private OnScrollListener mScrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onScroll(AbsListView view,
							int firstVisibleItem,
							int visibleItemCount, 
							int totalItemCount) 
		{
			if(firstVisibleItem + visibleItemCount >= totalItemCount-2){
				
			}
		}
	};
	
	private void getTops(){
		isLoading = true;
		GetChannelRunnable runnable = new GetChannelRunnable(lastTopId);
		runnable.setHandler(mHandler);
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case HandlerMessage.MSG_SUCCESS:
				handleSuccess(msg);
				break;
			case HandlerMessage.MSG_FAILURE:
				isLoading = false;
				break;
			default:
				break;
			}
			super.dispatchMessage(msg);
		};
	};
	
	private void handleSuccess(Message msg){
		List<Top> tempList = (List<Top>) msg.obj;
		if(lastTopId <= 0){
			mTops.clear();
		}
		if(tempList != null && tempList.size() > 0){
			lastTopId = tempList.get(tempList.size() - 1).getmTopId();
			noMore = false;
			mTops.addAll(tempList);
			mAdapter.notifyDataSetChanged();
		}
	}
}
