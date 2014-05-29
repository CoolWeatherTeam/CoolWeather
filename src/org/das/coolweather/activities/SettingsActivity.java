package org.das.coolweather.activities;


import org.das.coolweather.R;
import org.das.coolweather.utils.LaBD;
import org.das.coolweather.utils.WeatherHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsActivity extends Activity {

	public static final String TEMPERATURE = "TEMPERATURE";
	public static final String EMPTY_DB = "button";
	public static final String PREDICTION_LANG = "PREDICTION_LANG";
	
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
                	AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                	dialog.setPositiveButton("Si", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							LaBD.getMiBD(getActivity()).vaciar();
						}
					});
                	dialog.setNegativeButton("No", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
                	dialog.setCancelable(true);
                	dialog.setTitle("Borrar favoritos");
                	dialog.setMessage("Atencion: Esta acción no se puede deshacer.");
                	dialog.show();
                	
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
			
			if(key.equals(PREDICTION_LANG)) {
				WeatherHttpClient.PRED_LANG = sharedPreferences.getString(key, "sp");
				return;
			}
		}
	}

}
