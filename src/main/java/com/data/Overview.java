package com.data;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.json.DataBaseInfo;
import com.recommendations.GiveTips;

public class Overview extends HttpServlet {
	
	private static final long serialVersionUID = -8112750831178650005L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String[] names = new String[3];
		String[] statuss = new String[3];
		Connection conn = null;
		try {
			conn = DataBaseInfo.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT plantID, deviceID, name FROM UserPlants");
			stmt.execute();

			ResultSet rs = stmt.getResultSet();
			for (int i = 0; i < 3; i++) {
				if (rs.next()) {
					names[i] = rs.getString("name");
					int deviceID = Math.toIntExact(rs.getLong("deviceID"));
					statuss[i] = getPlantTips(conn, deviceID, names[i]);
					names[i] = "'" + names[i].toLowerCase() + "'";
				}
			}
			conn.close();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}

			// send data for graph to jsp
			request.getSession().setAttribute("names", Arrays.toString(names));
			request.getSession().setAttribute("statuss", Arrays.toString(statuss));

		request.getRequestDispatcher("/overview.jsp").forward(request, response);

	}

	private int getHumReading(Connection conn, int deviceID) throws SQLException {

		PreparedStatement stmt = conn.prepareStatement("SELECT value FROM Humidity INNER JOIN Sensors "
				+ "ON Humidity.sensorID = Sensors.sensorID " + "WHERE deviceID = ? AND "
				+ "dateTime = (SELECT MAX(dateTime) FROM Humidity INNER JOIN Sensors ON Humidity.sensorID = Sensors.sensorID WHERE deviceID = ?)");
		stmt.setInt(1, deviceID);
		stmt.setInt(2, deviceID);
		stmt.execute();

		ResultSet rs = stmt.getResultSet();
		if (rs.next()) {
			return rs.getInt("value");
		}

		return -1;

	}
	
	private String getPlantTips(Connection conn, int deviceID, String name) {
		List<String> tips;

		int mTemp = -1, mHum = -1, mSoil = -1, mLight = -1;

		try {
			mTemp = getTempReading(conn, deviceID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("Temp reading: " + mTemp);

		try {
			mHum = getHumReading(conn, deviceID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("Hum reading: " + mHum);

		try {
			mSoil = getSoilReading(conn, deviceID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("Soil reading: " + mSoil);

		try {
			mLight = getLightReading(conn, deviceID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.err.println("Light reading: " + mLight);

		System.err.println("Getting tips");
		tips = GiveTips.GET_TIPS(name, mTemp, mHum, mSoil, mLight);
		return Arrays.toString(listOfStrings(tips));
	}

	private int getSoilReading(Connection conn, int deviceID) throws SQLException {

		PreparedStatement stmt = conn.prepareStatement("SELECT value FROM SoilMoisture INNER JOIN Sensors "
				+ "ON SoilMoisture.sensorID = Sensors.sensorID " + "WHERE deviceID = ? AND "
				+ "dateTime = (SELECT MAX(dateTime) FROM SoilMoisture INNER JOIN Sensors ON SoilMoisture.sensorID = Sensors.sensorID WHERE deviceID = ?)");
		stmt.setInt(1, deviceID);
		stmt.setInt(2, deviceID);
		stmt.execute();

		ResultSet rs = stmt.getResultSet();
		if (rs.next()) {
			return rs.getInt("value");
		}

		return -1;

	}

	private int getTempReading(Connection conn, int deviceID) throws SQLException {

		PreparedStatement stmt = conn.prepareStatement("SELECT value FROM Temperature INNER JOIN Sensors "
				+ "ON Temperature.sensorID = Sensors.sensorID " + "WHERE deviceID = ? AND "
				+ "dateTime = (SELECT MAX(dateTime) FROM Temperature INNER JOIN Sensors ON Temperature.sensorID = Sensors.sensorID WHERE deviceID = ?)");
		stmt.setInt(1, deviceID);
		stmt.setInt(2, deviceID);
		stmt.execute();

		ResultSet rs = stmt.getResultSet();
		if (rs.next()) {
			return rs.getInt("value");
		}

		return -1;

	}

	private int getLightReading(Connection conn, int deviceID) throws SQLException {

		PreparedStatement stmt = conn.prepareStatement("SELECT value FROM Light INNER JOIN Sensors "
				+ "ON Light.sensorID = Sensors.sensorID " + "WHERE deviceID = ? AND "
				+ "dateTime = (SELECT MAX(dateTime) FROM Light INNER JOIN Sensors ON Light.sensorID = Sensors.sensorID WHERE deviceID = ?)");
		stmt.setInt(1, deviceID);
		stmt.setInt(2, deviceID);
		stmt.execute();

		ResultSet rs = stmt.getResultSet();
		if (rs.next()) {
			return rs.getInt("value");
		}

		return -1;

	}

	private String[] listOfStrings(List<String> tips) {
		String[] s = new String[tips.size()];
		int i = 0;
		for (String tip : tips) {
			s[i++] = tip;
		}
		return s;
	}
}
