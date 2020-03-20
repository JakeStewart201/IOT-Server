package com.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON extends HttpServlet {

	private static final long serialVersionUID = 8075295679650523677L;

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
	    String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
	    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    try {
	    	
			Object obj = new JSONParser().parse(new InputStreamReader(fileContent, request.getCharacterEncoding()));
			
			JSONObject jo = (JSONObject)obj;
			
			if (jo.containsKey("sensors")) { //This is for setup
				double lon = (double)jo.get("long");
				double lat = (double)jo.get("lat");

				boolean hasHum = false;
				boolean hasTemp = false;
				boolean hasSoil = false;
				boolean hasLight = false;
				
				JSONArray sensors = (JSONArray)jo.get("sensors");
				for (Object ob : sensors) {
					String s = (String)ob;
					switch(s) {
						case "H":
							hasHum = true;
							break;
						case "T":
							hasTemp = true;
							break;
						case "S":
							hasSoil = true;
							break;
						case "L":
							hasLight = true;
							break;
						default:
							throw new IOException("Invalid Sensor type");
					}
				}
				
				//TODO insert result into database
				
				//TODO form response JSON
				//{"device-id": 12, "sensor-dict": {"H": 9, "T": 10, "S": 11, "L": 12}}
			} else {
				int deviceId = (int)jo.get("device-id");
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
				Date time = formatter.parse((String)jo.get("timestamp"));
			}
			
			
			
			
		} catch (ParseException | java.text.ParseException e) {
			throw new IOException("Invalid JSON format");
		}
	    
	}
	
}
