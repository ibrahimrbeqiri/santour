package com.group4.santour;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import static android.location.Criteria.ACCURACY_FINE;

public class CreateTrack extends FragmentActivity implements OnMapReadyCallback,
        LocationListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {


    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Polyline gpsTrack;
    private PolylineOptions options;
    private LatLng coordinates;
    private ArrayList<LatLng> points;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtrack);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        EditText lat = (EditText) findViewById(R.id.lat);
        lat.setText("LATITUDE");
        EditText lon = (EditText) findViewById(R.id.lon);
        lat.setText("LONGITUDE");

        Button POD = (Button) findViewById(R.id.POD);

        POD.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(CreateTrack.this, CreatePoiPodActivity.class));
            }
        });

        points = new ArrayList<LatLng>();

        Button stop = findViewById(R.id.stop);
        stop.setEnabled(false);

        /*if (Build.VERSION.SDK_INT >= 24 && !isPermissionGranted()) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
        else {
            requestLocation();
        }*/
        if (!isLocationEnabled())
            showAlert(1);
    }
    public void startTrack(View v) {
        Button start = findViewById(R.id.start);
        start.setEnabled(false);
        Button stop = findViewById(R.id.stop);
        stop.setEnabled(true);
        String trackname = ((EditText) findViewById(R.id.editText)).getText().toString();
        System.out.println(trackname);


        options = new PolylineOptions().width(6).color(Color.RED).geodesic(true);

        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
            System.out.println(point);
        }


        Toast.makeText(this, "GPS Data is being recorded!", Toast.LENGTH_SHORT).show();

    }

    public void stopTrack(View v) {
        Button start = (Button) findViewById(R.id.start);
        start.setEnabled(true);
        Button stop = (Button) findViewById(R.id.stop);
        stop.setEnabled(false);

        gpsTrack = mMap.addPolyline(options);

        Toast.makeText(this, "GPS Data is not being recorded!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
        //mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        points.add(coordinates);

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, PERMISSION_ALL,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

            Criteria criteria = new Criteria();
            criteria.setAccuracy(ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_HIGH);
            String provider = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(provider, 5000, 5, this);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_ALL) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();

        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /*private void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        try {
            locationManager.requestLocationUpdates(provider, 10000, 10, this);
        }
        catch (SecurityException se)
        {Criteria criteria = new Criteria();
        criteria.setAccuracy(ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        try {
            locationManager.requestLocationUpdates(provider, 10000, 10, this);
            se.getMessage();
        }
    }
    private boolean isPermissionGranted() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v("mylog", "Permission is granted");
            return true;
        } else {
            Log.v("mylog", "Permission not granted");
            return false;
        }
    }*/
    private void showAlert(final int status) {
        String message, title, btnText;
        if (status == 1) {
            message = "Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                    "use this app";
            title = "Enable Location";
            btnText = "Location Settings";
        } else {
            message = "Please allow this app to access location!";
            title = "Permission access";
            btnText = "Grant";
        }
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        if (status == 1) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        } else
                            requestPermissions(PERMISSIONS, PERMISSION_ALL);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Location button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

}

