package com.json;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@javax.servlet.annotation.MultipartConfig
public class Setup extends HttpServlet {

	private static final long serialVersionUID = 2333248848945633977L;

	private Device createDevice(double lon, double lat, boolean hasH, boolean hasT, boolean hasS, boolean hasL)
			throws ClassNotFoundException, SQLException {
		// create a mysql database connection
		System.err.println("Connecting to database");
		Connection conn =  DataBaseInfo.getConnection();

		System.err.println("Inserting device");
		PreparedStatement stmt = conn.prepareStatement("insert into Device(lon, lat) values(?, ?) returning deviceid",
				Statement.RETURN_GENERATED_KEYS);
		stmt.setDouble(1, lon);
		stmt.setDouble(2, lat);
		stmt.execute();
		System.err.println("Device inserted");
		ResultSet rs = stmt.getGeneratedKeys();
		System.err.println("Got keys");
		System.err.println(rs.getMetaData().getColumnName(1));
		int deviceID = rs.next() ? rs.getInt(1) : -1;
		System.err.println("Got device id");
		System.err.println(deviceID);
		int humID = -1, tempID = -1, soilID = -1, lightID = -1;	

		if (hasH) {
			System.err.println("Creating hum");
			humID = createSensor(conn, deviceID, "H");
		}
		if (hasT) {
			System.err.println("Creating temp");
			tempID = createSensor(conn, deviceID, "T");
		}
		if (hasS) {
			System.err.println("Creating soil");
			soilID = createSensor(conn, deviceID, "S");
		}
		if (hasL) {
			System.err.println("Creating light");
			lightID = createSensor(conn, deviceID, "L");
		}

		
		conn.close();
		System.err.println("Closing connection");

		Device result = new Device(deviceID, humID, tempID, soilID, lightID);

		return result;

	}

	private int createSensor(Connection conn, int deviceID, String type) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("insert into Sensors(deviceID, type) values(?, ?) returning sensorid",
				Statement.RETURN_GENERATED_KEYS);
		stmt.setDouble(1, deviceID);
		stmt.setString(2, type);
		stmt.execute();
		ResultSet rs = stmt.getGeneratedKeys();
		int sensorID = rs.next() ? rs.getInt(1) : -1;

		return sensorID;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		System.err.println("Getting input stream");
		InputStream fileContent = request.getInputStream();

		try {

			System.err.println("Getting object");
			Object obj = new JSONParser().parse(new InputStreamReader(fileContent));

			JSONObject jo = (JSONObject) obj;
			System.err.println("Getting long");
			double lon = (double) jo.get("long");
			System.err.println("Getting lat");
			double lat = (double) jo.get("lat");
			
			System.err.printf("Long %f, Lat %f\n", lon, lat);

			boolean hasHum = false;
			boolean hasTemp = false;
			boolean hasSoil = false;
			boolean hasLight = false;
			
			System.err.println("Getting sensors");
			JSONArray sensors = (JSONArray) jo.get("sensors");

			System.err.println("Got sensors");
			
			for (Object ob : sensors) {
				String s = (String) ob;
				switch (s) {
				case "H":
					hasHum = true;
					System.err.println("has hum");
					break;
				case "T":
					hasTemp = true;
					System.err.println("has temp");
					break;
				case "S":
					hasSoil = true;
					System.err.println("has soil");
					break;
				case "L":
					hasLight = true;
					System.err.println("has light");
					break;
				default:
					System.err.println("wrong sensor '" + s + "'");
					throw new IOException("Invalid Sensor type");
				}
			}
			
			System.err.println("Done sensors");

			Device device = createDevice(lon, lat, hasHum, hasTemp, hasSoil, hasLight);

			System.err.println("Created devices");
			
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
			

		} catch (ParseException | ClassNotFoundException | SQLException e) {
			e.printStackTrace(System.err);
			throw new IOException("Invalid JSON format");
		}

	}

}
