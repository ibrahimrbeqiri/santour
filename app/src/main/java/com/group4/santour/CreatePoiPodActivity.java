package com.group4.santour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import firebase.FirebaseQueries;
import models.GPSData;
import models.POD;
import models.POI;
import models.Track;

public class CreatePoiPodActivity extends AppCompatActivity{

    private Button btnCamera;
    private ImageView imageView;
    private Uri uri;
    private boolean isPOI = false;
    private boolean showtime = false;

    private String imageString;
    private Bitmap bitmap;
    private POI showpoi;
    private POD showpod;
    private EditText nameset;
    private ImageView imageset;
    private Button takeset;
    private EditText latset;
    private EditText longset;
    private EditText descset;
    private Button saveset;
    private Button nextset;


    private POD pod = new POD();

    private String vertset = "-1";
    private String rockset = "-1";
    private String slopeset = "-1";

    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poi_pod);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

         /*
         * Get the intent and take the values from the intent.
         * With the boolean, look if it's a POI or a POD,
         * so you know which button has to be enabled and which one has to be disabled
         */
        Intent i = getIntent();
        if(i.getSerializableExtra("POI") != null) {
            isPOI = (Boolean) i.getSerializableExtra("POI");
        }

        double lat;
        double lon;

        /*
         * Get the latitude and longitude of the track.
         * When there are no values taken, it will add them to 0;0.
         */
        if(i.getSerializableExtra("latitude") != null) {
            lat = (Double) i.getSerializableExtra("latitude");
            lon = (Double) i.getSerializableExtra("longitude");
        }else{
            lat = 0;
            lon = 0;
        }

        String latitude;
        String longitude;

        /*
         * Convert latitude and longitude into Strings,
         * so you can show them on the view!
         */
        latitude = String.format("%.7f", lat);
        longitude = String.format("%.7f", lon);

        EditText editText1 = findViewById(R.id.gpsdataX);
        editText1.setText(latitude);

        EditText editText2 = findViewById(R.id.gpsdataY);
        editText2.setText(longitude);

        /*
         * It's POI = Save button is shown
         * It's POD = Next button is shown
         */
        if(!isPOI){
            Button button = findViewById(R.id.savePOI);
            button.setEnabled(false);
        }else{
            Button button = findViewById(R.id.nextPOD);
            button.setEnabled(false);
        }

            Intent intent = getIntent();
        if(intent.getSerializableExtra("poiobject") != null ) {
            showpoi = (POI) intent.getSerializableExtra("poiobject");
            nameset = findViewById(R.id.createPoi);
            nameset.setText(showpoi.getNamePOI());
            nameset.setEnabled(false);

            imageset = findViewById(R.id.imageView);
            if(showpoi.getPicturePOI() != null){
                byte[] decodedByteArray = android.util.Base64.decode(showpoi.getPicturePOI(), Base64.DEFAULT);
                Bitmap map = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
                imageset.setImageBitmap(map);
            }

            if(showpoi.getPicturePOI() ==null) {

                imageset = findViewById(R.id.imageView);
                imageset.setVisibility(View.GONE);
            }

            takeset = findViewById(R.id.takePicture);
            takeset.setVisibility(View.GONE);

            latset = findViewById(R.id.gpsdataX);
            latset.setText(showpoi.getGpsLocationPOI().getxGPS());

            longset = findViewById(R.id.gpsdataY);
            longset.setText(showpoi.getGpsLocationPOI().getyGPS());

            descset = findViewById(R.id.description);
            descset.setText(showpoi.getDescriptionPOI());
            descset.setEnabled(false);

            saveset = findViewById(R.id.savePOI);
            saveset.setVisibility(View.GONE);

            nextset = findViewById(R.id.nextPOD);
            nextset.setVisibility(View.GONE);

        }
        /*
         * Set POD View for the List!
         */
        if(intent.getSerializableExtra("podobject") != null){

            showpod = (POD) intent.getSerializableExtra("podobject");
            nameset = findViewById(R.id.createPoi);
            nameset.setText(showpod.getNamePOD());
            nameset.setEnabled(false);

            imageset = findViewById(R.id.imageView);
            if(showpod.getPicturePOD() != null){
                byte[] decodedByteArray = android.util.Base64.decode(showpod.getPicturePOD(), Base64.DEFAULT);
                Bitmap map = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
                imageset.setImageBitmap(map);
            }

            if(showpod.getPicturePOD()==null) {
                imageset = findViewById(R.id.imageView);
                imageset.setVisibility(View.GONE);
            }
            takeset = findViewById(R.id.takePicture);
            takeset.setVisibility(View.GONE);

            latset = findViewById(R.id.gpsdataX);
            latset.setText(showpod.getGpsLocationPOD().getxGPS());

            longset = findViewById(R.id.gpsdataY);
            longset.setText(showpod.getGpsLocationPOD().getyGPS());

            descset = findViewById(R.id.description);
            descset.setText(showpod.getDescriptionPOD());
            descset.setEnabled(false);

            saveset = findViewById(R.id.savePOI);
            saveset.setVisibility(View.GONE);

            if(showpod.getDetailVerticality() != null){
                vertset = showpod.getDetailVerticality();
            }
            if(showpod.getDetailRocks() != null){
                rockset = showpod.getDetailRocks();
            }
            if(showpod.getDetailSlope() != null){
                slopeset = showpod.getDetailSlope();
            }

            showtime = true;
        }


        btnCamera = findViewById(R.id.takePicture);
        imageView = findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });



        //action bar and menu initialization

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CreateTrack.class));
            }
        });
    }
    //method to encode the image to base64 string
    public String encodeToBase64(Bitmap bitmap) {

        String imageString;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        return imageString;

    }
    /*
     * Method is needed to capture the Camera activities
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*
         * If the camera was working and the picture was taken with the phone camera,
         * it will save the Picture in an Bitmap and display it on the imageView
         */
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            //uri = data.getData();
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            //Encode into base 64
            imageString = encodeToBase64(bitmap);


        }
    }

    /*
     * When you rotate your screen, your values get missing.
     * OnSaveInstanceState saves the values before rotating.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        EditText editText1 = findViewById(R.id.createPoi);
        String name = editText1.getText().toString();

        /*
         * Put your Bitmap into a bundle, otherwise the serialization won't work.
         */
        Bundle bundle = new Bundle();
        bundle.putParcelable("BitmapImage",bitmap);

        EditText editText2 = findViewById(R.id.gpsdataX);
        String dataX = editText2.getText().toString();
        EditText editText3 = findViewById(R.id.gpsdataY);
        String dataY = editText3.getText().toString();

        EditText editText4 = findViewById(R.id.description);
        String description = editText4.getText().toString();

        savedInstanceState.putString("myName",name);
        savedInstanceState.putBundle("bundle", bundle);
        savedInstanceState.putString("dataX", dataX);
        savedInstanceState.putString("dataY", dataY);
        savedInstanceState.putString("description", description);

    }

    /*
     * onRestoreInstanceState gets after the rotation all the saved values back.
     * Puts them back into the View Classes
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        /*
         * Bitmap must be saved in an object, otherwise it will not save your Picture
         */
        String name = savedInstanceState.getString("myName");
        String dataX = savedInstanceState.getString("dataX");
        String dataY = savedInstanceState.getString("dataY");
        Bundle bundle = savedInstanceState.getBundle("bundle");
        bitmap = bundle.getParcelable("BitmapImage");

        String description = savedInstanceState.getString("description");

        EditText editText1 = findViewById(R.id.createPoi);
        editText1.setText(name);



        EditText editText2 = findViewById(R.id.gpsdataX);
        editText2.setText(dataX);
        EditText editText3 = findViewById(R.id.gpsdataY);
        editText3.setText(dataY);

        ImageView imageView1 = findViewById(R.id.imageView);
        imageView1.setImageBitmap(bitmap);


        EditText editText4 = findViewById(R.id.description);
        editText4.setText(description);

    }

    /*
     * Save button saves the POI in the firebase!
     */
    public void sendCreatePOI(View view)throws ExecutionException, InterruptedException{

        /*
         * Take all the values from the view objects
         */
        EditText editText1 = findViewById(R.id.createPoi);
        String name = editText1.getText().toString();

        EditText editText2 = findViewById(R.id.gpsdataX);
        EditText editText3 = findViewById(R.id.gpsdataY);

        /*
         * Latitude and Longitude have to be saves into the GPSData
         * Can then be compared with all the GPSData of the track to display where the POI is located
         */
        GPSData gpsData = new GPSData();
        gpsData.setxGPS(editText2.getText().toString());
        gpsData.setyGPS(editText3.getText().toString());

        EditText editText4 = findViewById(R.id.description);
        String description = editText4.getText().toString();

        /*
         * Create the POI Object with all the values it needs to be saved
         */
        POI poi = new POI();
        poi.setNamePOI(name);
        poi.setDescriptionPOI(description);
        poi.setGpsLocationPOI(gpsData);

        poi.setPicturePOI(imageString);


        /*
         * Make a new bundle and put the poi in it.
         * With the intent, we will transfer the bundle to the FireBaseTestActivity and save it in Firebase!
         */
        Bundle bundle = new Bundle();
        bundle.putSerializable("poi",poi);

        /*
         * Make the intent to navigate to the other view
         * Put the bundle in the intent so it will be able to save the POI
         */
        Intent intent = new Intent(this, CreateTrack.class);
        intent.putExtras(bundle);

        /*
         * Start the activity with the next intent and finish the view
         * so you won't be able to click back after saving
         */


        startActivity(intent);
        finish();

    }

    /*
     * Next button saves the POD object and transferes it to the PODDetails Activity
     */
    public void sendNextPOD(View view) throws ExecutionException, InterruptedException{



        /*
         * Take all the values from the view objects
         */
        EditText editText1 = findViewById(R.id.createPoi);
        String name = editText1.getText().toString();

        EditText editText2 = findViewById(R.id.gpsdataX);
        EditText editText3 = findViewById(R.id.gpsdataY);

        /*
         * Latitude and Longitude have to be saves into the GPSData
         * Can then be compared with all the GPSData of the track to display where the POD is located
         */
        GPSData gpsData = new GPSData();

        gpsData.setxGPS(editText2.getText().toString());
        gpsData.setyGPS(editText3.getText().toString());

        EditText editText4 = findViewById(R.id.description);
        String description = editText4.getText().toString();


        /*
         * Create the POD Object with all the values it needs to be saved
         */
        pod.setNamePOD(name);
        pod.setDescriptionPOD(description);
        pod.setGpsLocationPOD(gpsData);

        pod.setPicturePOD(imageString);

         /*
         * Make the intent to navigate to the other view
         * Put the object in the intent so you can use the POD for later use
         */
        Intent intent = new Intent(this, PodDetails.class);
        if(!showtime) {
            intent.putExtra("pod", pod);
        }else{
            intent.putExtra("vertset", vertset);
            intent.putExtra("rockset", rockset);
            intent.putExtra("slopeset", slopeset);
        }

        /*
         * Start the activity with the next intent and finish the view
         * so you won't be able to click back after saving
         */
        startActivity(intent);
        finish();






    }

}
