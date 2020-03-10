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

        String title = "Recommendations";

        try {
            out.println("<!DOCTYPE html>");  // HTML 5
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>" + title + "</title></head>");
            out.println("<body>");
            out.println("<h1>" + title + "</h1>");
            // Set a hyperlink image to refresh this page
            int i = 1;
            for(String r : recommendations)
            {
                out.println("Tip " + i + ": "+ r + "<p/>");
                i+=1;
            }
            out.println("</body></html>");
        } finally {
            out.close();  // Always close the output writer
        }


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
