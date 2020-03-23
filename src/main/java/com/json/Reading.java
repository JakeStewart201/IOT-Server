package com.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

public class Reading extends HttpServlet {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private void addReading(int sensorID, int value, Date time) throws ClassNotFoundException, SQLException {
		Connection conn = DataBaseInfo.getConnection();

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
		PreparedStatement stmt = conn
				.prepareStatement("insert into Humidity(dateTime, sensorID, value) values(?, ?, ?)");
		stmt.setTimestamp(1, new java.sql.Timestamp(time.getTime()));
		stmt.setInt(2, sensorID);
		stmt.setInt(3, value);
		stmt.execute();
	}

	private void addSoilMoistureReading(Connection conn, Date time, int sensorID, int value) throws SQLException {
		PreparedStatement stmt = conn
				.prepareStatement("insert into SoilMoisture(dateTime, sensorID, value) values(?, ?, ?)");
		stmt.setTimestamp(1, new java.sql.Timestamp(time.getTime()));
		stmt.setInt(2, sensorID);
		stmt.setInt(3, value);
		stmt.execute();
	}

	private void addTemperatureReading(Connection conn, Date time, int sensorID, int value) throws SQLException {
		PreparedStatement stmt = conn
				.prepareStatement("insert into Temperature(dateTime, sensorID, value) values(?, ?, ?)");
		stmt.setTimestamp(1, new java.sql.Timestamp(time.getTime()));
		stmt.setInt(2, sensorID);
		stmt.setInt(3, value);
		stmt.execute();
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

			int deviceId = (int) jo.get("device-id");
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			Date time = formatter.parse((String) jo.get("timestamp"));
			JSONObject sensors = (JSONObject) jo.get("sensor-values");
			sensors.forEach((Object k, Object v) -> {
				try {
					addReading((int) k, (int) v, time);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			});

		} catch (ParseException | java.text.ParseException e) {
			throw new IOException("Invalid JSON format");
		}

	}
}
