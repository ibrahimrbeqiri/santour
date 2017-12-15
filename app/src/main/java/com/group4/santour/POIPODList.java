package com.group4.santour;

import android.app.ActionBar;
import android.content.Intent;
<<<<<<< HEAD
import android.support.design.widget.NavigationView;
=======
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
>>>>>>> 21cb487aad9d5c13440930e4b8cb1d9e5f849ad1
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.view.View;
=======
import android.view.MenuItem;
>>>>>>> 21cb487aad9d5c13440930e4b8cb1d9e5f849ad1
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import models.POD;
import models.POI;


public class POIPODList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

<<<<<<< HEAD
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

=======
        //action bar and menu initialization

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);
        if (getIntent().getExtras().getSerializable("poilist") != null){
            poiArrayList = (ArrayList<POI>) intent.getSerializableExtra("poilist");

        }
        if (getIntent().getExtras().getSerializable("podlist") != null){
            poiArrayList = (ArrayList<POI>) intent.getSerializableExtra("podlist");

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_create_track:
                //showFragment(new HomeFragment());
                Intent intent_create = new Intent(this, CreateTrack.class);
                startActivity(intent_create);
                break;
            case R.id.nav_display_track:
                //showFragment(new HomeFragment());
                Intent intent_listTrack = new Intent(this, ListTrackActivity.class);
                startActivity(intent_listTrack);
                break;
            case R.id.nav_about:
                //showFragment(new HomeFragment());
                Intent intent_about = new Intent(this, AboutActivity.class);
                startActivity(intent_about);
                break;
            default:
                return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
>>>>>>> 21cb487aad9d5c13440930e4b8cb1d9e5f849ad1
    }
}
