package org.das.coolweather.adapters;

import java.util.ArrayList;

import org.das.coolweather.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DayListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<DayInfo> daysInfo;
	
	public DayListAdapter(Context context, ArrayList<DayInfo> daysInfo) {
		this.context = context;
        this.daysInfo = daysInfo;
	}
	
	@Override
	public int getCount() {
		return daysInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return daysInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (View) inflater.inflate(
                    R.layout.daylayout, null);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.imgDay);
        TextView tempMin = (TextView)convertView.findViewById(R.id.txtMinTemp);
        TextView tempMax = (TextView)convertView.findViewById(R.id.txtMaxTemp);
        TextView wind = (TextView)convertView.findViewById(R.id.txtWindDay);
        TextView rain = (TextView)convertView.findViewById(R.id.txtRainDay);
        TextView pressure = (TextView)convertView.findViewById(R.id.txtPressureDay);
        TextView clouds = (TextView)convertView.findViewById(R.id.txtCloudsDay);

        DayInfo aDay = daysInfo.get(position);
        
        tempMin.setText(aDay.getTempMin().toString());
        tempMax.setText(aDay.getTempMax().toString());
        wind.setText(aDay.getWind());
        rain.setText(aDay.getRain().toString());
        pressure.setText(aDay.getPressure().toString());
        clouds.setText(aDay.getClouds().toString());
        
        return convertView;
	}

}
