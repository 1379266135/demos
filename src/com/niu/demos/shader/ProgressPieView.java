package com.niu.demos.shader;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.niu.demos.R;

public class ProgressPieView extends View {
	public interface OnProgressListener {
        public void onProgressChanged(int progress, int max);
        public void onProgressCompleted();
    }

    /**
     * Fills the progress radially in a clockwise direction.
     */
    public static final int FILL_TYPE_RADIAL = 0;
    /**
     * Fills the progress expanding from the center of the view.
     */
    public static final int FILL_TYPE_CENTER = 1;

	public static final int SLOW_ANIMATION_SPEED = 50;
    public static final int MEDIUM_ANIMATION_SPEED = 25;
    public static final int FAST_ANIMATION_SPEED = 1;

    private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_PROGRESS = 0;
    private static final int DEFAULT_START_ANGLE = -90;
    private static final float DEFAULT_STROKE_WIDTH = 3f;
    private static final float DEFAULT_TEXT_SIZE = 14f;
    private static final int DEFAULT_VIEW_SIZE = 96;

    private OnProgressListener mListener;
    private DisplayMetrics mDisplayMetrics;
    private int mMax = DEFAULT_MAX;
    private int mProgress = DEFAULT_PROGRESS;
    private int mStartAngle = DEFAULT_START_ANGLE;
    private boolean mInverted = false;
    private boolean mCounterclockwise = false;
    private boolean mShowStroke = true;
    private float mStrokeWidth = DEFAULT_STROKE_WIDTH;
    private boolean mShowText = true;
    private float mTextSize = DEFAULT_TEXT_SIZE;
    private Paint mStrokePaint;
    private Paint mTextPaint;
    private Paint mProgressPaint;
    private Paint mBackgroundPaint;
    private RectF mInnerRectF;
    private RectF mOvalRectF;
    private int mProgressFillType = FILL_TYPE_RADIAL;
	
	private int mAnimationSpeed = MEDIUM_ANIMATION_SPEED;
//    private AnimationHandler mAnimationHandler = new AnimationHandler();

    private int mViewSize;
    
	private float maskX;
	private Bitmap b;

    public ProgressPieView(Context context) {
        this(context, null);
    }

    public ProgressPieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressPieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    	shaderMatrix = new Matrix();
    	
        mDisplayMetrics = context.getResources().getDisplayMetrics();

        mStrokeWidth = mStrokeWidth * mDisplayMetrics.density;
        mTextSize = mTextSize * mDisplayMetrics.scaledDensity;

//        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressPieView);
//        final Resources res = getResources();

//        mMax = a.getInteger(R.styleable.ProgressPieView_max, mMax);
//        mProgress = a.getInteger(R.styleable.ProgressPieView_progress, mProgress);
//        mStartAngle = a.getInt(R.styleable.ProgressPieView_startAngle, mStartAngle);
//        mInverted = a.getBoolean(R.styleable.ProgressPieView_inverted, mInverted);
//        mCounterclockwise = a.getBoolean(R.styleable.ProgressPieView_counterclockwise, mCounterclockwise);
//        mStrokeWidth = a.getDimension(R.styleable.ProgressPieView_strokeWidth, mStrokeWidth);
//        mTypeface = a.getString(R.styleable.ProgressPieView_typeface);
//        mTextSize = a.getDimension(R.styleable.ProgressPieView_android_textSize, mTextSize);
//        mText = a.getString(R.styleable.ProgressPieView_android_text);

//        mShowStroke = a.getBoolean(R.styleable.ProgressPieView_showStroke, mShowStroke);
//        mShowText = a.getBoolean(R.styleable.ProgressPieView_showText, mShowText);
//        mImage = a.getDrawable(R.styleable.ProgressPieView_image);

//        int backgroundColor = res.getColor(R.color.default_background_color);
//        backgroundColor = a.getColor(R.styleable.ProgressPieView_backgroundColor, backgroundColor);
//        int progressColor = res.getColor(R.color.default_progress_color);
//        progressColor = a.getColor(R.styleable.ProgressPieView_progressColor, progressColor);
//        int strokeColor = res.getColor(R.color.default_stroke_color);
//        strokeColor = a.getColor(R.styleable.ProgressPieView_strokeColor, strokeColor);
//        int textColor = res.getColor(R.color.default_text_color);
//        textColor = a.getColor(R.styleable.ProgressPieView_android_textColor, textColor);
//
//        mProgressFillType = a.getInteger(R.styleable.ProgressPieView_progressFillType, mProgressFillType);
//
//        a.recycle();

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mProgressPaint.setColor(progressColor);
        mProgressPaint.setStyle(Paint.Style.FILL);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(mStrokeWidth);

        mInnerRectF = new RectF();
        mOvalRectF = new RectF();
        
