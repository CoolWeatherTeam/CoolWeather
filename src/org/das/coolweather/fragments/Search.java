package org.das.coolweather.fragments;

import org.das.coolweather.R;
import org.das.coolweather.R.id;
import org.das.coolweather.R.layout;
import org.das.coolweather.activities.DetailsActivity;
import org.das.coolweather.utils.WeatherHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class Search extends Fragment  {

	private TextView txtSearchCity, txtResultTempMin, txtResultTempMax, txtTempMin, txtTempMax;
	private ImageButton btnSearchTerm;
	private JSONObject cityData;
	
	public static Search newInstance() {
		Search fragment = new Search();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.search, container,
				false);
		
		txtSearchCity = (TextView) rootView.findViewById(R.id.txtSearchCity);
		txtResultTempMin = (TextView) rootView.findViewById(R.id.txtResultTempMin);
		txtResultTempMax = (TextView) rootView.findViewById(R.id.txtResultTempMax);
		txtTempMin = (TextView) rootView.findViewById(R.id.txtSearchTempMin);
		txtTempMin.setTextSize(20);
		txtTempMax = (TextView) rootView.findViewById(R.id.txtSearchTempMax);
		txtTempMax.setTextSize(20);
		btnSearchTerm = (ImageButton) rootView.findViewById(R.id.btnSearchDetails);
		
		btnSearchTerm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentDetails = new Intent(getActivity(), DetailsActivity.class);
				intentDetails.putExtra("JSON_DATA", cityData.toString());
				startActivity(intentDetails);
				
			}
		});
		return rootView;
	}
	
	public void getMinDetailsFromSearch(String pCity){
		try {
			cityData = WeatherHttpClient.
					getDataFromLocation(
							"q="+ pCity);
			if(cityData == null) {
				throw new JSONException("");
			}
			fillData();

		} catch(JSONException e) {
			Toast.makeText(getActivity(), "No hay internet", Toast.LENGTH_SHORT).show();
		}
		
	}

	private void fillData() throws JSONException {
		JSONArray prediction = cityData.getJSONArray("list");
		String ciudad = cityData.getJSONObject("city").getString("name"), 
			pais = cityData.getJSONObject("city").getString("country");
		int max = prediction.getJSONObject(0).getJSONObject("temp").getInt("max");
		int min = prediction.getJSONObject(0).getJSONObject("temp").getInt("min");
		
		txtSearchCity.setText(ciudad + ", " + pais);
		txtSearchCity.setTextSize(45);
		txtResultTempMin.setText(min + "º");
		txtResultTempMin.setTextSize(30);
		txtResultTempMax.setText(max + "º");
		txtResultTempMax.setTextSize(30);
	}

	@Override
	public void onPause() {
		super.onPause();
		if(cityData != null) {
			SharedPreferences sharedPref = (SharedPreferences)PreferenceManager.
						getDefaultSharedPreferences(getActivity());
			sharedPref.edit()
				.putString("JSON_DATA", cityData.toString())
				.commit();
		}
		
	}

	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences sharedPref = (SharedPreferences)PreferenceManager.
				getDefaultSharedPreferences(getActivity());
		try {
			cityData = new JSONObject(sharedPref.getString("JSON_DATA", ""));
			fillData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
