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

public class Recommendations extends HttpServlet {

	private static final long serialVersionUID = -3046677642715282124L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int deviceID = -1;
		try {
			deviceID = Integer.parseInt(request.getParameter("deviceID"));
		} catch (Exception ex) {
			deviceID = -1;
		}

		System.out.println("Parameter [deviceId] value = " + deviceID);

		if (deviceID > 0) {

			List<String> tips;
			// TODO get values from database

			String name = "";
			int mTemp = -1, mHum = -1, mSoil = -1, mLight = -1;
			
			Connection conn;
			try {
				conn = DataBaseInfo.getConnection();
				
				try {
					name = getName(conn, deviceID);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.err.println("Plant name: " + name);

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
				
				conn.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			System.err.println("Getting tips");
			tips = GiveTips.GET_TIPS(name, mTemp, mHum, mSoil, mLight);

			System.err.println("Got tips");
			if (tips.size() == 0) {
				tips.add("Everything looks great");
			}

			// send data for graph to jsp
			request.getSession().setAttribute("tips", Arrays.toString(listOfStrings(tips)));

		}

		request.getRequestDispatcher("/recommendations.jsp").forward(request, response);

	}

	private int getHumReading(Connection conn, int deviceID) throws SQLException {
		
		PreparedStatement stmt = conn.prepareStatement("SELECT value FROM Humidity INNER JOIN Sensors "
				+ "ON Humidity.sensorID = Sensors.sensorID "
				+ "WHERE deviceID = ? AND "
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

	private String getName(Connection conn, int deviceID) throws SQLException {
		
		PreparedStatement stmt = conn.prepareStatement("SELECT name FROM UserPlants WHERE deviceID = ?");
		stmt.setInt(1, deviceID);
		stmt.execute();
		
		ResultSet rs = stmt.getResultSet();
		if (rs.next()) {
			return rs.getString("name");
		}
		
		return "";
		
	}

	private int getSoilReading(Connection conn, int deviceID) throws SQLException {
		
		PreparedStatement stmt = conn.prepareStatement("SELECT value FROM SoilMoisture INNER JOIN Sensors "
				+ "ON SoilMoisture.sensorID = Sensors.sensorID "
				+ "WHERE deviceID = ? AND "
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
				+ "ON Temperature.sensorID = Sensors.sensorID "
				+ "WHERE deviceID = ? AND "
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
				+ "ON Light.sensorID = Sensors.sensorID "
				+ "WHERE deviceID = ? AND "
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
			s[i++] = "'" + tip + "'";
		}
		return s;
	}
}
