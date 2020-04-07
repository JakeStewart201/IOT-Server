package com.recommendations;

public class SearchResult {

	Plant plant;
	double tempDif;
	double humDif;
	double soilDif;
	double lightDif;
	
	public SearchResult(Plant p, double t, double h, double s, double l) {
		
		plant = p;
		tempDif = t;
		humDif = h;
		soilDif = s;
		lightDif = l;
	}
	
	public double getTotalDifference() {
		
		return tempDif + humDif + soilDif + lightDif;
	}
}
