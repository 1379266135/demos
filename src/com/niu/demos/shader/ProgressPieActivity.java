package com.niu.demos.shader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.niu.demos.R;

public class ProgressPieActivity extends Activity{
	private static final int SIZE = 96;
    private static final int MARGIN = 8;

    private SeekBar mSeekBar;
    private ProgressPieView mProgressPieViewCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_layout);

        // Code version
        float density = getResources().getDisplayMetrics().density;
        int size = (int) (density * SIZE);
        int margin = (int) (density * MARGIN);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        mProgressPieViewCode = new ProgressPieView(this);
        final LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(margin, margin, margin, margin);
        mProgressPieViewCode.setLayoutParams(layoutParams);
        mProgressPieViewCode.setBackgroundColor(getResources().getColor(R.color.holo_red_dark));
        mProgressPieViewCode.setProgressColor(getResources().getColor(R.color.holo_green_dark));
        mProgressPieViewCode.setStrokeColor(getResources().getColor(R.color.holo_blue_dark));
        mProgressPieViewCode.setStartAngle(720);
        container.addView(mProgressPieViewCode);

        // SeekBar 
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mProgressPieViewCode.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
