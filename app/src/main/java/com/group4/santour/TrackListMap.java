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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        track = (Track) intent.getSerializableExtra("track");

        options = new PolylineOptions().width(10).color(Color.RED).geodesic(true);


        gpsDatas = track.getGpsTrack();
        poiList = track.getPoiTrack();
        podList = track.getPodTrack();


        for(GPSData gpsData : gpsDatas)
        {
            double latitude = Double.parseDouble(gpsData.getxGPS());
            double longitude = Double.parseDouble(gpsData.getyGPS());

            LatLng latLng = new LatLng(latitude, longitude);
            options.add(latLng);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ListTrackActivity.class));
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        pathDraw = mMap.addPolyline(options);

        for(POI poi : poiList)
        {
            double latitude = Double.parseDouble(poi.getGpsLocationPOI().getxGPS());
            double longitude = Double.parseDouble(poi.getGpsLocationPOI().getyGPS());

            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(poi.getNamePOI())).showInfoWindow();
        }

        for(POD pod : podList)
        {
            double latitude = Double.parseDouble(pod.getGpsLocationPOD().getxGPS());
            double longitude = Double.parseDouble(pod.getGpsLocationPOD().getyGPS());

            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(pod.getNamePOD())).showInfoWindow();
        }

        double latitude = Double.parseDouble(gpsDatas.get(0).getxGPS());
        double longitude = Double.parseDouble(gpsDatas.get(0).getyGPS());

        LatLng latLng = new LatLng(latitude, longitude);
        // Add a marker in Sydney and move the camera
       mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
    }
}
