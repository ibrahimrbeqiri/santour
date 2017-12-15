package com.group4.santour;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import models.POD;
import models.POI;


public class POIPODList extends AppCompatActivity {

    private ListView poilistview;
    private ListView podlistview;
    private ArrayList<POI> poiArrayList;
    private ArrayList<POD> podArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poipodlist);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        poiArrayList = (ArrayList<POI>) intent.getSerializableExtra("poilist");
        podArrayList = (ArrayList<POD>) intent.getSerializableExtra("podlist");

        ArrayList<String> pois = new ArrayList<>();
        ArrayList<String> pods = new ArrayList<>();

        poilistview = findViewById(R.id.listpoi);
        podlistview = findViewById(R.id.listpod);

        for(int i = 0; i < poiArrayList.size(); i++) {
            POI poi = poiArrayList.get(i);
            pois.add("Name: "+poi.getNamePOI() + "\n" + "Latitude: " + poi.getGpsLocationPOI().getxGPS() + " Longitude: " + poi.getGpsLocationPOI().getyGPS());
        }
        for(int i = 0; i < podArrayList.size(); i++) {
            POD pod = podArrayList.get(i);
            pods.add("Name: "+pod.getNamePOD() + "\n" + "Latitude: " + pod.getGpsLocationPOD().getxGPS() + " Longitude: " + pod.getGpsLocationPOD().getyGPS());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pois);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pods);
        poilistview.setAdapter(adapter);
        podlistview.setAdapter(adapter2);

    }
}
