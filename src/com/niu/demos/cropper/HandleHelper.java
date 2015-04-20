package com.niu.demos.cropper;

import android.graphics.Rect;

public abstract class HandleHelper {
    private static final float UNFIXED_ASPECT_RATIO_CONSTANT = 1;
    private Edge mHorizontalEdge;
    private Edge mVerticalEdge;
    private EdgePair mActiveEdges;
    
	HandleHelper(Edge horizontalEdge, Edge verticalEdge){
		this.mHorizontalEdge = horizontalEdge;
		this.mVerticalEdge = verticalEdge;
		mActiveEdges = new EdgePair(mHorizontalEdge, mVerticalEdge);
	}
	
	void updateCropWindow(float x, float y, Rect imageRect, float snapRadius){
		final EdgePair mEdgePair = getmActiveEdges();
		final Edge primaryEdge = mEdgePair.primary;
		final Edge secondaryEdge = mEdgePair.secondary;
		if(primaryEdge != null){
//			primaryEdge.a
		}
	}
	
	public EdgePair getmActiveEdges() {
		return mActiveEdges;
	}

	abstract void updateCropWindow(float x, float y, float targetAspecRatio, Rect imageRect, float snapRadius);
}
