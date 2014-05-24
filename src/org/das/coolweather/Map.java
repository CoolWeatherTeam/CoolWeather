package org.das.coolweather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Map extends Fragment {

	private GoogleMap theMap;
	private JSONObject mapData;
	
	public Map() {
	}
	
	public static Map newInstance() {
		Map fragment = new Map();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.map, container,
				false);
		
		theMap = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.elmapa)).getMap();
		theMap.setMyLocationEnabled(true);
		
		theMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				mapData = WeatherHttpClient.getDataFromLocation("lat="+point.latitude + "&lon=" + point.longitude);
				try {
					JSONArray prediction = mapData.getJSONArray("list");
					String ciudad = mapData.getJSONObject("city").getString("name"), 
						pais = mapData.getJSONObject("city").getString("country");
					int max = prediction.getJSONObject(0).getJSONObject("temp").getInt("max");
					int min = prediction.getJSONObject(0).getJSONObject("temp").getInt("min");
					
					theMap.addMarker(new MarkerOptions()
					.position(point)
					.draggable(true)
					.title(ciudad + ", " + pais)
					.snippet("Max: " + max + "\n Min: " + min));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//Log.i("info", data.toString());
				
			}
		});
		

		theMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				Intent i = new Intent(getActivity().getApplicationContext(), 
						DetailsActivity.class);
				i.putExtra("JSON_DATA", mapData.toString());
				startActivity(i);
				
			}
		});
		
		
		theMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker marker) {
				
				Toast.makeText(getActivity(), "Marker en la posicion " + 
						marker.getPosition().toString() + " borrado", 
						Toast.LENGTH_LONG).show();
				marker.remove();
			}

			@Override
			public void onMarkerDrag(Marker marker) {
			}

			@Override
			public void onMarkerDragEnd(Marker marker) {
			}
			
		});
		
		return rootView;
	}

}
