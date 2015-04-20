package com.niu.demos.cropper;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CropImageView extends FrameLayout {
	
    private static final Rect EMPTY_RECT = new Rect();
	
    // Sets the default image guidelines to show when resizing
    public static final int DEFAULT_GUIDELINES = 1;
    public static final boolean DEFAULT_FIXED_ASPECT_RATIO = false;
    public static final int DEFAULT_ASPECT_RATIO_X = 1;
    public static final int DEFAULT_ASPECT_RATIO_Y = 1;

    private static final int DEFAULT_IMAGE_RESOURCE = 0;

    private static final String DEGREES_ROTATED = "DEGREES_ROTATED";

	public CropImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

}
