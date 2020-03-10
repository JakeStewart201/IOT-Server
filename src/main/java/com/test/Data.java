package com.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class Data extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        System.out.println("Parameter [type] value = " + type);

        String fromDate = request.getParameter("fromDate");
        System.out.println("Parameter [type] value = " + fromDate);

        if (type !=  null){

            if (!fromDate.equals("")){
                dateConstraint = LocalDate.parse(fromDate);
            }

            System.out.println("Date constraint: " + dateConstraint);

            //getDBdata(type, dateConstraint);

            //send data for graph to jsp
            request.getSession().setAttribute("dates", Arrays.toString(listofDates()));
            request.getSession().setAttribute("measurements", Arrays.toString(listofMeasuremnts()));
            request.getSession().setAttribute("label", type);

        }
        request.getRequestDispatcher("/data_search.jsp").forward(request, response);
    }


    private List<GraphData> data = new ArrayList<GraphData>();
    private LocalDate dateConstraint;

    public Data() {
        initTempData();
        dateConstraint = LocalDate.MIN;
    }

    private void initTempData(){

        int year = 2020;
        data.add(new GraphData(23, LocalDate.of(year, 01, 10)));
        data.add(new GraphData(-5.6, LocalDate.of(year, 01, 20)));
        data.add(new GraphData(17.5,LocalDate.of(year, 02, 15)));
        data.add(new GraphData(10, LocalDate.of(year, 03, 7)));
    }

    private ArrayList<GraphData> getDBdata(String measurementType, LocalDate timeConstraint){
        ArrayList<GraphData> data = new ArrayList<GraphData>();

        String sqlStatement = "SELECT value FROM " + measurementType;
        return data;
    }

    private String[] listofDates(){
        String[] arr = new String[data.size()];
        int i = 0;
        for (GraphData d: data) {
            arr[i] = "'" + d.getDateAsString() + "'";
            i+=1;
        }
        return arr;
    }

    private String[] listofMeasuremnts(){
        String[] arr = new String[data.size()];
        int i = 0;
        for (GraphData d: data) {
            String m = Double.toString(d.getMeasurement());
            arr[i] = "'" + m + "'";
            i+=1;
        }
        return arr;
    }

}
