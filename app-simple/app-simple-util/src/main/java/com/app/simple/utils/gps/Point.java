package com.app.simple.utils.gps;

public class Point {
	double longitude;
	double latitude;
	
	 
	 /**
	 */
	public Point() {
		super();
	}

	/**
	 * @param longitude
	 * @param latitude
	 */
	public Point(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}
}