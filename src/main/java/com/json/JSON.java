package com.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
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

	private static final String myDriver = "org.gjt.mm.mysql.Driver";
	private static final String myUrl = "jdbc:mysql://localhost/test";
	private static final String usernameDB = "test";
	private static final String passwordDB = "test";

	private Device createDevice(double lon, double lat, boolean hasH, boolean hasT, boolean hasS, boolean hasL) throws ClassNotFoundException, SQLException {
		// create a mysql database connection
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, usernameDB, passwordDB);

		PreparedStatement stmt = conn.prepareStatement("insert into Devices(lon, lat) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setDouble(1, lon);
		stmt.setDouble(2, lat);
		stmt.execute();
		ResultSet rs = stmt.getGeneratedKeys();
		int deviceID = rs.getInt(1);

		int humID = -1, tempID = -1, soilID = -1, lightID = -1;
		
		if (hasH) {
			humID = createSensor(conn, deviceID, "H");
		}
		if (hasT) {
			tempID = createSensor(conn, deviceID, "T");
		}
		if (hasS) {
			soilID = createSensor(conn, deviceID, "S");
		}
		if (hasL) {
			lightID = createSensor(conn, deviceID, "L");
		}
		
		conn.close();
		
		Device result = new Device(deviceID, humID, tempID, soilID, lightID);
		
		return result;
		
	}
	
	private void addReading(int sensorID, int value, Date time) throws ClassNotFoundException, SQLException {
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, usernameDB, passwordDB);
		
		String type = getType(conn, sensorID);
		
		switch (type) {
		case "L":
			addLightReading(conn, time, sensorID, value);
			break;
		case "H":
			addHumidityReading(conn, time, sensorID, value);
			break;
		case "M":
			addSoilMoistureReading(conn, time, sensorID, value);
			break;
		case "T":
			addTemperatureReading(conn, time, sensorID, value);
			break;
		}
		
		conn.close();
	}
	
	private String getType(Connection conn, int sensorID) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("select type from Sensors where sensorID = ?");
		
		stmt.setInt(1, sensorID);
		stmt.execute();
		
		return stmt.getResultSet().getString(1);
	}
	
	private void addLightReading(Connection conn, Date time, int sensorID, int value) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("insert into Light(dateTime, sensorID, value) values(?, ?, ?)");
		stmt.setTimestamp(1, new java.sql.Timestamp(time.getTime()));
		stmt.setInt(2, sensorID);
		stmt.setInt(3, value);
		stmt.execute();
	}
	
	private void addHumidityReading(Connection conn, Date time, int sensorID, int value) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("insert into Humidity(dateTime, sensorID, value) values(?, ?, ?)");
		stmt.setTimestamp(1, new java.sql.Timestamp(time.getTime()));
		stmt.setInt(2, sensorID);
		stmt.setInt(3, value);
		stmt.execute();
	}
	
	private void addSoilMoistureReading(Connection conn, Date time, int sensorID, int value) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("insert into SoilMoisture(dateTime, sensorID, value) values(?, ?, ?)");
		stmt.setTimestamp(1, new java.sql.Timestamp(time.getTime()));
		stmt.setInt(2, sensorID);
		stmt.setInt(3, value);
		stmt.execute();
	}
	
	private void addTemperatureReading(Connection conn, Date time, int sensorID, int value) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("insert into Temperature(dateTime, sensorID, value) values(?, ?, ?)");
		stmt.setTimestamp(1, new java.sql.Timestamp(time.getTime()));
		stmt.setInt(2, sensorID);
		stmt.setInt(3, value);
		stmt.execute();
	}
	
	private int createSensor(Connection conn, int deviceID, String type) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("insert into Sensors(deviceID, type) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setDouble(1, deviceID);
		stmt.setString(2, type);
		stmt.execute();
		ResultSet rs = stmt.getGeneratedKeys();
		int sensorID = rs.getInt(1);
		
		return sensorID;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		InputStream fileContent = filePart.getInputStream();

		try {

			Object obj = new JSONParser().parse(new InputStreamReader(fileContent, request.getCharacterEncoding()));

			JSONObject jo = (JSONObject) obj;

			if (jo.containsKey("sensors")) { // This is for setup
				double lon = (double) jo.get("long");
				double lat = (double) jo.get("lat");

				boolean hasHum = false;
				boolean hasTemp = false;
				boolean hasSoil = false;
				boolean hasLight = false;

				JSONArray sensors = (JSONArray) jo.get("sensors");
				for (Object ob : sensors) {
					String s = (String) ob;
					switch (s) {
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

				Device device = createDevice(lon, lat, hasHum, hasTemp, hasSoil, hasLight);

				JSONObject response = new JSONObject();
				response.put("device-id", device.getDeviceID());

				Map<Object, Object> sensorIDs = new LinkedHashMap<Object, Object>(4);
				sensorIDs.put("H", device.getHumID());
				sensorIDs.put("T", device.getTempID());
				sensorIDs.put("S", device.getSoilID());
				sensorIDs.put("L", device.getLightID());

				response.put("sensor-dict", sensorIDs);
				resp.setContentType("text/json");
				resp.setCharacterEncoding("UTF-8");

				PrintWriter writer = resp.getWriter();

				writer.write(response.toJSONString());

				writer.close();

			} else {
				int deviceId = (int) jo.get("device-id");
				DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
				Date time = formatter.parse((String) jo.get("timestamp"));
				JSONObject sensors = (JSONObject)jo.get("sensor-values");
				sensors.forEach((Object k, Object v) -> {
					try {
						addReading((int)k, (int)v, time);
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				});
			}

		} catch (ParseException | java.text.ParseException | ClassNotFoundException | SQLException e) {
			throw new IOException("Invalid JSON format");
		}

	}

}
