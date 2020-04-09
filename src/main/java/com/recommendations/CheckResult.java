package com.recommendations;

public class CheckResult {

	private String ok;
	private String message;
	private String type;
	
	public CheckResult (String b, String s, String type) {
		
		ok = b;
		message = s;
		this.type = type;
	}
	
	public String getOk () {
		
		return ok;
	}
	
	public String getMessage () {
		
		return "{'t': '" + ok + "', 'm':'" + message + "', 'i':'" + type + "'}";
	}
}
