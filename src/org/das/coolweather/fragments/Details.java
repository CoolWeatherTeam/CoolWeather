package org.das.coolweather.fragments;

import java.util.ArrayList;
import org.das.coolweather.R;
import org.das.coolweather.adapters.DayInfo;
import org.das.coolweather.adapters.DayListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Details extends Fragment {

	private static JSONObject data;
	
	public Details() {
		
	}
	
	public static Fragment newInstance() {
		Details fragment = new Details();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.details_week, container, false);
		
		try {
			data = new JSONObject(getActivity().getIntent().getStringExtra("JSON_DATA"));
			setupListView(rootView);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return rootView;
	}

	
	private void setupListView(View rootView) throws JSONException {
		ListView daysListView = (ListView)rootView.findViewById(R.id.daysListView);
		
		JSONArray days = data.getJSONArray("list");
		ArrayList<DayInfo> listDays = new ArrayList<DayInfo>(days.length());
		for(int i = 0; i < days.length(); i++) {
			JSONObject day = days.getJSONObject(i);
			
			String clouds = day.getString("clouds"), 
				pressure = day.getString("pressure"), 
				speed = day.getString("speed"), 
				tempMax = day.getJSONObject("temp").getString("max"), 
				tempMin = day.getJSONObject("temp").getString("min");
			String rain = null;
			if(day.names().toString().contains("rain")) {	
				rain = day.getString("rain");
			} else {
				rain = "0";
			}
			
			String icon = day.getJSONArray("weather").getJSONObject(0).getString("icon");
				
			DayInfo aDay = new DayInfo(icon, tempMin, tempMax, speed, clouds, rain, pressure);
			listDays.add(aDay);				
		}

		DayListAdapter listAdapter = new DayListAdapter(getActivity(), listDays);
		daysListView.setAdapter(listAdapter);
	}
	
}
