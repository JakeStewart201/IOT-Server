package com.recommendations;

public class Plant {

	private String name;
	private double minTemperature;
	private double maxTemperature;
	private double minHumidity;
	private double maxHumidity;
	private double minSoilMoisture;
	private double maxSoilMoisture;
	private double minLightLevel;
	private double maxLightLevel;
	
	public Plant (String n, double mint, double maxt, double minh, double maxh, double mins, double maxs, double minl, double maxl) {
		
		name = n;
		minTemperature = mint;
		maxTemperature = maxt;
		minHumidity = minh;
		maxHumidity = maxh;
		minSoilMoisture = mins;
		maxSoilMoisture = maxs;
		minLightLevel = minl;
		maxLightLevel = maxl;
	}
	
	public String getName() {
		
		return name;
	}
	
	public double getMinTemperature() {
		
		return minTemperature;
	}
	
    public double getMaxTemperature() {
		
		return maxTemperature;
	}
	
	public double getMinHumidity() {
		
		return minHumidity;
	}
	
    public double getMaxHumidity() {
		
		return maxHumidity;
	}
	
	public double getMinSoilMoisture() {
		
		return minSoilMoisture;
	}
	
	public double getMaxSoilMoisture() {
		
		return maxSoilMoisture;
	}
	
	public double getMinLightLevel() {
		
		return minLightLevel;
	}
	
    public double getMaxLightLevel() {
		
		return maxLightLevel;
	}
}
