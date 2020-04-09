package com.data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.json.DataBaseInfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Data extends HttpServlet {

	private static final long serialVersionUID = -7300071416365415232L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter();

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception ex) {
			id = 0;
		}
		System.out.println("Parameter [id] value = " + id);

		if (id != 0) {
			LocalDate dateConstraint;

			dateConstraint = LocalDate.MIN;

			String data = "";
			try {
				data = getDBdata(Timestamp.valueOf(dateConstraint.atStartOfDay()), id);
			} catch (ClassNotFoundException | SQLException e) {
				data = initTempData();
				e.printStackTrace();
			}

			// getDBdata(type, dateConstraint);

			// send data for graph to jsp
			request.getSession().setAttribute("data", data);
			System.out.println(data);

		}
		request.getRequestDispatcher("/data_search.jsp").forward(request, response);
	}

	private String initTempData() {
		ArrayList<GraphData> data = new ArrayList<>();
		int year = 2020;
		data.add(new GraphData(23, LocalDateTime.of(year, 01, 10, 6, 0)));
		data.add(new GraphData(-5.6, LocalDateTime.of(year, 01, 20, 12, 0)));
		data.add(new GraphData(17.5, LocalDateTime.of(year, 02, 15, 18, 0)));
		data.add(new GraphData(10, LocalDateTime.of(year, 03, 7, 23, 0)));
		return Arrays.toString(listofData(data));
	}

	private String getDBdata(Timestamp timeConstraint, int id)
			throws ClassNotFoundException, SQLException {
		String data = "";

		Connection conn = DataBaseInfo.getConnection();

		data += "{'Temperature': ";
		data += Arrays.toString(listofData(getTemperatureData(timeConstraint, conn, id)));
		data += ", ";

		data += "'Light': ";
		data += Arrays.toString(listofData(getLightData(timeConstraint, conn, id)));
		data += ", ";

		data += "'Humidity': ";
		data += Arrays.toString(listofData(getHumidityData(timeConstraint, conn, id)));
		data += ", ";

		data += "'Soil Moisture': ";
		data += Arrays.toString(listofData(getSoilMoistureData(timeConstraint, conn, id)));
		data += "}";
		conn.close();

		return data;
	}

	private ArrayList<GraphData> getLightData(Timestamp timeConstraint, Connection conn, int id)
			throws SQLException {
		ArrayList<GraphData> data = new ArrayList<>();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT value, dateTime FROM Light INNER JOIN Sensors " + "ON Light.sensorID = Sensors.sensorID "
						+ "WHERE dateTime > ? AND deviceID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
		return data;
	}

	private ArrayList<GraphData> getHumidityData(Timestamp timeConstraint, Connection conn, int id)
			throws SQLException {
		ArrayList<GraphData> data = new ArrayList<>();
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT value, dateTime FROM Humidity INNER JOIN Sensors " + "ON Humidity.sensorID = Sensors.sensorID "
						+ "WHERE dateTime > ? AND deviceID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
		return data;
	}

	private ArrayList<GraphData> getSoilMoistureData(Timestamp timeConstraint, Connection conn, int id)
			throws SQLException {
		ArrayList<GraphData> data = new ArrayList<>();
		PreparedStatement stmt = conn.prepareStatement("SELECT value, dateTime FROM SoilMoisture INNER JOIN Sensors "
				+ "ON SoilMoisture.sensorID = Sensors.sensorID "
				+ "WHERE dateTime > ? AND deviceID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
		return data;
	}

	private ArrayList<GraphData> getTemperatureData(Timestamp timeConstraint, Connection conn, int id)
			throws SQLException {
		ArrayList<GraphData> data = new ArrayList<>();
		PreparedStatement stmt = conn.prepareStatement("SELECT value, dateTime FROM Temperature INNER JOIN Sensors "
				+ "ON Temperature.sensorID = Sensors.sensorID "
				+ "WHERE dateTime > ? AND deviceID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
		return data;
	}

	private String[] listofData(List<GraphData> data) {
		String[] arr = new String[data.size()];
		int i = 0;
		System.err.println("Running");
		for (GraphData d : data) {
			arr[i] = "{\nx: " + d.getDateAsString() + ",\ny: " + Double.toString(d.getMeasurement()) + "\n}";
			i += 1;
		}
		return arr;
	}

}
