package com.niu.demos.images;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.niu.demos.R;
import com.niu.demos.adapter.SimpleBaseAdapter;
import com.niu.demos.images.utils.ImageManageUtil;

public class PiTopAdapter extends BaseAdapter {
	/** 设置每行几列 */
	public static final int COLUMNCOUNT = ImageManageUtil.getScreenWidth() >= 1080 ? 4 : 3;
	public static final int margin = ImageManageUtil.dip2pix(1);
	public static final int topMargin = ImageManageUtil.dip2pix(0);
	public static final int topPadding = ImageManageUtil.dip2pix(7);
	public static final int horizontalPadding = ImageManageUtil.dip2pix(5);
	public static final int imageSize = (ImageManageUtil.getScreenWidth() - margin*(COLUMNCOUNT+1) - horizontalPadding*2) / COLUMNCOUNT;
	
	private List<Top> mtTops;
	private Context mContext;

	public PiTopAdapter(Context context, List<Top> datas) {
		this.mtTops = datas;
		this.mContext = context;
	}

	public int getItemResourceId() {
		return R.layout.image_list_item_top;
	}
	
	@Override
	public int getCount() {
		if(this.mtTops == null || this.mtTops.size() <= 0){
			return 0;
		}
		int count = (int) Math.ceil((double)(mtTops.size()/COLUMNCOUNT));
		return count;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			LinearLayout ll = (LinearLayout) View.inflate(mContext, getItemResourceId(), null);
			LayoutParams params = new LayoutParams(imageSize, imageSize);
			params.rightMargin = margin;
		}
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	/*@Override
	public View getItemView(int position, View convertView,ViewHolder holder) {
		
		return convertView;
	}*/

}
