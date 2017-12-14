package com.group4.santour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;





public class POIPODList extends AppCompatActivity {

    ListView mListViewpoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poipodlist);
        mListViewpoi = (ListView) findViewById(R.id.listpoi);
        // Get a reference to the database service
       
    }
}
