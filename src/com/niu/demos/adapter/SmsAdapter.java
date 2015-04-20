package com.niu.demos.adapter;

import java.util.List;

import com.niu.demos.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SmsAdapter extends SimpleBaseAdapter<ESms>{
	private List<ESms> list;
	private Handler mHandler;

	public SmsAdapter(Context context, List<ESms> datas) {
		super(context, datas);
		this.list = datas;
	}
	
	public SmsAdapter(Context context, List<ESms> datas, Handler handler){
		super(context, datas);
		this.list = datas;
		this.mHandler = handler;
	}

	@Override
	public int getItemResourceId() {
		return R.layout.simple_item_layout;
	}

	@Override
	public View getItemView(int position, View convertView, ViewHolder holder) {
		Button reportBtn = holder.getView(R.id.sms_btn_report);
		TextView number = holder.getView(R.id.sms_phone_number);
		TextView content = holder.getView(R.id.sms_content);
		TextView date = holder.getView(R.id.sms_date);
		
		final ESms sms = (ESms) getItem(position);
		number.setText(sms.getAddr());
		content.setText(sms.getBody());
		date.setText(sms.getDate()+"");
		reportBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = 1;
				msg.obj = sms;
				mHandler.sendMessage(msg);
			}
		});
		return convertView;
	}
	
	public void clearData(){
		if(list != null){
			list.clear();
		}
		notifyDataSetChanged();
	}

}
