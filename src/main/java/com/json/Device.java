package com.json;

public class Device {

	private int deviceID;
	private int humID;
	private int tempID;
	private int soilID;
	private int lightID;
	
	public Device(int deviceID, int humID, int tempID, int soilID, int lightID) {
		this.deviceID = deviceID;
		this.humID = humID;
		this.tempID = tempID;
		this.soilID = soilID;
		this.lightID = lightID;
	}
	
	public int getDeviceID() {
		return deviceID;
	}
	
	public int getHumID() {
		return humID;
	}
	
	public int getTempID() {
		return tempID;
	}
	
	public int getSoilID() {
		return soilID;
	}
	
	public int getLightID() {
		return lightID;
	}
	
}
