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
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import firebase.FirebaseQueries;
import models.GPSData;
import models.POD;
import models.POI;
import models.Track;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;


public class CreateTrack extends FragmentActivity implements OnMapReadyCallback,
        LocationListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback{


    final static int PERMISSION_ALL = 1;
    final static String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Polyline gpsTrack;
    private PolylineOptions options;
    private ArrayList<LatLng> points;
    private ArrayList<GPSData> gpsDataList;
    private GPSData currentGpsData;

    private Chronometer time;
    private Location currentLocation;
    private LatLng currentCoordinates;
    private ArrayList<Location> locations;
    private TextView distance;
    private Boolean isPOI = false;
    private Track track;
    private POI poi;
    private POD pod;
    private float distanceMade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_createtrack);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!isLocationEnabled())
            showAlert(1);

        points = new ArrayList<>();
        locations = new ArrayList<>();

        Button POD = findViewById(R.id.POD);
        POD.setEnabled(false);
        POD.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                isPOI = false;
                Intent intent = new Intent(CreateTrack.this, CreatePoiPodActivity.class);
                intent.putExtra("POI", isPOI);
                intent.putExtra("track",track);
                if(currentLocation != null) {
                    intent.putExtra("latitude", currentLocation.getLatitude());
                    intent.putExtra("longitude", currentLocation.getLongitude());
                }
                startActivity(intent);
            }
        });

        Button POI = findViewById(R.id.POI);
        POI.setEnabled(false);
        POI.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                isPOI = true;
                Intent intent = new Intent(CreateTrack.this, CreatePoiPodActivity.class);
                intent.putExtra("POI", isPOI);
                intent.putExtra("track",track);
                if(currentLocation != null) {
                    intent.putExtra("latitude", currentLocation.getLatitude());
                    intent.putExtra("longitude", currentLocation.getLongitude());
                }
                startActivity(intent);
            }
        });

        Button stop = findViewById(R.id.stop);
        stop.setEnabled(false);

        distance = findViewById(R.id.distance);
        distance.setText("Distance: 0.00 km");
    }
    public LatLng getCoordinates()
    {
        return currentCoordinates;
    }
    @Override
    public void onPause()
    {
        super.onPause();

    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
    @Override
    public void onSaveInstanceState(Bundle  savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }
    public void startTrack(View v) {

        track = new Track();

        Button start = findViewById(R.id.start);
        start.setEnabled(false);
        Button stop = findViewById(R.id.stop);
        stop.setEnabled(true);

        Button POD = findViewById(R.id.POD);
        POD.setEnabled(true);
        Button POI = findViewById(R.id.POI);
        POI.setEnabled(true);

        EditText trackname = findViewById(R.id.editText);
        trackname.setEnabled(false);

        distance.setText("Distance: 0.00 km");

        options = new PolylineOptions().width(10).color(Color.RED).geodesic(true);
        points = new ArrayList<>();
        locations = new ArrayList<>();
        gpsDataList = new ArrayList<>();

        if(gpsTrack != null) {
            gpsTrack.remove();
        }

        points.add(currentCoordinates);
        locations.add(currentLocation);

        gpsDataList.add(currentGpsData);

        time = findViewById(R.id.chronometer2);
        time.setBase(SystemClock.elapsedRealtime());
        time.start();

        Toast.makeText(this, "GPS Data is being recorded!", Toast.LENGTH_SHORT).show();
    }

    public void stopTrack(View v) {
        Button start = findViewById(R.id.start);
        start.setEnabled(true);
        Button stop = findViewById(R.id.stop);
        stop.setEnabled(false);

        Button POD = findViewById(R.id.POD);
        POD.setEnabled(false);
        Button POI = findViewById(R.id.POI);
        POI.setEnabled(false);

        EditText trackname = findViewById(R.id.editText);
        trackname.setEnabled(true);

        time.stop();

        int elapsed = (int) (SystemClock.elapsedRealtime() - time.getBase());
        int hours = (elapsed / 3600000);
        int minutes = (elapsed - hours * 3600000) / 60000;
        int seconds = (elapsed - hours * 3600000 - minutes * 60000) / 1000;

        if(points.size() > 0) {
            for (int i = 0; i < points.size(); i++) {
                LatLng point = points.get(i);
                options.add(point);
            }
        }

        gpsTrack = mMap.addPolyline(options);

        distanceMade = 0;

        if(locations.size() > 1) {

            for(int i = 1; i < locations.size(); i++) {
                Location beginning = locations.get(i-1);
                Location end = locations.get(i);
                distanceMade = distanceMade + (beginning.distanceTo(end) / 1000);

            }

        }
        distance.setText("Distance: " + String.format("%.2f", distanceMade) + " km");

        Toast.makeText(this, "GPS Data is not being recorded!", Toast.LENGTH_SHORT).show();

        String nameTrack = ((EditText) findViewById(R.id.editText)).getText().toString();
        String timerString = hours + ":" + minutes + ":" + seconds;
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


        track.setGpsTrack(gpsDataList);
        track.setNameTrack(nameTrack);
        track.setKm(String.format("%.2f", distanceMade));
        track.setTimer(timerString);
        track.setTrackDate(currentDate);

        FirebaseQueries fbq = new FirebaseQueries();
        fbq.insertTrack(track);

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));

        GPSData gpsData = new GPSData(location.getLatitude(), location.getLongitude());

        TextView latitude = findViewById(R.id.latitude);
        TextView longitude = findViewById(R.id.longitude);
        latitude.setText("Latitude: " + String.format("%.7f", location.getLatitude()));
        longitude.setText("Longitude: " + String.format("%.7f", location.getLongitude()));


        if(coordinates != currentCoordinates) {
            points.add(coordinates);
            currentCoordinates = coordinates;
            gpsDataList.add(gpsData);
            currentGpsData = gpsData;
        }
        if(location != currentLocation) {
            locations.add(location);
            currentLocation = location;
        }


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
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_HIGH);
            String provider = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(provider, 0, 3, this);

            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(currentLocation != null) {
                TextView latitude = findViewById(R.id.latitude);
                TextView longitude = findViewById(R.id.longitude);
                latitude.setText("Latitude: " + String.format("%.7f", currentLocation.getLatitude()));
                longitude.setText("Longitude: " + String.format("%.7f", currentLocation.getLongitude()));


                currentGpsData = new GPSData(currentLocation.getLatitude(), currentLocation.getLongitude());

                currentCoordinates = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentCoordinates));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 7));
            }

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);

        if(getIntent().getExtras().getSerializable("poi")!=null){
            poi=(POI)this.getIntent().getExtras().getSerializable("poi");
            track.setPoiTrack(poi);
        }

        if(getIntent().getExtras().getSerializable("pod")!=null){
            pod=(POD)this.getIntent().getExtras().getSerializable("pod");
            track.setPodTrack(pod);
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

