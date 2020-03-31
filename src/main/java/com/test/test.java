package com.test;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class test extends HttpServlet {

	private static final long serialVersionUID = 368261114784206578L;

@Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      // Set the response message's MIME type.
      response.setContentType("text/html;charset=UTF-8");
      // Allocate a output writer to write the response message into the network socket.
      PrintWriter out = response.getWriter();
 
      // Use a ResourceBundle for localized string in "LocalStrings_xx.properties" for i18n.
      // The request.getLocale() sets the locale based on the "Accept-Language" request header.
      ResourceBundle rb = ResourceBundle.getBundle("LocalStrings", request.getLocale());
      
      // Write the response message, in an HTML document.
      try {
         out.println("<!DOCTYPE html>");  // HTML 5
         out.println("<html><head>");
         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
         String title = rb.getString("helloworld.title");
         out.println("<title>" + title + "</title></head>");
         out.println("<body>");
         out.println("<h1>" + title + "</h1>");  // Prints "Hello, world!"
         // Set a hyperlink image to refresh this page
         out.println("<a href='" + request.getRequestURI() + "'><img src='images/back.png'></a>");
         out.println("</body></html>");
      } finally {
         out.close();  // Always close the output writer
      }
   }
}