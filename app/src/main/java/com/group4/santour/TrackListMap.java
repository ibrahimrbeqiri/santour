package com.group4.santour;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import models.GPSData;
import models.POD;
import models.POI;
import models.Track;

public class TrackListMap extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private List<GPSData> gpsDatas = new ArrayList<>();
    private Polyline pathDraw;
    private PolylineOptions options;
    private List<POD> podList = new ArrayList<>();
    private List<POI> poiList = new ArrayList<>();
    private Track track;
    private TextView trackName;
    private GPSData startLocation;
    private GPSData endLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // get the intent from the activity that sent you here
        Intent intent = getIntent();

        // get the track from the intent
        track = (Track) intent.getSerializableExtra("track");

        // set the polyline options
        options = new PolylineOptions().width(10).color(Color.RED).geodesic(true);

        // set the textview equals to the track name
        trackName = findViewById(R.id.trackname);
        trackName.setText(track.getNameTrack());

        // get the lists of gps data, pois and pods from the tack
        gpsDatas = track.getGpsTrack();
        poiList = track.getPoiTrack();
        podList = track.getPodTrack();

        // for every gps data object in the list get the coordinates and put them in the polyline options list
        for (GPSData gpsData : gpsDatas) {
            // parse from string to double because Google Maps API accepts only LatLng for coordinates
            double latitude = Double.parseDouble(gpsData.getxGPS());
            double longitude = Double.parseDouble(gpsData.getyGPS());

            LatLng latLng = new LatLng(latitude, longitude);
            options.add(latLng);
        }

        // action bar in the menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListTrackActivity.class));
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // draw the path with the polyline
        pathDraw = mMap.addPolyline(options);


        // set the markers for every POI in the list, marker for POIs are set in green
        for(POI poi : poiList)
        {
            double latitude = Double.parseDouble(poi.getGpsLocationPOI().getxGPS());
            double longitude = Double.parseDouble(poi.getGpsLocationPOI().getyGPS());

            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).
                    title(poi.getNamePOI())).showInfoWindow();
        }

        // set the markes for every POD in the list, marker for PODs are set in orange
        for(POD pod : podList)
        {
            double latitude = Double.parseDouble(pod.getGpsLocationPOD().getxGPS());
            double longitude = Double.parseDouble(pod.getGpsLocationPOD().getyGPS());

            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).
                    title(pod.getNamePOD())).showInfoWindow();
        }


        // set two markers for the start and end location of a track
        // get first and last GPSData from the track arraylist
        gpsDatas = track.getGpsTrack();
        GPSData startLocation = gpsDatas.get(0);
        GPSData endLocation = gpsDatas.get(gpsDatas.size()-1);

        // set the marker for start location
        double startLatitude = Double.parseDouble(startLocation.getxGPS());
        double startLongitude = Double.parseDouble(startLocation.getyGPS());
        LatLng LatLngStartLocation = new LatLng(startLatitude, startLongitude);
        mMap.addMarker(new MarkerOptions().position(LatLngStartLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("start")).showInfoWindow();

        // set the marker for end location
        double endLatitude = Double.parseDouble(endLocation.getxGPS());
        double endLongitude = Double.parseDouble(endLocation.getyGPS());
        LatLng LatLngEndLocation = new LatLng(endLatitude, endLongitude);
        mMap.addMarker(new MarkerOptions().position(LatLngEndLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("end")).showInfoWindow();


        // move the camera and zoom it to the first gps data object which is the start of the track
        double latitude = Double.parseDouble(gpsDatas.get(0).getxGPS());
        double longitude = Double.parseDouble(gpsDatas.get(0).getyGPS());
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
    }
}
