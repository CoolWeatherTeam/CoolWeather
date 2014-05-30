package org.das.coolweather.fragments;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.das.coolweather.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Graph extends Fragment {

	private JSONObject data;
	
	public static Fragment newInstance() {
		Graph fragment = new Graph();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.graph,
				container, false);
		try {
			data = new JSONObject(getActivity().getIntent().getStringExtra("JSON_DATA"));
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
    	JSONArray datosSemana = data.getJSONArray("list");
    	
    	GraphViewData[] maxData = new GraphViewData[datosSemana.length()];
    	GraphViewData[] minData = new GraphViewData[datosSemana.length()];
    	for(int i = 0; i < datosSemana.length(); i++){
			
			double maxima = Double.parseDouble(datosSemana.getJSONObject(i).getJSONObject("temp").getString("max"));
			double minima = Double.parseDouble(datosSemana.getJSONObject(i).getJSONObject("temp").getString("min"));
			maxData[i] = new GraphViewData(i, maxima);
			minData[i] = new GraphViewData(i, minima);
    	}
       

        //Creamos un estilo para la linea
        GraphViewSeriesStyle estilo= new GraphViewSeriesStyle(Color.RED,4);
        //Los añadimos a una serie, objeto que se añade al grafico
        GraphViewSeries maximos = new GraphViewSeries(getActivity().getString(R.string.Maximus), estilo, maxData);
        
        //annadimos los datos 
        graphView.addSeries(maximos);
        
        estilo = new GraphViewSeriesStyle(Color.WHITE, 4);
        //Los añadimos a una serie, objeto que se añade al grafico
        GraphViewSeries minimos = new GraphViewSeries(getActivity().getString(R.string.Minimus), estilo, minData);
        
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
        graphView.getGraphViewStyle().setNumVerticalLabels(maxData.length);
        	//numero de divisiones horizontales ( una por dia de la semana)
//        graphView.getGraphViewStyle().setNumHorizontalLabels(7);
        	//Mostramos la leyenda
        graphView.setShowLegend(true);
        	//Posicionamos el la leyenda en la parte inferior derecha
        graphView.setLegendAlign(LegendAlign.BOTTOM);
        	//Definimos la guia horizontal con los dias de la semana 
        graphView.setHorizontalLabels(calcularDias());
    		//Posicionamos la camara en el centro de la grafica
//        graphView.setMinimumHeight(500);
//		graphView.setMinimumWidth(300);
			
		LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.graph);
		layout.addView(graphView);
	}
	
	private String[] calcularDias(){
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

}
