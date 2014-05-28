package org.das.coolweather.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

public class WeatherHttpClient {
	public static String UNITS;
	public static String PRED_LANG;
	private static final String APPID = "670d7fd5ed6be7af97f71f698ba1aad2";
	private static final String MODE = "json";
	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?APPID="+APPID+"&";
	

	public static JSONObject getDataFromLocation(String optionString) {
		
		try {
			return new AsyncTask<String, Void, JSONObject>() {

				@Override
				protected JSONObject doInBackground(String... params) {
					try {
						HttpParams httpParameters = new BasicHttpParams();
						HttpClient httpclient = new DefaultHttpClient(httpParameters);
						HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
						HttpConnectionParams.setSoTimeout(httpParameters, 15000);
						
						HttpGet httpget = new HttpGet(BASE_URL + params[0]);
						HttpResponse response = httpclient.execute(httpget);
						HttpEntity entity = response.getEntity();
						String result = EntityUtils.toString(entity);
						JSONObject data = new JSONObject(result);
						return data;
					} catch (UnsupportedEncodingException e) {
						return null;
					} catch (ClientProtocolException e) {
						return null;
					} catch (IOException e) {
						return null;
					} catch (JSONException e) {
						return null;
					}
				}
				
			}.execute(optionString+"&mode=" + MODE + "&units="+UNITS +"&lang="+PRED_LANG).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
