package com.test;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Recommendations extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        request.getSession().setAttribute("recommendations", recommendations);

        request.getRequestDispatcher("/recommend.jsp").forward(request, response);

        out.close();

    }

    private List<String> recommendations = new ArrayList<String>();

    public Recommendations() {
        initRecommendations();
    }

    private void initRecommendations(){
        recommendations.add("Water your plants more!");
        recommendations.add("Your poppy isn't getting enough light.");
        recommendations.add("Your house is too humid.");

    }
}
