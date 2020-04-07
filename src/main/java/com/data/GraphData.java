package com.data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class GraphData {

    private double measurement;
    private LocalDateTime date;

	public GraphData(double measurement, LocalDateTime date) {
        this.measurement = measurement;
        this.date = date;
    }

    public double getMeasurement() {
        return measurement;
    }

    public String getDateAsString(){
        return Long.toString(date.toEpochSecond(ZoneOffset.UTC) * 1000);
    }

    public LocalDateTime getDate() {
        return date;
    }
}
