package com.group4.santour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.util.concurrent.ExecutionException;

import firebase.FirebaseQueries;
import models.GPSData;
import models.POD;
import models.POI;
import models.Track;

public class CreatePoiPodActivity extends AppCompatActivity {

    private Button btnCamera;
    private ImageView imageView;
    private Uri uri;
    private boolean isPOI = false;

    private String imageString;
    private Bitmap bitmap;

    private static final int CAMERA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poi_pod);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnCamera = findViewById(R.id.takePicture);
        imageView = findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        /*
         * Get the intent and take the values from the intent.
         * With the boolean, look if it's a POI or a POD,
         * so you know which button has to be enabled and which one has to be disabled
         */
        Intent i = getIntent();
        isPOI = (Boolean)i.getSerializableExtra("POI");


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

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            //Encode into base 64
            FirebaseQueries fbq = new FirebaseQueries();
            imageString = fbq.encodeToBase64(bitmap);


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

        // Picture
        // has to be done with a storage reference
        // Example: https://www.youtube.com/watch?v=Zy2DKo0v-OY

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

        //insert into Firebase storage as bitmap
        FirebaseQueries fbq = new FirebaseQueries();
        fbq.insertPicture(imageString);

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
        POD pod = new POD();
        pod.setNamePOD(name);
        pod.setDescriptionPOD(description);
        pod.setGpsLocationPOD(gpsData);

        pod.setPicturePOD(imageString);

         /*
         * Make the intent to navigate to the other view
         * Put the object in the intent so you can use the POD for later use
         */
        Intent intent = new Intent(this, PodDetails.class);
        intent.putExtra("pod", pod);

         /*
         * Start the activity with the next intent and finish the view
         * so you won't be able to click back after saving
         */

        //insert into Firebase storage as bitmap
        FirebaseQueries fbq = new FirebaseQueries();
        fbq.insertPicture(imageString);

        startActivity(intent);
        finish();

    }

}
