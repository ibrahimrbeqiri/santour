package com.group4.santour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

import models.POD;
import models.POI;


public class POIPODList extends AppCompatActivity{

    private ListView poilistview;
    private ListView podlistview;
    private ArrayList<POI> poiArrayList;
    private ArrayList<POD> podArrayList;
    private ArrayList<String> pois;
    private ArrayList<String> pods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poipodlist);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        Intent intent = getIntent();
            poiArrayList = (ArrayList<POI>) intent.getSerializableExtra("poilist");
            podArrayList = (ArrayList<POD>) intent.getSerializableExtra("podlist");


           pois = new ArrayList<>();
           pods = new ArrayList<>();

            poilistview = findViewById(R.id.listpoi);
            podlistview = findViewById(R.id.listpod);

            if (!poiArrayList.isEmpty()) {
                for (int i = 0; i < poiArrayList.size(); i++) {
                    POI poi = poiArrayList.get(i);
                    pois.add("Name: " + poi.getNamePOI() + "\n" + "Latitude: " + poi.getGpsLocationPOI().getxGPS() + " Longitude: " + poi.getGpsLocationPOI().getyGPS());
                }
            }
            if (!podArrayList.isEmpty()) {
                for (int i = 0; i < podArrayList.size(); i++) {
                    POD pod = podArrayList.get(i);
                    pods.add("Name: " + pod.getNamePOD() + "\n" + "Latitude: " + pod.getGpsLocationPOD().getxGPS() + " Longitude: " + pod.getGpsLocationPOD().getyGPS());
                }
            }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pois);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pods);
        poilistview.setAdapter(adapter);
        podlistview.setAdapter(adapter2);

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
}
