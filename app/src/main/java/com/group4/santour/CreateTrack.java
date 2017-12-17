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
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import firebase.FirebaseQueries;
import models.GPSData;
import models.POD;
import models.POI;
import models.Track;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;


public class CreateTrack extends AppCompatActivity implements OnMapReadyCallback,Serializable,
        LocationListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        NavigationView.OnNavigationItemSelectedListener{


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
    private String currentXGPSData;
    private String currentYGPSData;


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
    private Button POIPODList;

    private List<POI> poilist;
    private List<POD> podlist;

    private FirebaseQueries fbq = new FirebaseQueries();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_createtrack);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // get the location service to display the location later in the app
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // if the location is not enabled in the device show the alert dialog and ask for permission
        if (!isLocationEnabled())
            showAlert(1);

        // instanciate the lists on activity creation
        points = new ArrayList<>();
        locations = new ArrayList<>();
        gpsDataList = new ArrayList<>();

        // send the new intent with the extras when POD button is clicked
        Button POD = findViewById(R.id.POD);
        POD.setEnabled(false);
        POD.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                isPOI = false;
                POIPODList.setEnabled(true);
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

        // send the new intent with the extras when POI button is clicked
        Button POI = findViewById(R.id.POI);
        POI.setEnabled(false);
        POI.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                isPOI = true;
                POIPODList.setEnabled(true);

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

        // POI POD list from this activity the the POIPODList activitie
        POIPODList = findViewById(R.id.POIPODList);
        POIPODList.setEnabled(false);
        POIPODList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent = new Intent(CreateTrack.this, POIPODList.class);

                intent.putExtra("poilist", (Serializable) poilist);
                intent.putExtra("podlist", (Serializable) podlist);
                startActivity(intent);
            }
        });


        // button stop
        Button stop = findViewById(R.id.stop);
        stop.setEnabled(false);

        // set the distance text view
        distance = findViewById(R.id.distance);
        distance.setText("Distance: 0.00 km");

        //action bar and menu initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        //enable the location and get the location button listeners
        enableMyLocation();
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    public void startTrack(View v) {

        // clear the map from markers
        mMap.clear();
        //create a new track object
        track = new Track();
        //start button
        Button start = findViewById(R.id.start);
        start.setEnabled(false);
        //stop button
        Button stop = findViewById(R.id.stop);
        stop.setEnabled(true);
        //POD and POI button
        Button PODbutton = findViewById(R.id.POD);
        PODbutton.setEnabled(true);
        Button POIbutton = findViewById(R.id.POI);
        POIbutton.setEnabled(true);

        //inizialisation of poi/podlist
        poilist = new ArrayList<>();
        podlist = new ArrayList<>();
        EditText trackname = findViewById(R.id.editText);
        trackname.setEnabled(false);

        // set the distance textview
        distance.setText("Distance: 0.00 km");

        // reinstanciate the lists and the polyline options
        options = new PolylineOptions().width(10).color(Color.RED).geodesic(true);
        points = new ArrayList<>();
        locations = new ArrayList<>();
        gpsDataList = new ArrayList<>();

        // everytime you click start the polyline is removed so you can have a new one
        if(gpsTrack != null) {
            gpsTrack.remove();
        }

        // add current location, coordinates, gpsdata to their respective lists
        points.add(currentCoordinates);
        locations.add(currentLocation);
        gpsDataList.add(currentGpsData);

        // set the timer and start it
        time = findViewById(R.id.chronometer2);
        time.setBase(SystemClock.elapsedRealtime());
        time.start();

        // notify user that the GPS data is being recorded
        Toast.makeText(this, "GPS Data is being recorded!", Toast.LENGTH_SHORT).show();
    }

    public void stopTrack(View v) {

        // start button
        Button start = findViewById(R.id.start);
        start.setEnabled(true);
        //stop button
        Button stop = findViewById(R.id.stop);
        stop.setEnabled(false);
        //POD button
        Button POD = findViewById(R.id.POD);
        POD.setEnabled(false);
        //POI button
        Button POI = findViewById(R.id.POI);
        POI.setEnabled(false);

        //Trackname
        EditText trackname = findViewById(R.id.editText);
        trackname.setEnabled(true);

        // stop the timer and get the data from it in seconds, minutes, hours
        time.stop();
        int elapsed = (int) (SystemClock.elapsedRealtime() - time.getBase());
        int hours = (elapsed / 3600000);
        int minutes = (elapsed - hours * 3600000) / 60000;
        int seconds = (elapsed - hours * 3600000 - minutes * 60000) / 1000;

        // add all the coordinates to the polyline options
        if(points.size() > 1) {
            for (int i = 0; i < points.size(); i++) {
                LatLng point = points.get(i);
                options.add(point);
            }
        }

        // get the polyline
        gpsTrack = mMap.addPolyline(options);

        // calculate the distance
        distanceMade = 0;
        if(locations.size() > 1) {

            for(int i = 1; i < locations.size(); i++) {
                Location beginning = locations.get(i-1);
                Location end = locations.get(i);
                distanceMade = distanceMade + (beginning.distanceTo(end) / 1000);

            }
        }

        // set the distance textview and let the user now GPS data stopped recording
        distance.setText("Distance: " + String.format("%.2f", distanceMade) + " km");
        Toast.makeText(this, "GPS Data is not being recorded!", Toast.LENGTH_SHORT).show();

        // get the data needed to insert into the track object before saving them to firebase
        String nameTrack = ((EditText) findViewById(R.id.editText)).getText().toString();
        String timerString = hours + ":" + minutes + ":" + seconds;
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        // set the track object data
        track.setGpsTrack(gpsDataList);
        track.setNameTrack(nameTrack);
        track.setKm(String.format("%.2f", distanceMade));
        track.setTimer(timerString);
        track.setTrackDate(currentDate);

        // insert the track into firebase realtime database
        fbq.insertTrack(track);

    }

    @Override
    public void onLocationChanged(Location location) {
        // get the coordinates and move camera to that location everytime it changes
        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));

        //GPS data Format
        String xGPSData = String.format("%.7f", location.getLatitude());
        String yGPSData = String.format("%.7f", location.getLongitude());

        GPSData gpsData = new GPSData(xGPSData, yGPSData);

        // set the textview for latitude and longitude everytime they change
        TextView latitude = findViewById(R.id.latitude);
        TextView longitude = findViewById(R.id.longitude);
        latitude.setText("Latitude: " + String.format("%.7f", location.getLatitude()));
        longitude.setText("Longitude: " + String.format("%.7f", location.getLongitude()));

        // add the coordinates to the list and update the currentcoordinates
        if(coordinates != currentCoordinates) {
            points.add(coordinates);
            currentCoordinates = coordinates;

        }
        // add the gpsdata to the list and update the current gps data
        if(gpsData != currentGpsData)
        {
            gpsDataList.add(gpsData);
            currentGpsData = gpsData;

        }
        // add the location to the list and update the current location
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
    // check if location is enabled in the device
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    private void enableMyLocation() {
        // ask for permission from the device
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

            // get the currentlocation from the location manager
            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(currentLocation != null) {
                // set the textviews for latitude and longitude with current coordinates
                TextView latitude = findViewById(R.id.latitude);
                TextView longitude = findViewById(R.id.longitude);
                latitude.setText("Latitude: " + String.format("%.7f", currentLocation.getLatitude()));
                longitude.setText("Longitude: " + String.format("%.7f", currentLocation.getLongitude()));

                // get the current GPS data
                currentXGPSData = String.format("%.7f", currentLocation.getLatitude());
                currentYGPSData = String.format("%.7f", currentLocation.getLongitude());
                currentGpsData = new GPSData(currentXGPSData, currentYGPSData);

                // get the current coordinates and move the camera and zoom it to those coordinates
                currentCoordinates = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentCoordinates));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 10));
            }

        }
    }
    // user permission utils class to ask for permissions from the device
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
    // handle the new intents coming after you save the POD and POI
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);

        if(getIntent().getExtras()!=null) {

            if (getIntent().getExtras().getSerializable("poi") != null) {
                poi = (POI) this.getIntent().getExtras().getSerializable("poi");

                // add the poi to the poilist to display it later
                poilist.add(poi);
                // set the tracks poilist
                track.setPoiTrack(poilist);

                // get the coordinates of the poi
                double xGps = Double.parseDouble(poi.getGpsLocationPOI().getxGPS());
                double yGps = Double.parseDouble(poi.getGpsLocationPOI().getyGPS());

                // add the marker to poi coordinates
                LatLng poiLocation = new LatLng(xGps, yGps);
                mMap.addMarker(new MarkerOptions().position(poiLocation)
                        .title(poi.getNamePOI())).showInfoWindow();

                //insert poi image into Firebase realtime database
                if(poi.getPicturePOI() != null) {
                    fbq.insertPicture(poi.getPicturePOI());
                }
            }

            if (getIntent().getExtras().getSerializable("pod") != null) {
                pod = (POD) this.getIntent().getExtras().getSerializable("pod");

                // add the pod to the poilist to display it later
                podlist.add(pod);
                // set the tracks podlist
                track.setPodTrack(podlist);

                // get the coordinates of the pod
                double xGps = Double.parseDouble(pod.getGpsLocationPOD().getxGPS());
                double yGps = Double.parseDouble(pod.getGpsLocationPOD().getyGPS());

                // add the marker to pod coordinates
                LatLng poiLocation = new LatLng(xGps, yGps);
                mMap.addMarker(new MarkerOptions().position(poiLocation)
                        .title(pod.getNamePOD())).showInfoWindow();

                //insert pod image into Firebase
                if(pod.getPicturePOD() != null) {
                    fbq.insertPicture(pod.getPicturePOD());
                }
            }
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

    // show this aler if the location is not enabled in the device
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // redirect to the corresponding pages when you click an item in the navigation menu
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_create_track:
                break;
            case R.id.nav_display_track:
                //showFragment(new HomeFragment());
                Intent intent_listTrack = new Intent(this, ListTrackActivity.class);
                startActivity(intent_listTrack);
                break;
            case R.id.nav_about:
                //showFragment(new HomeFragment());
                Intent intent_about = new Intent(this, AboutActivity.class);
                startActivity(intent_about);
                break;
            default:
                return false;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

