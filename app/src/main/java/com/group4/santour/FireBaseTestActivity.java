package com.group4.santour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import firebase.Track_DB;


/**
 * Created by Vincent_2 on 24.11.2017.
 */


public class FireBaseTestActivity extends AppCompatActivity {

    private AppCompatActivity activity = FireBaseTestActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_test);

        //get tracks data
        Track_DB track_db = new Track_DB(activity);

        // get and display data
        //track_db.getDataFromFirebase();

        // reset data
        //track_db.addInitialDataToFirebase();

    }

}
