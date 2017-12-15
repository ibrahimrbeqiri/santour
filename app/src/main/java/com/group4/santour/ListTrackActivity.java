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
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.ListAdapter;
import models.Track;

public class ListTrackActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppCompatActivity activity;
    private DatabaseReference sanTourDatabase;
    private DatabaseReference trackCloudEndPoint;
    private ListView listViewTrack;
    private List<Track> tracks;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_track);

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

        sanTourDatabase =  FirebaseDatabase.getInstance().getReference();
        trackCloudEndPoint = sanTourDatabase.child("track");
        tracks = new ArrayList<>();
        //listViewTrack = activity.findViewById(R.id.listViewTest);

        adapter = new ListAdapter(this, tracks);
    }

    public void getTracksFromFirebase()
    {
        trackCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    Track track = noteSnapshot.getValue(Track.class);
                    tracks.add(track);
                    Log.d("*** test track", track.getNameTrack()+" "+track.getDescriptionTrack());
                }

                listViewTrack.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.getMessage());
            }
        });
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
