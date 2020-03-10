import java.util.*;

public class BestFitResult {

	private Boolean resultFound;
	private LinkedList<Plant> plantResults = new LinkedList<Plant>();
	private String message;
	
	public BestFitResult(Boolean f, Plant p) {
		
		resultFound = f;
		plantResults.add(p);
	}
	
	public BestFitResult (Boolean f, String e) {
		
		resultFound = f;
		message = e;
	}
	
	public LinkedList<Plant> getResultsList () {
		
		return plantResults;
	}
	
	public Boolean getFound () {
		
		return resultFound;
	}
	
	public String getMessage () {
		
		return message;
	}
}
