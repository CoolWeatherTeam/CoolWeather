package org.das.coolweather.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.das.coolweather.R;
import org.das.coolweather.adapters.DayInfo;
import org.das.coolweather.adapters.DayListAdapter;
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
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TableRow;

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
			result = getApplicationContext().getString(R.string.TodayIn) + " " + ciudad + ", " + pais + 
					getApplicationContext().getString(R.string.ThereIsAPrevision)+ " " + weather +
					getApplicationContext().getString(R.string.AndTempMax) + " " + tempMax + 
					getApplicationContext().getString(R.string.AndTempMin) + " " + tempMin;
			
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
	public static String[] calcularDias(){
		String[] dias= new String[]{"L", "M", "X", "J", "V", "S", "D"};;
		Calendar c= new GregorianCalendar();
		int dia=c.get(Calendar.DAY_OF_WEEK);
	    switch (dia) {
	            case Calendar.TUESDAY:
	            	dias= new String[]{"M", "X", "J", "V", "S", "D","L"};
                     break;
	            case Calendar.WEDNESDAY:
	            	dias= new String[]{"X", "J", "V", "S", "D","L", "M"};
                     break;
	            case Calendar.THURSDAY:
	            	dias= new String[]{"J", "V", "S", "D","L", "M", "X"};
                     break;
	            case Calendar.FRIDAY:
	            	dias= new String[]{"V", "S", "D","L", "M", "X", "J"};
                     break;
	            case Calendar.SATURDAY:
	            	dias= new String[]{"S", "D","L", "M", "X", "J", "V",};
                     break;
	            case Calendar.SUNDAY:
	            	dias= new String[]{"D","L", "M", "X", "J", "V", "S" };
                     break;
	    }
		return dias;
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
			View rootView = inflater.inflate(R.layout.details_week, container, false);
			
			try {
				setupListView(rootView);
				setupChart(rootView);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		return rootView;
		}

		private void setupChart(View rootView) throws JSONException {
			LineGraphView l1 = new LineGraphView(getActivity().getApplicationContext(), getActivity().getString(R.string.WeeklyTemperatures));
		        
	        //Definimos la grafica
			GraphView graphView =l1;
	        float[] maximas=new float[7],minimas=new float[7];
        	JSONArray hoy = data.getJSONArray("list");
        	for(int i=0;i<hoy.length();i++){
    			
    			maximas[i] = Float.parseFloat(hoy.getJSONObject(i).getJSONObject("temp").getString("max"));
    			minimas[i] = Float.parseFloat(hoy.getJSONObject(i).getJSONObject("temp").getString("min"));
        	
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
	        GraphViewSeries maximos = new GraphViewSeries(getActivity().getString(R.string.Maximus), estilo, gr1);
	        
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
	        GraphViewSeries minimos = new GraphViewSeries(getActivity().getString(R.string.Minimus),estilo,gr1);
	        
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
	        graphView.setHorizontalLabels(calcularDias());
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
		}

		private void setupListView(View rootView) throws JSONException {
			ListView daysListView = (ListView)rootView.findViewById(R.id.daysListView);
			
			JSONArray days = data.getJSONArray("list");
			ArrayList<DayInfo> listDays = new ArrayList<DayInfo>(days.length());
			String[] daysNames= calcularDias();
			
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
					
				DayInfo aDay = new DayInfo(daysNames[i],icon, tempMin, tempMax, speed, clouds, rain, pressure);
				listDays.add(aDay);				
			}

			DayListAdapter listAdapter = new DayListAdapter(getActivity(), listDays);
			daysListView.setAdapter(listAdapter);
		}
	
	}
}
