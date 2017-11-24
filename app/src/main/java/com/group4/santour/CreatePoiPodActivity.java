package com.group4.santour;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

public class CreatePoiPodActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poi_pod);

        Button btnCamera = (Button) findViewById(R.id.takePicture);
        imageView = (ImageView)findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

    }

    public void sendCreatePOI(View view)throws ExecutionException, InterruptedException{

        EditText editText1 = (EditText) findViewById(R.id.createPoi);
        String name = editText1.getText().toString();

        // Picture


        EditText editText2 = (EditText) findViewById(R.id.gpsdata);
        String gpsData = editText2.getText().toString();

        EditText editText3 = (EditText) findViewById(R.id.description);
        String description = editText3.getText().toString();

        //Cloud Connection

        //Save in Cloud!
    }
}
