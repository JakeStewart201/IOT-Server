package com.test;

import java.time.LocalDate;


public class GraphData {

    private double measurement;
    private LocalDate date;

    public GraphData(double measurement,  LocalDate date) {
        this.measurement = measurement;
        this.date = date;
    }

    public double getMeasurement() {
        return measurement;
    }

    public String getDateAsString(){
        return date.toString();
    }

    public LocalDate getDate() {
        return date;
    }
}
