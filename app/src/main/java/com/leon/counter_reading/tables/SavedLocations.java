package com.leon.counter_reading.tables;

public class SavedLocations {
    public int id;
    public double accuracy;
    public double longitude;
    public double latitude;

    public SavedLocations(double accuracy, double longitude, double latitude) {
        this.accuracy = accuracy;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
