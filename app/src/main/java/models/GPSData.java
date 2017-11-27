package models;

/**
 * Created by Vincent_2 on 26.11.2017.
 */

public class GPSData {

    private String idGPSData;
    private String xGPS;
    private String yGPS;


    public GPSData(){

    }

    public GPSData(String xGPS, String yGPS) {
        this.xGPS = xGPS;
        this.yGPS = yGPS;
    }

    public String getIdGPSData() {
        return idGPSData;
    }

    public void setIdGPSData(String idGPSData) {
        this.idGPSData = idGPSData;
    }

    public String getxGPS() {
        return xGPS;
    }

    public void setxGPS(String xGPS) {
        this.xGPS = xGPS;
    }

    public String getyGPS() {
        return yGPS;
    }

    public void setyGPS(String yGPS) {
        this.yGPS = yGPS;
    }
}
