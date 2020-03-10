
public class CheckResult {

	private Boolean ok;
	private String message;
	
	public CheckResult (Boolean b, String s) {
		
		ok = b;
		message = s;
	}
	
	public Boolean getOk () {
		
		return ok;
	}
	
	public String getMessage () {
		
		return message;
	}
}
