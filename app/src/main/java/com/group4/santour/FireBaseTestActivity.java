package com.group4.santour;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import DAL.Track_DB;
import adapters.ListAdapter;
import models.Track;


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