        createShader();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        mViewSize = Math.min(width, height);

        setMeasuredDimension(width, height);
    }
    
	private Drawable wave; // 波纹图片
	private BitmapShader shader;
    
	/**
	 * Create the shader draw the wave with current color for a background
	 * repeat the bitmap horizontally, and clamp colors vertically
	 */
	private void createShader() {

		if (wave == null) {
			wave = getResources().getDrawable(R.drawable.wave);
		}
		int waveW = wave.getIntrinsicWidth();
		int waveH = wave.getIntrinsicHeight();
		
		b = Bitmap.createBitmap(waveW, waveH, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.drawColor(mBackgroundPaint.getColor());
		wave.setBounds(0, 0, waveW, waveH);
		wave.draw(c);

		shader = new BitmapShader(b, Shader.TileMode.REPEAT,Shader.TileMode.CLAMP);
		mProgressPaint.setShader(shader);
		
	}
    
	private Matrix shaderMatrix; // 阴影矩阵
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        mInnerRectF.set(0, 2, mViewSize-5, mViewSize-5);
        mInnerRectF.offset((getWidth() - mViewSize) / 2, (getHeight() - mViewSize) / 2);
        mOvalRectF.set(0, mInnerRectF.bottom-(mProgress * mViewSize / 100-10), mViewSize+20, mInnerRectF.bottom);

        if (mShowStroke) {
            canvas.drawOval(mInnerRectF, mStrokePaint);
        }
        
        canvas.drawArc(mInnerRectF, 0, 360, true, mProgressPaint);

        switch (mProgressFillType) {
            case FILL_TYPE_RADIAL:
                
        		// modify text paint shader according to sinking state
        		if (shader != null) {

        			// first call after sinking, assign it to our paint
        			if (mProgressPaint.getShader() == null) {
        				mProgressPaint.setColor(mBackgroundPaint.getColor());
        				mProgressPaint.setShader(shader);
        			}

        			// translate shader accordingly to maskX maskY positions
        			// maskY is affected by the offset to vertically center the wave
        			maskX += 45;
        			shaderMatrix.setTranslate(maskX, mInnerRectF.bottom-(mProgress * mViewSize / 100) - 300);

        			// assign matrix to invalidate the shader
        			shader.setLocalMatrix(shaderMatrix);
        		} else {
        			mProgressPaint.setShader(null);
        		}
                break;
            default:
                throw new IllegalArgumentException("Invalid Progress Fill = " + mProgressFillType);
        }

    }

    /**
     * Gets the maximum progress value.
     */
    public int getMax() {
        return mMax;
    }

    /**
     * Sets the maximum progress value. Defaults to 100.
     */
    public void setMax(int max) {
        if (max <= 0 || max < mProgress) {
            throw new IllegalArgumentException(
                    String.format("Max (%d) must be > 0 and >= %d", max, mProgress));
        }
        mMax = max;
        invalidate();
    }
	

    /**
     * Sets the current progress (must be between 0 and max).
     */
    public void setProgress(int progress) {
        if (progress > mMax || progress < 0) {
            throw new IllegalArgumentException(
                    String.format("Progress (%d) must be between %d and %d", progress, 0, mMax));
        }
        mProgress = progress;
        if (null != mListener) {
            if (mProgress == mMax) {
                mListener.onProgressCompleted();
            } else {
                mListener.onProgressChanged(mProgress, mMax);
            }
        }
        invalidate();
    }

    /**
     * Sets the start angle the {@link #FILL_TYPE_RADIAL} uses.
     * @param startAngle start angle in degrees
     */
    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
    }

    /**
     *  Sets the color used to display the progress of the view.
     *  @param color - color of the progress part of the view
     */
    public void setProgressColor(int color) {
        mProgressPaint.setColor(color);
        invalidate();
    }

    /**
     *  Get the color used to display the stroke of the view.
     */
    public int getStrokeColor() {
        return mStrokePaint.getColor();
    }

    /**
     * Sets the color used to display the stroke of the view.
     * @param color - color of the stroke part of the view
     */
    public void setStrokeColor(int color) {
        mStrokePaint.setColor(color);
        invalidate();
    }

    /**
     * Gets the progress fill type.
     */
    public int getProgressFillType() {
        return mProgressFillType;
    }

    /**
     * Sets the progress fill type.
     * @param fillType one of {@link #FILL_TYPE_CENTER}, {@link #FILL_TYPE_RADIAL}
     */
    public void setProgressFillType(int fillType) {
        mProgressFillType = fillType;
    }

    /**
     * Sets the progress listner.
     * @param listener progress listener
     *
     * @see com.filippudak.ProgressPieView.ProgressPieView.OnProgressListener
     */
    public void setOnProgressListener(OnProgressListener listener) {
        mListener = listener;
    }

}
