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

		String type = request.getParameter("type");
		System.out.println("Parameter [type] value = " + type);

		String fromDate = request.getParameter("fromDate");
		System.out.println("Parameter [fromDate] value = " + fromDate);

		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Parameter [id] value = " + id);

		if (type != null) {
			LocalDate dateConstraint;

			dateConstraint = LocalDate.MIN;

			if (!fromDate.equals("")) {
				dateConstraint = LocalDate.parse(fromDate);
			}

			List<GraphData> data = new ArrayList<>();
			try {
				data = getDBdata(type, Timestamp.valueOf(dateConstraint.atStartOfDay()), id);
			} catch (ClassNotFoundException | SQLException e) {
				initTempData(data);
				e.printStackTrace();
			}

			System.out.println("Date constraint: " + dateConstraint);

			// getDBdata(type, dateConstraint);

			// send data for graph to jsp
			request.getSession().setAttribute("data", Arrays.toString(listofData(data)));
			request.getSession().setAttribute("label", type);

		}
		request.getRequestDispatcher("/data_search.jsp").forward(request, response);
	}

	private void initTempData(List<GraphData> data) {

		int year = 2020;
		data.add(new GraphData(23, LocalDateTime.of(year, 01, 10, 6, 0)));
		data.add(new GraphData(-5.6, LocalDateTime.of(year, 01, 20, 12, 0)));
		data.add(new GraphData(17.5, LocalDateTime.of(year, 02, 15, 18, 0)));
		data.add(new GraphData(10, LocalDateTime.of(year, 03, 7, 23, 0)));
	}

	private ArrayList<GraphData> getDBdata(String measurementType, Timestamp timeConstraint, int id)
			throws ClassNotFoundException, SQLException {
		ArrayList<GraphData> data = new ArrayList<GraphData>();

		Connection conn = DataBaseInfo.getConnection();

		switch (measurementType) {
		case "Temperature":
			getTemperatureData(timeConstraint, data, conn, id);
			break;
		case "Light":
			getLightData(timeConstraint, data, conn, id);
			break;
		case "Humidity":
			getHumidityData(timeConstraint, data, conn, id);
			break;
		case "Soil Moisture":
			getSoilMoistureData(timeConstraint, data, conn, id);
			break;
		default:
			initTempData(data);
		}
		conn.close();

		return data;
	}

	private void getLightData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn, int id)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT value, dateTime FROM Light WHERE dateTime > ? AND sensorID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
	}

	private void getHumidityData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn, int id)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT value, dateTime FROM Humidity WHERE dateTime > ? AND sensorID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
	}

	private void getSoilMoistureData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn, int id)
			throws SQLException {
		PreparedStatement stmt = conn
				.prepareStatement("SELECT value, dateTime FROM SoilMoisture WHERE dateTime > ? AND sensorID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);
		
		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
	}

	private void getTemperatureData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn, int id)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT value, dateTime FROM Temperature WHERE dateTime > ? AND sensorID = ? ORDER BY dateTime ASC");
		stmt.setTimestamp(1, timeConstraint);
		stmt.setInt(2, id);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
	}

	private String[] listofData(List<GraphData> data) {
		String[] arr = new String[data.size()];
		int i = 0;
		System.err.println("Running");
		for (GraphData d : data) {
			arr[i] = "{\nx: " + d.getDateAsString() + ",\ny: " +  Double.toString(d.getMeasurement()) + "\n}";
			i += 1;
		}
		return arr;
	}

}
