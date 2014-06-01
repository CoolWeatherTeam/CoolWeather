package org.das.coolweather.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
			
			String humidity = day.getString("humidity"), 
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
			
			String fecha = getDate(i);
			
			String icon = day.getJSONArray("weather").getJSONObject(0).getString("icon");
			DayInfo aDay = new DayInfo(fecha, icon, tempMin, tempMax, speed, humidity, rain, pressure);
			listDays.add(aDay);				
		}

		DayListAdapter listAdapter = new DayListAdapter(getActivity(), listDays);
		daysListView.setAdapter(listAdapter);
	}

	private String getDate(int i) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.add(Calendar.DAY_OF_MONTH, i);
		Date aDate = aCalendar.getTime();
		SimpleDateFormat formato = 
			    new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
		String fecha = formato.format(aDate);
		return fecha;
	}
	
}
