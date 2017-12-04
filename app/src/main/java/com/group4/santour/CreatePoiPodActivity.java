package com.group4.santour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import firebase.FirebaseQueries;
import models.GPSData;
import models.POD;
import models.POI;
import models.Track;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class CreatePoiPodActivity extends AppCompatActivity {

    private Button btnCamera;
    private ImageView imageView;
    private Uri uri;
    private boolean isPOI = false;
    private Track track;

    private static final int CAMERA_REQUEST_CODE = 1;

  //  private StorageReference nStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poi_pod);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

      //  nStorage = FirebaseStorage.getInstance().getReference();

        btnCamera = (Button) findViewById(R.id.takePicture);
        imageView = (ImageView)findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        Intent i = getIntent();
        isPOI = (Boolean)i.getSerializableExtra("POI");
        track = (Track)i.getSerializableExtra("track");


        String latitude;
        String longitude;

        double lat;
        double lon;

        if((Double)i.getSerializableExtra("latitude") != null) {
            lat = (Double) i.getSerializableExtra("latitude");
            lon = (Double) i.getSerializableExtra("longitude");
        }else{
            lat = 0;
            lon = 0;
        }

        //double lat = 163.123;
        //double lon = 325.234;


        latitude = String.format("%.7f", lat);
        longitude = String.format("%.7f", lon);

        EditText editText1 = findViewById(R.id.gpsdataX);
        editText1.setText(latitude);

        EditText editText2 = findViewById(R.id.gpsdataY);
        editText2.setText(longitude);

        if(!isPOI){
            Button button = findViewById(R.id.savePOI);
            button.setEnabled(false);
        }else{
            Button button = findViewById(R.id.nextPOD);
            button.setEnabled(false);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            uri = data.getData();

         //   StorageReference filepath = nStorage.child("Photos").child(uri.getLastPathSegment());

         //   filepath.putFile(uri).addOnSuccessListener..

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        EditText editText1 = (EditText) findViewById(R.id.createPoi);
        String name = editText1.getText().toString();

        EditText editText2 = (EditText) findViewById(R.id.gpsdataX);
        String dataX = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.gpsdataY);
        String dataY = editText3.getText().toString();

        EditText editText4 = (EditText) findViewById(R.id.description);
        String description = editText4.getText().toString();

        savedInstanceState.putString("myName",name);
        savedInstanceState.putString("dataX", dataX);
        savedInstanceState.putString("dataY", dataY);
        savedInstanceState.putString("description", description);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        String name = savedInstanceState.getString("myName");
        String dataX = savedInstanceState.getString("dataX");
        String dataY = savedInstanceState.getString("dataY");
        String description = savedInstanceState.getString("description");

        EditText editText1 = (EditText) findViewById(R.id.createPoi);
        editText1.setText(name);

        EditText editText2 = (EditText) findViewById(R.id.gpsdataX);
        editText2.setText(dataX);
        EditText editText3 = (EditText) findViewById(R.id.gpsdataY);
        editText3.setText(dataY);

        EditText editText4 = (EditText) findViewById(R.id.description);
        editText4.setText(description);

    }

    public void sendCreatePOI(View view)throws ExecutionException, InterruptedException{

        EditText editText1 = (EditText) findViewById(R.id.createPoi);
        String name = editText1.getText().toString();

        // Picture
        // has to be done with a storage reference
        // Example: https://www.youtube.com/watch?v=Zy2DKo0v-OY

        EditText editText2 = (EditText) findViewById(R.id.gpsdataX);
        EditText editText3 = (EditText) findViewById(R.id.gpsdataY);

        //Create the GPS Data with the coordinates
        GPSData gpsData = new GPSData();

        gpsData.setxGPS(editText2.getText().toString());
        gpsData.setyGPS(editText3.getText().toString());

        EditText editText4 = (EditText) findViewById(R.id.description);
        String description = editText4.getText().toString();

        //Create the POI
        POI poi = new POI();
        poi.setNamePOI(name);
        poi.setDescriptionPOI(description);
        poi.setGpsLocationPOI(gpsData);

        //Cloud Connection

        //Save in Cloud!

        //Redirect back to Track

        Bundle bundle = new Bundle();
        bundle.putSerializable("poi",poi);
        Intent intent = new Intent(this, CreateTrack.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }

    public void sendNextPOD(View view) throws ExecutionException, InterruptedException{
        EditText editText1 = (EditText) findViewById(R.id.createPoi);
        String name = editText1.getText().toString();

        // Picture
        // has to be done with a storage reference
        // Example: https://www.youtube.com/watch?v=Zy2DKo0v-OY

        EditText editText2 = (EditText) findViewById(R.id.gpsdataX);
        EditText editText3 = (EditText) findViewById(R.id.gpsdataY);

        //Create the GPS Data with the coordinates
        GPSData gpsData = new GPSData();

        gpsData.setxGPS(editText2.getText().toString());
        gpsData.setyGPS(editText3.getText().toString());

        EditText editText4 = (EditText) findViewById(R.id.description);
        String description = editText4.getText().toString();

        //Create the POD
        POD pod = new POD();
        pod.setNamePOD(name);
        pod.setDescriptionPOD(description);
        pod.setGpsLocationPOD(gpsData);

        Intent intent = new Intent(this, PodDetails.class);
        intent.putExtra("pod", pod);

        startActivity(intent);
        finish();

    }
}
