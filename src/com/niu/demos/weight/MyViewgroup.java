package com.niu.demos.weight;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class MyViewgroup extends ViewGroup implements OnClickListener, OnTouchListener, OnLongClickListener{
	private int dpi;
	private int childSizeW;
	public static float childRatio = 1f;
	private int childSizeH;
	private int padding;
	private int colCount;
	private int scroll = 0; // 滑动的距离
	
	private boolean isEnable = true;
	private int lastX, lastY = -1, lastTarget = -1;
    protected float lastDelta = 0;
	private int draggedViewIndex = -1; // 被拖拽view的id
	private boolean touching = false;
	private ArrayList<Integer> newPos = new ArrayList<Integer>();
	
    protected OnRearrangeListener onRearrangeListener;
    protected OnClickListener secondaryOnClickListener;
    private OnItemClickListener onItemClickListener;
	
	private void setLisenters(){
		setOnTouchListener(this);
		super.setOnClickListener(this);
		setOnLongClickListener(this);
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		secondaryOnClickListener = l;
	}

	public MyViewgroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLisenters();
		setChildrenDrawingOrderEnabled(true);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		dpi = metrics.densityDpi;
	}
	
	/**
	 * 返回迭代的绘制子类索引。如果你想改变子类的绘制顺序就要重写该方法。默认返回 i 值。
	 * 提示：为了能够调用该方法，你必须首先调用setChildrenDrawingOrderEnabled(boolean)来允许子类排序。
	 * @param 
	 * 		childCount Viewgroup中子view的个数
	 * 
	 * 			i		当前迭代顺序
	 */
	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		if(draggedViewIndex == -1){
			return i;
			
		}else if(i == childCount - 1){
			return draggedViewIndex;
			
		} else if(i >= draggedViewIndex){
			return i + 1;
			
		} else {
			return i;
		}
	}
	
	@Override
	public void addView(View child) {
		super.addView(child);
		newPos.add(-1);
	}
	
	@Override
	public void removeViewAt(int index) {
		super.removeViewAt(index);
		newPos.remove(index);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		// compute width of view, in dp
		float w = (r - l) / (dpi / 160f);

		// determine number of columns, at least 2
		colCount = 3;
		int sub = 240;
		w -= 280;
		while (w > 0) {
			colCount++;
			w -= sub;
			sub += 40;
		}

		// determine childSize and padding, in px
		childSizeW = (r - l) / colCount;
		childSizeW = Math.round(childSizeW * childRatio);
		childSizeH = childSizeW + 10;

		padding = ((r - l) - (childSizeW * colCount)) / (colCount + 1);

		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);

			Point xy = getCoorFromIndex(i);
			view.measure(childSizeW, childSizeH);
			view.layout(xy.x, xy.y, xy.x + childSizeW, xy.y + childSizeH);

		}

	}
	/** 通过子view的索引，计算出此view的坐标点 */
	protected Point getCoorFromIndex(int index) {
		int col = index % colCount;
		int row = index / colCount;
		
		return new Point(padding + (childSizeW + padding) * col,
						 padding + (childSizeH + padding) * row - 0);
	}
	
	private int getLastIndex(){
		return getIndexFromCoor(lastX, lastY);
	}
	
	/** 通过坐标，返回子view的索引 */
	private int getIndexFromCoor(int x, int y){
		Log.i("info", "touch X == " + x);
		Log.i("info", "touch Y == " + y);
		
		int col = getColOrRowFromCoor(x, childSizeW);
		int row = getColOrRowFromCoor(y, childSizeH);
		if(col == -1 || row == -1){
			return -1;
		}
		
		Log.i("info", "dragged View col=== " + col);
		Log.i("info", "dragged View row == " + row);
		
		int index = row * colCount + col;
		Log.i("info", "dragged View is == " + index);
		
		if(index >= getChildCount()){
			return -1;
		}
		return index;
	}
	
	/** 通过子View的坐标信息， 计算此View的行或列 */
	private int getColOrRowFromCoor(int coor, int childWOrH){
		float indexLog = coor / childWOrH;
		if(indexLog < 0){
			Log.i("info", "coor/childWOrH is=== 0");
		}
		int log = Math.round(indexLog);
		Log.i("info", "Math.round(indexLog) is=== " + log);
		
		coor -= padding;
		for (int i = 0; 0 < coor; i++) {
			if(coor < childWOrH){
				return i;
			} else {
				coor -= (childWOrH + padding);
			}
		}
		return -1;
	}
	/** 拖拽动画 */
	private void animateDragged(){
		View v = getChildAt(draggedViewIndex);
		int x = getCoorFromIndex(draggedViewIndex).x + childSizeW / 2;
		int y = getCoorFromIndex(draggedViewIndex).y + childSizeH / 2;
        int l = x - (3 * childSizeW / 4), t = y - (3 * childSizeH / 4);
    	v.layout(l, t, l + (childSizeW * 3 / 2), t + (childSizeH * 3 / 2));
    	AnimationSet anim = new AnimationSet(true);
    	ScaleAnimation scale = new ScaleAnimation(0.667f, 1, 0.67f, 1, childSizeW*3/4, childSizeH*3/4);
    	scale.setDuration(150);
    	
    	AlphaAnimation alpha = new AlphaAnimation(1, 0.5f);
    	alpha.setDuration(150);
    	
    	anim.addAnimation(scale);
    	anim.addAnimation(alpha);
    	anim.setFillEnabled(true);
    	anim.setFillAfter(true);
    	
		v.clearAnimation();
		v.startAnimation(anim);
	}
	@Override
	public boolean onLongClick(View v) {
		if(!isEnable){
			return false;
		}
		int selectViewIndex = getLastIndex();
		if(selectViewIndex != -1){
			draggedViewIndex = selectViewIndex;
			animateDragged();
			return true;
		}
		return false;
	}
	
	private int getTargetFromCoor(int x, int y){
		if(getColOrRowFromCoor(y + scroll, childSizeH) == -1){
			return -1;
		}
		
		int leftView = getIndexFromCoor(x - (childSizeW/4), y);
		int rightView = getIndexFromCoor(x + (childSizeW/4), y);
		if(leftView < -1 && rightView < -1){
			return -1;
		}
		
		if(leftView == rightView){
			return -1;
		}
		
		int target = -1;
		if(leftView > -1){
			target = leftView;
		} else if(rightView > -1){
			target = rightView;
		}
		
		if(draggedViewIndex < target){
			return target - 1;
		}
		
		return target;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		// 多点触控
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			isEnable = true;
			lastX = (int) event.getX();
			lastY = (int) event.getY();
			touching = true;
			break;
			
		case MotionEvent.ACTION_MOVE:
			int delta = (int)(lastY - event.getY());
			
			if(draggedViewIndex != -1){ // 可以进行拖拽
				int coorX = (int) event.getX();
				int coorY = (int) event.getY();
				int left = coorX - (3*childSizeW/4);
				int top = coorY - (3*childSizeH/4);
				getChildAt(draggedViewIndex).layout(left, top, left+(3*childSizeW/4), top+(3*childSizeH/4));
				
				int target = getTargetFromCoor(coorX, coorY);
				if(lastTarget != target){
					if(target != -1){
						animateGap(target);
						lastTarget = target;
					}
				}
				
			} else { // 布局滑动，（类似 ScrollView的效果）
				scroll += delta;
				clampScroll();
				if(Math.abs(delta) > 2){
					isEnable = false;
				}
				onLayout(true, getLeft(), getTop(), getRight(), getBottom());
			}
			
			lastX = (int) event.getX();
			lastY = (int) event.getY();
			lastDelta = delta;
			
			break;
			
		case MotionEvent.ACTION_UP:
			if(draggedViewIndex != -1){
				View v_up = getChildAt(draggedViewIndex);
				if(lastTarget != -1){
					reorderChildren();
					
				} else {
					Point xy = getCoorFromIndex(draggedViewIndex);
					v_up.layout(xy.x, xy.y, xy.x+childSizeW, xy.y+childSizeH);
				}
				
				v_up.clearAnimation();
				
				if(v_up instanceof ImageView){
					((ImageView)v_up).setAlpha(255);
				}
				lastTarget = -1;
				draggedViewIndex = -1;
			}
			touching =false;
			break;
		}
		
		if(draggedViewIndex != -1){
			return true;
		}
		
		return false;
	}

	@Override
	public void onClick(View v) {
		if(isEnable){
			if (secondaryOnClickListener != null)
    			secondaryOnClickListener.onClick(v);
    		if (onItemClickListener != null && getLastIndex() != -1)
    			onItemClickListener.onItemClick(null, getChildAt(getLastIndex()), getLastIndex(), getLastIndex() / colCount);
		}
		
	}
	
	/** 设置交换动画 */
	private void animateGap(int target){
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			if(i == draggedViewIndex){
				continue;
			}
			
			int index = i;
			// 判断，拖拽的view索引 与 目标view的索引之间的所有view的索引减一， 向上移动一位。 其它的view索引不变
			if(draggedViewIndex < target && i >= draggedViewIndex+1 && i <= target){
				index --;
				
			// 判断，拖拽的view索引 与 目标view的索引之间的所有view的索引加一， 向下移动一位。 其它的view索引不变
			} else if(draggedViewIndex > target && i >= target && i < draggedViewIndex){
				index ++;
			}
			
			int oldPos = i;
			if(newPos.get(i) != -1){
				oldPos = newPos.get(i);
			}
			if(oldPos == index){
				continue;
			}
			
			Point oldXY = getCoorFromIndex(oldPos);
			Point newXY = getCoorFromIndex(index);
			Point oldOffest = new Point(oldXY.x-v.getLeft(), oldXY.y-v.getTop());
			Point newOffest = new Point(newXY.x-v.getLeft(), newXY.y-v.getTop());
			
			TranslateAnimation anim = new TranslateAnimation(Animation.ABSOLUTE, oldOffest.x, 
															 Animation.ABSOLUTE, newOffest.x, 
															 Animation.ABSOLUTE, oldOffest.y,
															 Animation.ABSOLUTE, newOffest.y);
			anim.setFillAfter(true);
			anim.setDuration(150);
			anim.setFillEnabled(true);
			v.clearAnimation();
			v.setAnimation(anim);
			v.startAnimation(anim);
			newPos.set(i, index);
		}
	}
	
	private void clampScroll(){
		int stretch = 3;
		int overreach = getHeight() / 2;
		int rowCount = (int) Math.ceil((double)getChildCount() / colCount);
		int max = rowCount * childSizeH + (rowCount + 1) * padding - getHeight();
		max = Math.max(max, 0);
		if(scroll < -overreach){
			scroll = -overreach;
			lastDelta = 0;
		} else if(scroll > max + overreach){
			lastDelta = 0;
			scroll = max + overreach;
		} else if(scroll < 0){
			if(scroll >= -stretch){
				scroll = 0;
			} else if(!touching){
				scroll -= scroll / stretch;
			}
		} else if(scroll > max){
			if(scroll <= max + stretch){
				scroll = max;
			} else if(!touching){
				scroll += (max - scroll) / stretch;
			}
		}
	}
	
	/** 对子view进行重新布局 */
	private void reorderChildren(){
		ArrayList<View> views = new ArrayList<View>();
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).clearAnimation();
			views.add(getChildAt(i));
		}
		removeAllViews();
		
		while (draggedViewIndex != lastTarget) {
			if(lastTarget == views.size()){
				views.add(views.remove(draggedViewIndex));
				draggedViewIndex = lastTarget;
				
			} else if(draggedViewIndex > lastTarget){
				Collections.swap(views, draggedViewIndex, draggedViewIndex - 1);
				draggedViewIndex --;
				
			} else if(draggedViewIndex < lastTarget){
				Collections.swap(views, draggedViewIndex, draggedViewIndex + 1);
				draggedViewIndex ++;
			}
		}
		
		for (int i = 0; i < views.size(); i++) {
			newPos.set(i, -1);
			addView(views.get(i));
		}
		
		onLayout(true, getLeft(), getTop(), getRight(), getBottom());
	}
	
    //OTHER METHODS
    public void setOnRearrangeListener(OnRearrangeListener l)
    {
    	this.onRearrangeListener = l;
    }
    public void setOnItemClickListener(OnItemClickListener l)
    {
    	this.onItemClickListener = l;
    }
/*	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		
		int count = getChildCount();
		View mView = null;
		if(count > 0){
			for (int i = 0; i < count; i++) {
				mView = getChildAt(i);
				if(mView != null){
					drawChild(canvas, mView, getDrawingTime());
				}
			}
		}
	}*/

}
