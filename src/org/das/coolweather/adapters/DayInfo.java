package org.das.coolweather.adapters;

public class DayInfo {
	private String imageSrc, 
		tempMin, 
		tempMax, 
		wind, 
		clouds, 
		rain, 
		pressure,day;
	
	public DayInfo(String day, String imageSrc, String tempMin, 
			String tempMax, String wind, String clouds, String rain, String pressure) {
		this.day=day;
		this.imageSrc = imageSrc;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.wind = wind;
		this.clouds = clouds;
		this.rain = rain;
		this.pressure = pressure;
		
	}

	public String getImageSrc() {
		return imageSrc;
	}
	
	public String getDay() {
		return day;
	}
	
	public String getTempMin() {
		return tempMin;
	}

	public String getTempMax() {
		return tempMax;
	}

	public String getWind() {
		return wind;
	}

	public String getClouds() {
		return clouds;
	}

	public String getRain() {
		return rain;
	}

	public String getPressure() {
		return pressure;
	}
	
}
