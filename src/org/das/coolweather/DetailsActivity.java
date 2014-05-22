package org.das.coolweather;

import org.json.JSONException;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		
//		JSONWeatherTask task = new JSONWeatherTask();
//		task.execute(new String[]{getIntent().getExtras().getString("City")});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_details, container, false);
			
		return rootView;
		}
		
	}
	
	
//	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
//		
//
//		private TextView txtCity, txtResultHum, txtResultTemp;
//		private ImageView imgIcon;
//		
//		@Override
//		protected Weather doInBackground(String... params) {
//			Weather weather = new Weather();
//			String data = WeatherHttpClient.getWeatherData(params[0]);
//			
//			try {
//				weather = JSONWeatherParser.getWeather(data);
//				
//				// Let's retrieve the icon
//				weather.iconData = WeatherHttpClient.getImage(weather.currentCondition.getIcon());
//				
//			} catch (JSONException e) {				
//				e.printStackTrace();
//			}
//			return weather;
//			
//		}
		
		
		
		
//		@Override
//		protected void onPostExecute(Weather weather) {			
//			super.onPostExecute(weather);
//			
//			txtCity = (TextView) findViewById(R.id.txtCity);
//			txtResultHum = (TextView) findViewById(R.id.txtResultHum);
//			txtResultTemp = (TextView) findViewById(R.id.txtResultTemp);
//			imgIcon = (ImageView) findViewById(R.id.imgIcon);
//			
//			
//			if (weather.iconData != null && weather.iconData.length > 0) {
//				Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length); 
//				imgIcon.setImageBitmap(img);
//			}
//			
//			txtCity.setText(weather.location.getCity() + "," + weather.location.getCountry());
//			txtCity.setTextSize(0, 64);
//			
//			txtResultHum.setText(weather.currentCondition.getHumidity() + "%");
//			txtResultTemp.setText(Math.round((weather.temperature.getTemp() - 273.15)) + " C�");

			


			
			
//		condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
//		hum.setText("" + weather.currentCondition.getHumidity() + "%");
//		press.setText("" + weather.currentCondition.getPressure() + " hPa");
//		windSpeed.setText("" + weather.wind.getSpeed() + " mps");
//		windDeg.setText("" + weather.wind.getDeg() + "�");
			
//		}
//	}	
}
