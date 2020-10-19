package com.leon.reading_counter.utils.gis;

public class FeatureGIS {
    public String type;
    public GeometryGIS geometry;
    public Properties properties;
    public int id;

    public FeatureGIS() {
        properties = new Properties();
    }
}
