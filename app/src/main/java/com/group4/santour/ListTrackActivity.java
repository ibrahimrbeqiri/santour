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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        // get the Firebase reference and get the tracks ordered by name
        sanTourDatabase = FirebaseDatabase.getInstance().getReference().child("tracks");
        sanTourDatabase.orderByChild("nameTrack").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                    Track track = dataSnapshot.getValue(Track.class);
                    tracks.add(track);
                    trackList.add("Track Name: " + track.getNameTrack() + "\nDate: " + track.getTrackDate() + "\nDistance: " + track.getKm() + " km" + "   Time: " + track.getTimer());
                    adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // set the adapter with the tacklist which we got from firebase
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, trackList);
        // set the listview in the activity with the adapter
        listViewTrack = findViewById(R.id.listtrack);
        listViewTrack.setAdapter(adapter);

        // if an item is clicked it will go to the Track List Map with the corresponding track
        listViewTrack.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListTrackActivity.this, TrackListMap.class);
                intent.putExtra("track", tracks.get(position));
                startActivity(intent);
            }

        });

        // action bar and menu initialization
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

        // redirect to the selected menu item
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_create_track:
                //showFragment(new HomeFragment());
                Intent intent_create = new Intent(ListTrackActivity.this, CreateTrack.class);
                startActivity(intent_create);
                break;
            case R.id.nav_display_track:
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
