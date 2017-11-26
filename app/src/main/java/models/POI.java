package models;

/**
 * Created by Vincent_2 on 25.11.2017.
 */

public class POI {

    private String idPOI;
    private String namePOI;
    private String descriptionPOI;
    private String picturePOI;
    private GPSData gpsLocationPOI;

    public POI(){

    }

    public POI(String namePOI, String descriptionPOI, String picturePOI, GPSData gpsLocationPOI) {
        this.namePOI = namePOI;
        this.descriptionPOI = descriptionPOI;
        this.picturePOI = picturePOI;
        this.gpsLocationPOI = gpsLocationPOI;
    }


    public String getIdPOI() {
        return idPOI;
    }

    public void setIdPOI(String idPOI) {
        this.idPOI = idPOI;
    }

    public String getNamePOI() {
        return namePOI;
    }

    public void setNamePOI(String namePOI) {
        this.namePOI = namePOI;
    }

    public String getDescriptionPOI() {
        return descriptionPOI;
    }

    public void setDescriptionPOI(String descriptionPOI) {
        this.descriptionPOI = descriptionPOI;
    }

    public String getPicturePOI() {
        return picturePOI;
    }

    public void setPicturePOI(String picturePOI) {
        this.picturePOI = picturePOI;
    }

    public GPSData getGpsLocationPOI() {
        return gpsLocationPOI;
    }

    public void setGpsLocationPOI(GPSData gpsLocationPOI) {
        this.gpsLocationPOI = gpsLocationPOI;
    }
}
