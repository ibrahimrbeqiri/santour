package models;

import java.io.Serializable;

/**
 * Created by Vincent_2 on 26.11.2017.
 */

public class GPSData implements Serializable{

    private String idGPSData;
    private double xGPS;
    private double yGPS;


    public GPSData(){

    }

    public GPSData(double xGPS, double yGPS) {
        this.xGPS = xGPS;
        this.yGPS = yGPS;
    }

    public String getIdGPSData() {
        return idGPSData;
    }

    public void setIdGPSData(String idGPSData) {
        this.idGPSData = idGPSData;
    }

    public double getxGPS() {
        return xGPS;
    }

    public void setxGPS(double xGPS) {
        this.xGPS = xGPS;
    }

    public double getyGPS() {
        return yGPS;
    }

    public void setyGPS(double yGPS) {
        this.yGPS = yGPS;
    }
}
