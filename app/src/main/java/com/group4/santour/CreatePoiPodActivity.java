package com.group4.santour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

import models.GPSData;
import models.POD;
import models.POI;

public class CreatePoiPodActivity extends AppCompatActivity {

    private Button btnCamera;
    private ImageView imageView;
    private Uri uri;

    private static final int CAMERA_REQUEST_CODE = 1;

  //  private StorageReference nStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poi_pod);

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
        String description = editText3.getText().toString();

        //Create the POI
        POI poi = new POI();
        poi.setNamePOI(name);
        poi.setDescriptionPOI(description);
        poi.setGpsLocationPOI(gpsData);

        //Cloud Connection

        //Save in Cloud!

        //Redirect back to Track
        Intent intent = new Intent(this, CreateTrack.class);
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
        String description = editText3.getText().toString();

        //Create the POI
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
