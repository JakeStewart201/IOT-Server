package com.test;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

import com.json.DataBaseInfo;

public class AddPlant extends HttpServlet {

	private static final long serialVersionUID = 6859778672964842264L;

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
		
		String name = request.getParameter("name");

		System.out.println("Parameter [name] value = " + name);

		if (deviceID > 0) {
			
			Connection conn;
			try {
				conn = DataBaseInfo.getConnection();

				PreparedStatement stmt = conn
						.prepareStatement("insert into UserPlants(deviceID, name) values(?, ?)");
				stmt.setInt(1, deviceID);
				stmt.setString(2, name);
				stmt.execute();

				conn.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} finally {
				
			}
			
		}

		request.getRequestDispatcher("/add_plant.jsp").forward(request, response);

	}
}
