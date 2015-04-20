package com.niu.demos.wifi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.ExpandableListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.niu.demos.R;
import com.niu.demos.wifi.view.OperFrameDialog;
import com.niu.demos.wifi.view.PullToRefreshBase;
import com.niu.demos.wifi.view.PullToRefreshBase.OnRefreshListener;
import com.niu.demos.wifi.view.PullToRefreshExpandableListView;

public class SettingWifiActivity extends ExpandableListActivity implements OnRefreshListener<ExpandableListView>, OnClickListener{
	private WifiAdmin mWifiAdmin;
	private RelativeLayout layout_empty;
	private TextView connectedWifi;
//	private ExpandableListView mListView;
	private PullToRefreshExpandableListView mListView;
	private List<ScanResult> mWifiList = null;
	private RelativeLayout headerView;
	private wifiAdapter mAdapter;
	private ArrayList<String> groups;
//	private ArrayList<WifiInfo> childs;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				ScanResult scan = (ScanResult)msg.obj;
				int type = -1;
				if(scan.capabilities.contains("WPA")){
					type = 3;
				} else if(scan.capabilities.contains("WEP")){
					type = 2;
				} else {
					type = 1;
				}
				
				WifiConfiguration config = mWifiAdmin.CreateWifiInfo(scan.SSID, "comon12321", type);
				mWifiAdmin.addNetwork(config);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_wifi_layout);
		headerView = (RelativeLayout) View.inflate(this, R.layout.list_header, null);
		headerView.setOnClickListener(this);
		mWifiAdmin = new WifiAdmin(this);
		initView();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		boolean on = Settings.System.getInt(getContentResolver(),Settings.System.AIRPLANE_MODE_ON,0) == 1 ? true : false;
		Log.i("info", "wifi 开关状态： " + mWifiAdmin.mWifiManager.isWifiEnabled());
		Log.i("info", "飞行模式  开关状态： " + on);
		if(on || !mWifiAdmin.mWifiManager.isWifiEnabled()){
			layout_empty.setVisibility(View.VISIBLE);
			connectedWifi.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
			if(mAdapter != null){

			}
		} else {
			layout_empty.setVisibility(View.GONE);
			connectedWifi.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.VISIBLE);
			mWifiAdmin.startScan();
			mWifiList = mWifiAdmin.getWifiList();
			showDataInfo();
		}
	}
	
	/**
	 * 显示扫描结果
	 */
	private void showDataInfo(){
		if(mWifiList != null && mWifiList.size() > 0){
			mAdapter = new wifiAdapter(this, mWifiAdmin.getFreeAndPwdWiFi(), handler);
			setListAdapter(mAdapter);
//			mListView.setAdapter((ListAdapter) mAdapter);
			for (int i = 0; i < mWifiAdmin.getFreeAndPwdWiFi().size(); i++) {
				getExpandableListView().expandGroup(i);
			}
		} else {
			layout_empty.setVisibility(View.VISIBLE);
			connectedWifi.setVisibility(View.GONE);
			mListView.setVisibility(View.GONE);
		}
	}
	
	private void initView() {
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
		registerReceiver(mWifiConnectReceiver, mFilter);
		
		layout_empty = (RelativeLayout) findViewById(R.id.empty_layout);
		connectedWifi = (TextView) headerView.findViewById(R.id.conn_wifi);
		mListView = (PullToRefreshExpandableListView) findViewById(R.id.list_expand);
		mListView.setOnRefreshListener(this);
		getExpandableListView().addHeaderView(headerView);
	}
	
	
	class wifiAdapter extends BaseExpandableListAdapter{
		private ArrayList<String> group;
//		private ArrayList<ScanResult> childs;
		private Context mContext;
		private HashMap<String, ArrayList<ScanResult>> dataSource;
		private Handler mHandler;
		
		public wifiAdapter(Context context, HashMap<String, ArrayList<ScanResult>> childs, Handler handler){
			this.mContext = context;
			this.dataSource = childs;
			this.mHandler = handler;
			sortData(childs);
		}
		
		private void sortData(HashMap<String, ArrayList<ScanResult>> childs){
			Set keys = childs.keySet();
			if(keys != null) {
				group = new ArrayList<String>();
			    Iterator iterator = keys.iterator(); 
			    while(iterator.hasNext()) {
			        String key = (String) iterator.next();
			        Object value = childs.get(key);       
			        group.add(key);
			    } 
			}
		}
		
		public void removeAll(){
			if(this.dataSource != null){
				dataSource.clear();
				group.clear();
			}
		}
		
		public void addResource(HashMap<String, ArrayList<ScanResult>> childs, Handler handler){
			this.dataSource = childs;
			this.mHandler = handler;
			sortData(childs);
		}

		@Override
		public int getGroupCount() {
			
			return this.dataSource.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return dataSource.get(group.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			
			return group.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			
			return dataSource.get(group.get(groupPosition)).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if(convertView == null){
				convertView = View.inflate(mContext, R.layout.setting_wifi_group_item, null);
			} 
			((TextView)convertView.findViewById(R.id.group)).setText(group.get(groupPosition));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.wifi_child_item, null);
				holder.wifiText = (TextView) convertView.findViewById(R.id.wifi_name);
				holder.statusText = (TextView) convertView.findViewById(R.id.wifi_status);
				holder.mImageView = (ImageView) convertView.findViewById(R.id.wifi_icon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			final ScanResult info = dataSource.get(group.get(groupPosition)).get(childPosition);
			holder.wifiText.setText(info.SSID);
//			holder.statusText.setText(info.getStatus());
			holder.mImageView.setImageLevel(getLevel());
			if(groupPosition == 0){
				holder.mImageView.setImageResource(R.drawable.wifi_signal_lock);
			} else {
				holder.mImageView.setImageResource(R.drawable.wifi_signal_open);
			}
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Message msg = Message.obtain();
					msg.what = 1;
					msg.obj = info;
					mHandler.sendMessage(msg);
				}
			});
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			
			return false;
		}
		
	    int getLevel() {
	    	WifiInfo wifi = mWifiAdmin.getWifiInfo();
	    	Log.i("info", "SSID = " + wifi.getSSID());
	    	Log.i("info", "Rssi = " + wifi.getRssi());
	    	Log.i("info", "IpAddress = " + wifi.getIpAddress());
	        if (wifi.getRssi() == Integer.MAX_VALUE) {
	            return -1;
	        }
	        return WifiManager.calculateSignalLevel(wifi.getRssi(), 4);
	    }
	}
	
	class ViewHolder {
		private TextView wifiText;
		private TextView statusText;
		private ImageView mImageView;
		public TextView getWifiText() {
			return wifiText;
		}
		public void setWifiText(TextView wifiText) {
			this.wifiText = wifiText;
		}
		public TextView getStatusText() {
			return statusText;
		}
		public void setStatusText(TextView statusText) {
			this.statusText = statusText;
		}
		public ImageView getmImageView() {
			return mImageView;
		}
		public void setmImageView(ImageView mImageView) {
			this.mImageView = mImageView;
		}
		
	}
	
	private BroadcastReceiver mWifiConnectReceiver = new BroadcastReceiver() {
		 
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("info", "Wifi onReceive action = " + intent.getAction());
			int message = -1;
			if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
				message = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
			}
			Log.i("info", "liusl wifi onReceive msg=" + message);
			switch (message) {
	        case WifiManager.WIFI_STATE_DISABLED:
	        	Log.i("info", "WIFI_STATE_DISABLED");
	        break;
	        case WifiManager.WIFI_STATE_DISABLING:
	        	Log.i("info", "WIFI_STATE_DISABLING");
	        break;
	        case WifiManager.WIFI_STATE_ENABLED:
	        	Log.i("info", "WIFI_STATE_ENABLED");
//	            threadSleep(10000);
//	            pingWifiGateway(EthUtils.getWifiGateWay());
	        break;
	        case WifiManager.WIFI_STATE_ENABLING:
	        	Log.i("info", "WIFI_STATE_ENABLING");
	        break;
	        case WifiManager.WIFI_STATE_UNKNOWN:
	        	Log.i("info", "WIFI_STATE_UNKNOWN");
	        break;
	        default:
	                break;
	        }
		}
	};

	@Override
	public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
		// 这里是刷新操作
		mWifiAdmin.startScan();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(mAdapter != null){
					mAdapter.removeAll();
				}
				if(mWifiAdmin.getFreeAndPwdWiFi() != null){
					mAdapter.addResource(mWifiAdmin.getFreeAndPwdWiFi(), handler);
				}
				mAdapter.notifyDataSetChanged();
				// Call onRefreshComplete when the list has been refreshed.
				mListView.onRefreshComplete();
			}
		}, 2000);
		
	}

	@Override
	public void onClick(View v) {
		TextView name = (TextView) v.findViewById(R.id.conn_wifi);
		OperFrameDialog operDlg = new OperFrameDialog(this);
		operDlg.setTitle(name.getText().toString());
		ListView listView = operDlg.getListView();

		String[] panels = getResources().getStringArray(R.array.comon_dialog_operation);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, panels);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0: // 分享网络
					
					break;
				case 1: // 断开连接
					
					break;
				default:
					break;
				}
			}
		});
		operDlg.show();
	}
}
