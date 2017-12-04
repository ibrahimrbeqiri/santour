package models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
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
    private long km;
    private String startLocation;
    private String endLocation;
    private List<LatLng> gpsTrack;
    private List<POI> poiTrack;
    private List<POD> podTrack;


    public Track() {
        poiTrack=new ArrayList<>();
        podTrack=new ArrayList<>();

    }

    public Track(String nameTrack, String descriptionTrack, String pictureTrack, String timer, long km, String startLocation, String endLocation, List<LatLng> gpsTrack, List<POI> poiTrack, List<POD> podTrack) {
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

    public long getKm() {
        return km;
    }

    public void setKm(long km) {
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

    public List<LatLng> getGpsTrack() {
        return gpsTrack;
    }

    public void setGpsTrack(List<LatLng> gpsTrack) {
        this.gpsTrack = gpsTrack;
    }

    public List<POI> getPoiTrack() {
        return poiTrack;
    }

    public void setPoiTrack(POI poiTrack) {
        this.poiTrack.add(poiTrack);
    }

    public List<POD> getPodTrack() {
        return podTrack;
    }

    public void setPodTrack(POD podTrack) {
        this.podTrack.add(podTrack);
    }

}
