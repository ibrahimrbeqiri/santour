package com.group4.santour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import models.POD;

public class PodDetails extends AppCompatActivity {

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

        Intent i = getIntent();
        pod = (POD)i.getSerializableExtra("pod");

        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);

        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);

        addListenerToCheckBox();


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        Boolean isShown1 = false;
        Boolean isShown2 = false;
        Boolean isShown3 = false;

        if(verticality.isChecked()){
            isShown1 = true;
        }
        if(rocks.isChecked()){
            isShown2 = true;
        }
        if(slope.isChecked()){
            isShown3 = true;
        }

        savedInstanceState.putBoolean("vert",isShown1);
        savedInstanceState.putBoolean("rock", isShown2);
        savedInstanceState.putBoolean("slope", isShown3);


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

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

    public void sendSavePOD(View view)throws ExecutionException, InterruptedException{
        if(verticality.isChecked()){
            pod.setDetailVerticality(String.valueOf(seekBar1.getProgress()));
        }
        if(rocks.isChecked()){
            pod.setDetailRocks(String.valueOf(seekBar2.getProgress()));
        }
        if(slope.isChecked()){
            pod.setDetailSlope(String.valueOf(seekBar3.getProgress()));
        }

        System.out.println(pod.getNamePOD());
        System.out.println(pod.getDescriptionPOD());
        System.out.println(pod.getDetailVerticality());
        System.out.println(pod.getDetailRocks());
        System.out.println(pod.getDetailSlope());
        System.out.println("TEEEEEEEEEEEEEEEEEEEEEESSSSSSSSSSSSSSTTTTTTTTTTTTTTT");

        Bundle bundle = new Bundle();
        bundle.putSerializable("pod",pod);
        Intent intent = new Intent(this, CreateTrack.class);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();

    }

    public void addListenerToCheckBox(){


        textMin1 = (TextView) findViewById(R.id.textMin1);
        textMin2 = (TextView) findViewById(R.id.textMin2);
        textMin3 = (TextView) findViewById(R.id.textMin3);

        textMax1 = (TextView) findViewById(R.id.textMax1);
        textMax2 = (TextView) findViewById(R.id.textMax2);
        textMax3 = (TextView) findViewById(R.id.textMax3);

        verticality = (CheckBox) findViewById(R.id.verticality);
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

        rocks = (CheckBox) findViewById(R.id.rocks);
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

        slope = (CheckBox) findViewById(R.id.slope);
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

}
