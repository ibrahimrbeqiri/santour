package com.group4.santour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import models.Track;

public class ListTrackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference sanTourDatabase;
    private ListView listViewTrack;
    private ArrayList<Track> tracks = new ArrayList<>();
    private ArrayList<String> trackList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_track);

        /*for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            trackList.add("Track Name: " + track.getNameTrack() + " Date: " + track.getTrackDate() + "\n" + "Distance: " + track.getKm() + " Time: " + track.getTimer());
            System.out.println(track.getIdTrack());
        }*/

        sanTourDatabase = FirebaseDatabase.getInstance().getReference().child("tracks");
        sanTourDatabase.orderByChild("trackDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Track track = postSnapshot.getValue(Track.class);
                    tracks.add(track);
                    trackList.add("Track Name: " + track.getNameTrack() + "\nDate: " + track.getTrackDate() + "\nDistance: " + track.getKm() + " km" + "   Time: " + track.getTimer());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, trackList);
        listViewTrack = findViewById(R.id.listtrack);
        listViewTrack.setAdapter(adapter);

        listViewTrack.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListTrackActivity.this, TrackListMap.class);
                intent.putExtra("track", tracks.get(position));
                startActivity(intent);
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
    }

}
