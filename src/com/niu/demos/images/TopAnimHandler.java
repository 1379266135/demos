package com.niu.demos.images;

import java.util.ArrayList;
import java.util.List;

import com.niu.demos.R;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class TopAnimHandler extends Handler{
	private static final int AnimDelay = 1700;
	
	private NextAnim nextAnim = new NextAnim();
	private ArrayList<CellHolder> cells = new ArrayList<CellHolder>();
    private List<CellHolder> animCells = new ArrayList<CellHolder>();
	private int scrollState = OnScrollListener.SCROLL_STATE_IDLE;

    public static final class MSG {
        public static final int CHECK = 1745;
        public static final int ADD_CELL = 1758;
        public static final int ONSCROLL = 1826;
        public static final int SINGLE_ANIMATION_END = 1840;
        public static final int RESUME = 1355;
        public static final int PAUSE = 1356;
        public static final int CLEAR = 1819;
    }
    
    public static class CellHolder{
    	private static final int animDuration = 700;
    	Rect paddingRect = new Rect();
    	public ImageView ivAnimated;
    	public ImageView iv;
    	public FrameLayout mFrameLayout;
        private FrameLayout flContainer;
        
        String imageName = "";
        int currentPosition;
        boolean isReadyForAnimation = false;
    	
    	public CellHolder(LayoutInflater inflater){
    		mFrameLayout = (FrameLayout) inflater.inflate(R.layout.images_list_item_top_grid_item, null);
    		iv = (ImageView) mFrameLayout.findViewById(R.id.image);
    		ivAnimated = (ImageView) mFrameLayout.findViewById(R.id.iv_animted);
    		flContainer = (FrameLayout) mFrameLayout.findViewById(R.id.fl);
    		Drawable foreground = inflater.getContext().getResources().getDrawable(R.drawable.grid_photo_overlay);
    		foreground.getPadding(paddingRect);
    		mFrameLayout.setForeground(foreground);
    		mFrameLayout.setPadding(paddingRect.left, paddingRect.top, paddingRect.right, paddingRect.bottom);
    		mFrameLayout.setForegroundGravity(Gravity.FILL);
    	}
    	
    	public void anim(){
    		
    	}
    	
    	public void reset(){
    		ivAnimated.clearAnimation();
    		ivAnimated.setImageBitmap(null);
    		ivAnimated.setVisibility(View.INVISIBLE);
//    		ivAnimated.removeCallbacks(action);
//    		ivAnimated.removeCallbacks(action);
    		iv.clearAnimation();
    		currentPosition = 0;
    	}
    }
    
    @Override
    public void handleMessage(Message msg) {
    	switch (msg.what) {
		case MSG.ADD_CELL:
			if(msg.obj != null && msg.obj instanceof CellHolder){
				if(!cells.contains((CellHolder)msg.obj)){
					cells.add((CellHolder) msg.obj);
				}
			}
			break;
			
		case MSG.CHECK:
		case MSG.RESUME:
			check();
			break;
			
		case MSG.ONSCROLL:
			scrollState = (Integer) msg.obj;
			
			break;
			
		default:
			break;
		}
    }
    
    private void check(){
    	if(scrollState != OnScrollListener.SCROLL_STATE_IDLE){
    		return;
    	}
//    	removeCallbacks(r);
    }
    
    private void onScrollStateChange(int state){
    	if(state == OnScrollListener.SCROLL_STATE_IDLE){
    		check();
    	} else {
    		stopAnimQueue();
    	}
    }
    
    private void stopAnimQueue(){
    	removeCallbacks(check);
    	removeCallbacks(nextAnim);
//    	removeCallbacks(reset);
    	nextAnim.setCurrentCell(null);
    	postDelayed(check, AnimDelay);
    }
    
    private Runnable check = new Runnable() {
		
		@Override
		public void run() {
			if(cells.size() == 0){
				return;
			}
			if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
				startAnimQueue();
			}
		}
	};
	
	private void startAnimQueue(){
		
	}
	
	private class NextAnim implements Runnable{
		private CellHolder currentCell;
		
		public void setCurrentCell(CellHolder currentCell) {
			this.currentCell = currentCell;
		}

		@Override
		public void run() {
			if(animCells.size() > 0){
				CellHolder cell = animCells.get(0);
				if(currentCell != null && animCells.contains(currentCell)){
					int currentPosition = animCells.indexOf(currentCell);
					currentPosition ++;
					if(currentPosition > animCells.size()){
						currentPosition = 0;
					}
					cell = animCells.get(currentPosition);
				}
				cell.anim();
				setCurrentCell(currentCell);
			}
		}
	}
	
	private class Reset implements Runnable{

		@Override
		public void run() {
			for (CellHolder cell : cells) {
				cell.reset();
			}
		}
		
	}
}
