package com.group4.santour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class PodDetails extends AppCompatActivity {

    private CheckBox verticality;
    private CheckBox rocks;
    private CheckBox slope;

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
        addListenerToCheckBox();


    }

    public void addListenerToCheckBox(){
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);

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
