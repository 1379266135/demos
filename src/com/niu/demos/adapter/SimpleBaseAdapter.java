package com.niu.demos.adapter;

import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SimpleBaseAdapter<T> extends BaseAdapter{
	
	private Context mContext;
	private List<T> dataSource;
	
	public SimpleBaseAdapter(Context context, List<T> datas){
		this.mContext = context;
		this.dataSource = datas;
	}

	@Override
	public int getCount() {
		if(dataSource != null && dataSource.size() > 0){
			return dataSource.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = View.inflate(mContext, getItemResourceId(), null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return getItemView(position, convertView, holder);
	}
	
	public class ViewHolder{
		private SparseArray<View> views = new SparseArray<View>();
		private View convertView;
		
		public ViewHolder(View convertView){
			this.convertView = convertView;
		}
		
		@SuppressWarnings({ "unchecked", "hiding" })
		public <T extends View> T getView(int viewId){
			View view = views.get(viewId);
			if(null == view){
				view = convertView.findViewById(viewId);
				views.put(viewId, view);
			}
			
			return (T) view;
		}
	}
	
	public void addObject(T obj){
		dataSource.add(obj);
		notifyDataSetChanged();
	}
	
	public void addAll(List<T> data){
		dataSource.addAll(data);
		notifyDataSetChanged();
	}
	
	public void removeObj(T obj){
		dataSource.remove(obj);
		notifyDataSetChanged();
	}
	
	public void removeObj(int index){
		dataSource.remove(index);
		notifyDataSetChanged();
	}
	
	public void removeAll(List<T> list){
		dataSource.removeAll(list);
		notifyDataSetChanged();
	}
	
	public void replaceAll(List<T> list){
		dataSource.clear();
		dataSource.addAll(list);
		notifyDataSetChanged();
	}
	
	/** 子类需要重写该方法， 此方法用于设置item的布局资源id */
	public abstract int getItemResourceId();
	/**
	 * 子类需要重写该方法， 代替BaseAdapter中的getView方法
	 * @param position
	 * @param convertView
	 * @param holder
	 * @return
	 */
	public abstract View getItemView(int position, View convertView, ViewHolder holder);

}
