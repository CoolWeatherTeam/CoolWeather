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
import android.os.Bundle;
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
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class Search extends Fragment  {

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
	}

	private TextView txtSearchCity, txtResultTempMin, txtResultTempMax, txtSearchTerm;
	private Button btnSearchTerm;
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
//		edtSearchTerm= (EditText) rootView.findViewById(R.id.edtSearchTerm);
		txtSearchCity = (TextView) rootView.findViewById(R.id.txtSearchCity);
		txtResultTempMin = (TextView) rootView.findViewById(R.id.txtResultTempMin);
		txtResultTempMax = (TextView) rootView.findViewById(R.id.txtResultTempMax);
		txtSearchTerm = (TextView) rootView.findViewById(R.id.txtSearchTerm);
		btnSearchTerm = (Button) rootView.findViewById(R.id.btnSearchDetails);
		
		btnSearchTerm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intentDetails = new Intent(getActivity(), DetailsActivity.class);
//				intentDetails.putExtra("City", edtSearchTerm.getText().toString());
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
			JSONArray prediction = cityData.getJSONArray("list");
			String ciudad = cityData.getJSONObject("city").getString("name"), 
				pais = cityData.getJSONObject("city").getString("country");
			int max = prediction.getJSONObject(0).getJSONObject("temp").getInt("max");
			int min = prediction.getJSONObject(0).getJSONObject("temp").getInt("min");
			
			txtSearchTerm.setText(pCity + " " + min + " " + max);
			txtSearchCity.setText(pCity);
			txtResultTempMin.setText(min+"");
			txtResultTempMax.setText(max+"");

		} catch(JSONException e) {
			e.printStackTrace();
		}
		
	}

}
