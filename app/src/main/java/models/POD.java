package models;

import java.io.Serializable;

/**
 * Created by Vincent_2 on 25.11.2017.
 */

public class POD implements Serializable{

    private String idPOD;
    private String namePOD;
    private String descriptionPOD;
    private String picturePOD;
    private GPSData gpsLocationPOD;

    private String detailVerticality;
    private String detailRocks;
    private String detailSlope;


    public POD(){

    }

    public POD(String namePOD, String descriptionPOD, String picturePOD, GPSData gpsLocationPOD, String detailVerticality, String detailRocks, String detailSlope) {
        this.namePOD = namePOD;
        this.descriptionPOD = descriptionPOD;
        this.picturePOD = picturePOD;
        this.gpsLocationPOD = gpsLocationPOD;
        this.detailVerticality = detailVerticality;
        this.detailRocks = detailRocks;
        this.detailSlope = detailSlope;
    }

    public POD(String namePOD, String descriptionPOD, String picturePOD, GPSData gpsLocationPOD) {
        this.namePOD = namePOD;
        this.descriptionPOD = descriptionPOD;
        this.picturePOD = picturePOD;
        this.gpsLocationPOD = gpsLocationPOD;
        this.detailVerticality = detailVerticality;
        this.detailRocks = detailRocks;
        this.detailSlope = detailSlope;
    }

    public String getIdPOD() {
        return idPOD;
    }

    public void setIdPOD(String idPOD) {
        this.idPOD = idPOD;
    }

    public String getNamePOD() {
        return namePOD;
    }

    public void setNamePOD(String namePOD) {
        this.namePOD = namePOD;
    }

    public String getDescriptionPOD() {
        return descriptionPOD;
    }

    public void setDescriptionPOD(String descriptionPOD) {
        this.descriptionPOD = descriptionPOD;
    }

    public String getPicturePOD() {
        return picturePOD;
    }

    public void setPicturePOD(String picturePOD) {
        this.picturePOD = picturePOD;
    }

    public GPSData getGpsLocationPOD() {
        return gpsLocationPOD;
    }

    public void setGpsLocationPOD(GPSData gpsLocationPOD) {
        this.gpsLocationPOD = gpsLocationPOD;
    }

    public String getDetailVerticality() {
        return detailVerticality;
    }

    public void setDetailVerticality(String detailVerticality) {
        this.detailVerticality = detailVerticality;
    }

    public String getDetailRocks() {
        return detailRocks;
    }

    public void setDetailRocks(String detailRocks) {
        this.detailRocks = detailRocks;
    }

    public String getDetailSlope() {
        return detailSlope;
    }

    public void setDetailSlope(String detailSlope) {
        this.detailSlope = detailSlope;
    }

}
