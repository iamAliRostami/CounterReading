package com.leon.counter_reading.tables;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "SavedLocation", indices = @Index(value = {"id"}, unique = true))
public class SavedLocation {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public double accuracy;
    public double longitude;
    public double latitude;

    public SavedLocation(double accuracy, double longitude, double latitude) {
        this.accuracy = accuracy;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}