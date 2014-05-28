package org.das.coolweather.fragments;

import java.util.HashMap;
import org.das.coolweather.R;
import org.das.coolweather.activities.DetailsActivityHost;
import org.das.coolweather.utils.LaBD;
import org.das.coolweather.utils.WeatherHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Map extends Fragment {

	private GoogleMap theMap;
	private JSONObject mapData;
	private HashMap<String, LatLng> markers;
	
	public Map() {
		markers = new HashMap<String, LatLng>();
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
				Marker aMarker = theMap.addMarker(new MarkerOptions()
				.position(point)
				.draggable(true));
				LaBD.getMiBD(getActivity()).addMarker(point.latitude, point.longitude);
				markers.put(aMarker.getId(), aMarker.getPosition());
				
			}
		});
		
		theMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				try {
					mapData = WeatherHttpClient.
							getDataFromLocation(
									"lat="+ marker.getPosition().latitude + 
									"&lon=" + marker.getPosition().longitude);
					if(mapData != null) {						
						JSONArray prediction = mapData.getJSONArray("list");
						String ciudad = mapData.getJSONObject("city").getString("name"), 
								pais = mapData.getJSONObject("city").getString("country");
						int max = prediction.getJSONObject(0).getJSONObject("temp").getInt("max");
						int min = prediction.getJSONObject(0).getJSONObject("temp").getInt("min");
						
						marker.setTitle(ciudad + ", " + pais);
						marker.setSnippet("Max: " + max + "\n Min: " + min);
					} else {
						throw new JSONException("");
					}
				} catch(JSONException e) {
					Toast.makeText(getActivity(), "No hay internet", Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});

		theMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			
			@Override
			public void onInfoWindowClick(Marker marker) {
				Intent i = new Intent(getActivity().getApplicationContext(), 
						DetailsActivityHost.class);
				i.putExtra("JSON_DATA", mapData.toString());
				startActivity(i);
				
			}
		});
		
		
		theMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker marker) {
				
				LatLng markerPosition = markers.get(marker.getId());
				
				Toast.makeText(getActivity(), getActivity().getString(R.string.MarkerInPosition)+ " " + 
						markerPosition.toString() + " " + getActivity().getString(R.string.deleted), 
						Toast.LENGTH_LONG).show();
				
				LaBD.getMiBD(getActivity())
					.removeMarker(markerPosition.latitude, 
							markerPosition.longitude);
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


	
	@Override
	public void onPause() {
		super.onPause();
		theMap.clear();
		markers = new HashMap<String, LatLng>();
	}

	@Override
	public void onResume() {		
		super.onResume();
		Cursor aCursor = LaBD.getMiBD(getActivity()).getMarkers();
		if(aCursor.moveToFirst()){
			do {
				Marker aMarker = theMap.addMarker(new MarkerOptions()
				.position(new LatLng(aCursor.getDouble(0), aCursor.getDouble(1)))
				.draggable(true));
				markers.put(aMarker.getId(), aMarker.getPosition());
			} while(aCursor.moveToNext());
		}
	}

}
