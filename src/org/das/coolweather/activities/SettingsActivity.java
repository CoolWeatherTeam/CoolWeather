package org.das.coolweather.activities;


import org.das.coolweather.R;
import org.das.coolweather.utils.LaBD;
import org.das.coolweather.utils.WeatherHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

public class SettingsActivity extends Activity {

	public static final String TEMPERATURE = "TEMPERATURE";
	public static final String EMPTY_DB = "button";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		if (savedInstanceState == null) {
			// Display the fragment as the main content.
	        getFragmentManager().beginTransaction()
	                .replace(android.R.id.content, new SettingsFragment())
	                .commit();
		}
	}
	
	public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.preferences);
	        Preference button = (Preference)findPreference(EMPTY_DB);
	        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference arg0) { 
                    LaBD.getMiBD(getActivity()).vaciar();
                    return true;
                }
            });
	        
	    }

		@Override
		public void onPause() {
			super.onPause();
			getPreferenceManager().getSharedPreferences()
			.unregisterOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onResume() {
			super.onResume();
			getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if(key.equals(TEMPERATURE)) {
				WeatherHttpClient.UNITS = sharedPreferences.getString(key, "metric");
				return;
			}
		}
	}

}
