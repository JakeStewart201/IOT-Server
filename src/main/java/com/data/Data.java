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
		System.out.println("Parameter [type] value = " + fromDate);

		if (type != null) {

			List<GraphData> data = new ArrayList<GraphData>();
			LocalDate dateConstraint;

			initTempData(data);
			dateConstraint = LocalDate.MIN;

			if (!fromDate.equals("")) {
				dateConstraint = LocalDate.parse(fromDate);
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

	private ArrayList<GraphData> getDBdata(String measurementType, Timestamp timeConstraint)
			throws ClassNotFoundException, SQLException {
		ArrayList<GraphData> data = new ArrayList<GraphData>();

		Connection conn = DataBaseInfo.getConnection();

		switch (measurementType) {
		case "Temperature":
			getTemperatureData(timeConstraint, data, conn);
			break;
		case "Light":
			getLightData(timeConstraint, data, conn);
			break;
		case "Humidity":
			getHumidityData(timeConstraint, data, conn);
			break;
		case "Soil Moisture":
			getSoilMoistureData(timeConstraint, data, conn);
			break;
		default:
			initTempData(data);
		}
		conn.close();

		return data;
	}

	private void getLightData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT dataTime, sensorID FROM Light WHERE dateTime > ?");
		stmt.setTimestamp(1, timeConstraint);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
	}

	private void getHumidityData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT dataTime, sensorID FROM Humidity WHERE dateTime > ?");
		stmt.setTimestamp(1, timeConstraint);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
	}

	private void getSoilMoistureData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn)
			throws SQLException {
		PreparedStatement stmt = conn
				.prepareStatement("SELECT dataTime, sensorID FROM SoilMoisture WHERE dateTime > ?");
		stmt.setTimestamp(1, timeConstraint);

		if (stmt.execute()) {

			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				data.add(new GraphData(rs.getInt("value"), rs.getTimestamp("dateTime").toLocalDateTime()));
			}
		}
	}

	private void getTemperatureData(Timestamp timeConstraint, ArrayList<GraphData> data, Connection conn)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT dataTime, sensorID FROM Temperature WHERE dateTime > ?");
		stmt.setTimestamp(1, timeConstraint);

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
