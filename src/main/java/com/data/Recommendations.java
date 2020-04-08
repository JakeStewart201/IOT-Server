package com.data;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

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
			//tips = GiveTips.GET_TIPS(name, mTemp, mHum, mSoil, mLight);
			System.err.println("Getting tips");
			tips = GiveTips.GET_TIPS("Tomato", 31, 79, 81, 699);

			System.err.println("Got tips");
			if (tips.size() == 0) {
				tips.add("Everything looks great");
			}
			
			// send data for graph to jsp
			request.getSession().setAttribute("tips", Arrays.toString(listOfStrings(tips)));
			
		}

		request.getRequestDispatcher("/recommendations.jsp").forward(request, response);

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
