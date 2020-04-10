package com.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Reading extends HttpServlet {

	private static final long serialVersionUID = -2348683777669285986L;
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private void addReading(int sensorID, int value, Date time, int deviceID) throws ClassNotFoundException, SQLException {
		Connection conn = DataBaseInfo.getConnection();

		String type = getType(conn, sensorID, deviceID);

		switch (type) {
		case "L":
			addLightReading(conn, time, sensorID, value);
			break;
		case "H":
			addHumidityReading(conn, time, sensorID, value);
			break;
		case "S":
			addSoilMoistureReading(conn, time, sensorID, value);
			break;
		case "T":
			addTemperatureReading(conn, time, sensorID, value);
			break;
		}

		conn.close();
	}

	private String getType(Connection conn, int sensorID, int deviceID) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("select type from Sensors where sensorID = ? AND deviceID = ?");
		System.err.println(sensorID);
		System.err.println(deviceID);
		stmt.setInt(1, sensorID);
		stmt.setInt(2, deviceID);
		stmt.execute();

		ResultSet results = stmt.getResultSet();
		
		for (int i = 1; i <= results.getMetaData().getColumnCount(); i++) {
			System.err.println(results.getMetaData().getColumnName(i));
			System.err.println(results.getMetaData().getColumnType(i));
		}
		
		results.next();
		
		return results.getString("type");
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
		InputStream fileContent = request.getInputStream();

		try {

			Object obj = new JSONParser().parse(new InputStreamReader(fileContent));

			JSONObject jo = (JSONObject) obj;

			int deviceID = Math.toIntExact((long) jo.get("device-id"));
			DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
			Date time = formatter.parse((String) jo.get("timestamp"));
			JSONObject sensors = (JSONObject) jo.get("sensor-values");
			sensors.forEach((Object k, Object v) -> {
				try {
					addReading(Integer.parseInt((String)k), Math.toIntExact((long) v), time, deviceID);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			});

		} catch (ParseException | java.text.ParseException e) {
			throw new IOException("Invalid JSON format");
		}

	}
}
