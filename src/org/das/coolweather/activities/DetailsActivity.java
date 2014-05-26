package org.das.coolweather.activities;

import org.das.coolweather.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;

public class DetailsActivity extends Activity {

	private static JSONObject data;
	private ShareActionProvider mShareActionProvider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		try {
			data = new JSONObject(getIntent().getStringExtra("JSON_DATA"));
			Log.i("JSON", data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		
		// Set up ShareActionProvider's default share intent
	    MenuItem item = menu.findItem(R.id.menu_item_share);
	    
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
	    mShareActionProvider.setShareIntent(getDefaultIntent());


		return true;
	}
	
	/** Defines a default (dummy) share intent to initialize the action provider.
	  * However, as soon as the actual content to be used in the intent
	  * is known or changes, you must update the share intent by again calling
	  * mShareActionProvider.setShareIntent()
	  */
	private Intent getDefaultIntent() {
	    Intent intent = new Intent(Intent.ACTION_SEND);
	    intent.setType("text/plain");
	    intent.putExtra(Intent.EXTRA_TEXT, getPrediction());
	    return intent;
	}


	private String getPrediction() {
		String result = "";
		try {
			String ciudad = data.getJSONObject("city").getString("name");
			String pais = data.getJSONObject("city").getString("country");
			JSONObject hoy = data.getJSONArray("list").getJSONObject(0);
			String tempMax = hoy.getJSONObject("temp").getString("max");
			String tempMin = hoy.getJSONObject("temp").getString("min");
			String weather = hoy.getJSONArray("weather").getJSONObject(0).getString("description");
			result = "Hoy en " + ciudad + ", " + pais + " hay una prevision de " + weather +
					" y una temp max de " + tempMax + " y min de " + tempMin;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
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
			
			LineGraphView l1 = new LineGraphView(getActivity().getApplicationContext(), "Temperaturas semanales");
		        
		        //Definimos la grafica
		       GraphView graphView =l1;
		        float[] maximas=new float[7],minimas=new float[7];
		        try {
		        	JSONArray hoy = data.getJSONArray("list");
		        	for(int i=0;i<hoy.length();i++){
		    			
		    			maximas[i] = Float.parseFloat(hoy.getJSONObject(i).getJSONObject("temp").getString("max"));
		    			minimas[i] = Float.parseFloat(hoy.getJSONObject(i).getJSONObject("temp").getString("min"));
		        	
		        	}
		        } catch (JSONException e) {
		        	e.printStackTrace();
		        }
		        
		        //Definimos los datos de la linea correspondiente a las temperaturas maximas
		        GraphViewData[] gr1= new GraphViewData[] {
		           	 new GraphViewData(0,maximas[0]),
		           	 new GraphViewData(1,maximas[1]),
		                new GraphViewData(2,maximas[2]),
		                new GraphViewData(3,maximas[3]),
		                new GraphViewData(4,maximas[4]),
		                new GraphViewData(5,maximas[5]),
		                new GraphViewData(6,maximas[6]),
		           };
		        

		        //Creamos un estilo para la linea
		        GraphViewSeriesStyle estilo= new GraphViewSeriesStyle(Color.RED,4);
		        //Los añadimos a una serie, objeto que se añade al grafico
		        GraphViewSeries maximos = new GraphViewSeries("Maximas", estilo, gr1);
		        
		        //annadimos los datos 
		        graphView.addSeries(maximos);
		        
		        
		        //Definimos los datos de la linea correspondiente a las temperaturas maximas
		       gr1= new GraphViewData[] {
		           	 new GraphViewData(0,minimas[0]),
		           	 new GraphViewData(1,minimas[1]),
		                new GraphViewData(2,minimas[2]),
		                new GraphViewData(3,minimas[3]),
		                new GraphViewData(4,minimas[4]),
		                new GraphViewData(5,minimas[5]),
		                new GraphViewData(6,minimas[6]),
		           };
		         estilo = new GraphViewSeriesStyle(Color.WHITE, 4);
		        //Los añadimos a una serie, objeto que se añade al grafico
		        GraphViewSeries minimos = new GraphViewSeries("Minimas",estilo,gr1);
		        
		        //annadimos los datos 
		        graphView.addSeries(minimos); 
		     
		        
		        //Estilos
		        	//Rellenamos la superficie que ocupan las lineas
		        ((LineGraphView) graphView).setDrawBackground(true);
		        ((LineGraphView) graphView).setDrawDataPoints(true);
		        
		        	//Tamanio del texto
		        graphView.getGraphViewStyle().setTextSize(18);
		        	//Color de la rejilla
		        graphView.getGraphViewStyle().setGridColor(Color.WHITE);
		        	//Color de la guia horizontal
		        graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.CYAN);
		        	//Color de la gui vertical
		        graphView.getGraphViewStyle().setVerticalLabelsColor(Color.CYAN);
		        	//Numero de divisiones verticales (Una por dato)
		        graphView.getGraphViewStyle().setNumVerticalLabels(gr1.length);
		        	//numero de divisiones horizontales ( una por dia de la semana)
		        graphView.getGraphViewStyle().setNumHorizontalLabels(7);
		        	//Mostramos la leyenda
		        graphView.setShowLegend(true);
		        	//Posicionamos el la leyenda en la parte inferior derecha
		        graphView.setLegendAlign(LegendAlign.BOTTOM);
		        	//Definimos la guia horizontal con los dias de la semana 
		        graphView.setHorizontalLabels(new String[] {"L", "M", "X", "J", "V", "S", "D"});
		    		//Posicionamos la camara en el centro de la grafica
		        graphView.setViewPort(0, 6);
		    		//Permitimos la opcion de desplazar el grafico
		        graphView.setScrollable(true);
		        	//Permitimos la opcion de re-escalar el grafico
		        graphView.setScalable(true);
		        graphView.setMinimumHeight(500);
				graphView.setMinimumWidth(300);
				
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.graph);
			layout.addView(graphView);
			
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
