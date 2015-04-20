package com.niu.demos;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.util.Log;

public class CheckPreferenceFragment extends PreferenceFragment{
	private CheckBoxPreference checkPreference;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.my_preference);
		checkPreference = (CheckBoxPreference) findPreference("intercept_sms");
		checkPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				Log.i("info", "拦截状态：　" + preference.toString());
				return true;
			}
		});
	}
}
