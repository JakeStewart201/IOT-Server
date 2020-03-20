package com.recommendations;

public class CheckResults {

	CheckResult tempCheck;
	CheckResult humCheck;
	CheckResult soilCheck;
	CheckResult lightCheck;
	
	public CheckResults (CheckResult t, CheckResult h, CheckResult s, CheckResult l) {
		
		tempCheck = t;
		humCheck = h;
		soilCheck = s;
		lightCheck = l;
	}
	
	public CheckResult getTempCheckResult () {
		
		return tempCheck;
	}
	
	public CheckResult getHumCheckResult () {
		
		return humCheck;
	}

	public CheckResult getSoilCheckResult () {
	
		return soilCheck;
	}

	public CheckResult getLightCheckResult () {
	
		return lightCheck;
	}
}
