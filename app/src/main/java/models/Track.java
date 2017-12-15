package models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vincent_2 on 25.11.2017.
 */

public class Track implements Serializable{

    private String idTrack;
    private String nameTrack;
    private String descriptionTrack;
    private String pictureTrack;
    private String timer;
    private String km;
    private String startLocation;
    private String endLocation;
    private List<GPSData> gpsTrack;
    private List<POI> poiTrack;
    private List<POD> podTrack;
    private String trackDate;


    public Track() {
        poiTrack=new ArrayList<>();
        podTrack=new ArrayList<>();
        gpsTrack=new ArrayList<>();
    }

    public Track(String nameTrack, String descriptionTrack, String pictureTrack, String timer, String km, String startLocation, String endLocation, List<GPSData> gpsTrack, List<POI> poiTrack, List<POD> podTrack, String trackDate) {
        this.nameTrack = nameTrack;
        this.descriptionTrack = descriptionTrack;
        this.pictureTrack = pictureTrack;
        this.timer = timer;
        this.km = km;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.gpsTrack = gpsTrack;
        this.poiTrack = poiTrack;
        this.podTrack = podTrack;
        this.trackDate = trackDate;
    }

    public String getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(String idTrack) {
        this.idTrack = idTrack;
    }

    public String getNameTrack() {
        return nameTrack;
    }

    public void setNameTrack(String nameTrack) {
        this.nameTrack = nameTrack;
    }

    public String getDescriptionTrack() {
        return descriptionTrack;
    }

    public void setDescriptionTrack(String descriptionTrack) {
        this.descriptionTrack = descriptionTrack;
    }

    public String getPictureTrack() {
        return pictureTrack;
    }

    public void setPictureTrack(String pictureTrack) {
        this.pictureTrack = pictureTrack;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public List<GPSData> getGpsTrack() {
        return gpsTrack;
    }

    public void setGpsTrack(List<GPSData> gpsTrack) {
        this.gpsTrack = gpsTrack;
    }

    public List<POI> getPoiTrack() {
        return poiTrack;
    }

    public void setPoiTrack(List<POI> poiTrack) {
        this.poiTrack = poiTrack;
    }

    public List<POD> getPodTrack() {
        return podTrack;
    }

    public void setPodTrack(List<POD> podTrack) {
        this.podTrack = podTrack;
    }

    public String getTrackDate() { return trackDate;}

    public void setTrackDate(String trackDate) {this.trackDate = trackDate;}

}
