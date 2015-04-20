package com.niu.demos.notify;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.niu.demos.R;

public class NotificationFilterActivity extends Activity {
	protected MyReceiver mReceiver = new MyReceiver();
    public static String INTENT_ACTION_NOTIFICATION = "it.gmariotti.notification";

    protected TextView title;
    protected TextView text;
    protected TextView subtext;
    protected ImageView largeIcon;
    private Button btnListener;
    private static final Intent sSettingsIntent =
            new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_filter_layout);

        //Retrieve ui elements
        title = (TextView) findViewById(R.id.nt_title);
        text = (TextView) findViewById(R.id.nt_text);
        subtext = (TextView) findViewById(R.id.nt_subtext);
        largeIcon = (ImageView) findViewById(R.id.nt_largeicon);
        btnListener = (Button) findViewById(R.id.btn_service);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH){
        	btnListener.setVisibility(View.VISIBLE);
        	btnListener.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivityForResult(sSettingsIntent, 100);
				}
			});
        } else {
        	btnListener.setVisibility(View.GONE);
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_autorize:
            	Intent i = new Intent();
            	if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            		i.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            	} else {
            		i.setClass(this, MyAccesstService.class);
            	}
            	startService(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReceiver == null) mReceiver = new MyReceiver();
        registerReceiver(mReceiver, new IntentFilter(INTENT_ACTION_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                Bundle extras = intent.getExtras();
                String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
                int notificationIcon = extras.getInt(Notification.EXTRA_SMALL_ICON);
                Bitmap notificationLargeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
                CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
                CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

                title.setText(notificationTitle);
                text.setText(notificationText);
                subtext.setText(notificationSubText);

                if (notificationLargeIcon != null) {
                    largeIcon.setImageBitmap(notificationLargeIcon);
                }
            }

        }
    }
}
