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
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import models.POD;

public class PodDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private CheckBox verticality;
    private CheckBox rocks;
    private CheckBox slope;

    private POD pod;

    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;

    private TextView textMin1;
    private TextView textMax1;

    private TextView textMin2;
    private TextView textMax2;

    private TextView textMin3;
    private TextView textMax3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_details);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        /*
         * Get the intent to take the pod object which was set in CreatePoiPodActivity
         */
        Intent i = getIntent();
        pod = (POD)i.getSerializableExtra("pod");

        /*
         * Get all the require seekbars
         */
        seekBar1 = findViewById(R.id.seekBar1);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);

        /*
         * add all the listeners for the checkboxes
         */
        addListenerToCheckBox();

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

    /*
     * When you rotate your screen, your values get missing.
     * OnSaveInstanceState saves the values before rotating.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        Boolean isShown1 = false;
        Boolean isShown2 = false;
        Boolean isShown3 = false;

        /*
         * Look which checkbox is selected.
         * Add a new boolean value, which you can put through the serializable
         */
        if(verticality.isChecked()){
            isShown1 = true;
        }
        if(rocks.isChecked()){
            isShown2 = true;
        }
        if(slope.isChecked()){
            isShown3 = true;
        }

        /*
         * Put the boolean values in your bundle
         */
        savedInstanceState.putBoolean("vert",isShown1);
        savedInstanceState.putBoolean("rock", isShown2);
        savedInstanceState.putBoolean("slope", isShown3);


    }

    /*
     * onRestoreInstanceState gets after the rotation all the saved values back.
     * Puts them back into the View Classes
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        /*
         * Get the boolean values from the bundle.
         * If it is checked, then make the sliders visible again.
         */
        Boolean vert = savedInstanceState.getBoolean("vert");
        if(vert){
            seekBar1.setVisibility(View.VISIBLE);
            textMin1.setVisibility(View.VISIBLE);
            textMax1.setVisibility(View.VISIBLE);
        }

        Boolean rock = savedInstanceState.getBoolean("rock");
        if(rock){
            seekBar2.setVisibility(View.VISIBLE);
            textMin2.setVisibility(View.VISIBLE);
            textMax2.setVisibility(View.VISIBLE);
        }

        Boolean slope = savedInstanceState.getBoolean("slope");
        if(slope){
            seekBar3.setVisibility(View.VISIBLE);
            textMin3.setVisibility(View.VISIBLE);
            textMax3.setVisibility(View.VISIBLE);
        }



    }

    /*
     * Button to save the POD into the firebase
     */
    public void sendSavePOD(View view)throws ExecutionException, InterruptedException{

        /*
         * If the Checkbar is checked, add the progress into the pod object
         */
        if(verticality.isChecked()){
            pod.setDetailVerticality(String.valueOf(seekBar1.getProgress()));
        }
        if(rocks.isChecked()){
            pod.setDetailRocks(String.valueOf(seekBar2.getProgress()));
        }
        if(slope.isChecked()){
            pod.setDetailSlope(String.valueOf(seekBar3.getProgress()));
        }

        /*
         * Put the POD into the bundle so you can save it into the firebase
         */
        Bundle bundle = new Bundle();
        bundle.putSerializable("pod",pod);

        /*
         * Make the intent to navigate back to the track
         */
        Intent intent = new Intent(this, CreateTrack.class);
        intent.putExtras(bundle);

        /*
         * start the activity and finish it to not be able to go back to the pod
         */
        startActivity(intent);
        finish();

    }

    /*
     * Method to add all Listeners to the checkboxes
     */
    public void addListenerToCheckBox(){

        /*
         * Take all values from the views and add the listeners to the checkboxes
         */
        textMin1 = findViewById(R.id.textMin1);
        textMin2 = findViewById(R.id.textMin2);
        textMin3 = findViewById(R.id.textMin3);

        textMax1 = findViewById(R.id.textMax1);
        textMax2 = findViewById(R.id.textMax2);
        textMax3 = findViewById(R.id.textMax3);

        /*
         * Set the onClickListeners and check
         * if the Checkbox is checked, then make the seekbars visible, otherwise hide them
         */
        verticality = findViewById(R.id.verticality);
        verticality.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(((CheckBox)v).isChecked()){
                            seekBar1.setVisibility(View.VISIBLE);
                            textMin1.setVisibility(View.VISIBLE);
                            textMax1.setVisibility(View.VISIBLE);
                        }else{
                            seekBar1.setVisibility(View.GONE);
                            textMin1.setVisibility(View.GONE);
                            textMax1.setVisibility(View.GONE);
                        }
                    }
                }
        );

        rocks = findViewById(R.id.rocks);
        rocks.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(((CheckBox)v).isChecked()){
                            seekBar2.setVisibility(View.VISIBLE);
                            textMin2.setVisibility(View.VISIBLE);
                            textMax2.setVisibility(View.VISIBLE);
                        }else{
                            seekBar2.setVisibility(View.GONE);
                            textMin2.setVisibility(View.GONE);
                            textMax2.setVisibility(View.GONE);
                        }
                    }
                }
        );

        slope = findViewById(R.id.slope);
        slope.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(((CheckBox)v).isChecked()){
                            seekBar3.setVisibility(View.VISIBLE);
                            textMin3.setVisibility(View.VISIBLE);
                            textMax3.setVisibility(View.VISIBLE);
                        }else{
                            seekBar3.setVisibility(View.GONE);
                            textMin3.setVisibility(View.GONE);
                            textMax3.setVisibility(View.GONE);
                        }
                    }
                }
        );
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
