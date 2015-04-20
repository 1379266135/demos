package com.niu.demos.wifi.view;


import com.niu.demos.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class OperFrameDialog extends Dialog{


	private TextView mTitleView;
	private ListView mListView;

	public OperFrameDialog(Context context) {
		super(context, R.style.comon_custom_dialog);

		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View initView = inflater.inflate(R.layout.comon_layout_dialog, null);
		mTitleView = (TextView) initView.findViewById(R.id.sms_dialog_title);
		mListView = (ListView) initView.findViewById(R.id.sms_item_list);
		setContentView(initView);
		setCanceledOnTouchOutside(true);
		setContentView(initView);
	}

	public ListView getListView() {
		return mListView;
	}

	/**
	 * 设置对话框标题
	 * <p>
	 * Title: setTitle
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param title
	 * @see android.app.Dialog#setTitle(java.lang.CharSequence)
	 */
	@Override
	public void setTitle(CharSequence title) {
		mTitleView.setText(title);
	}

	/**
	 * 设置对话框标题
	 * <p>
	 * Title: setTitle
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param titleId
	 * @see android.app.Dialog#setTitle(int)
	 */
	@Override
	public void setTitle(int titleId) {
		mTitleView.setText(titleId);
	}

}
