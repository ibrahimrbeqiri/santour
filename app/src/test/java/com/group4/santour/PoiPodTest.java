package com.group4.santour;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import models.GPSData;

/**
 * Created by Lavdrim on 04.12.2017.
 */

public class PoiPodTest {

    @Test
    public void receivesPictureTaken() throws Exception{

        CreatePoiPodActivity cpp = new CreatePoiPodActivity();

        ImageView imageView1 = cpp.findViewById(R.id.imageView);

        Assert.assertNotNull(imageView1);

    }

    @Test
    public void checkboxSeekBarShowing() throws Exception{

        PodDetails pod = new PodDetails();
        boolean isShowing = true;

        SeekBar seekBar1 = pod.findViewById(R.id.seekBar1);
        CheckBox checkBox1 = pod.findViewById(R.id.verticality);

        SeekBar seekBar2 = pod.findViewById(R.id.seekBar2);
        CheckBox checkBox2 = pod.findViewById(R.id.rocks);

        SeekBar seekBar3 = pod.findViewById(R.id.seekBar3);
        CheckBox checkBox3 = pod.findViewById(R.id.slope);


        if(checkBox1.isChecked() && seekBar1.getVisibility() != View.VISIBLE){
            isShowing = false;
        }

        if(!checkBox1.isChecked() && seekBar1.getVisibility() == View.VISIBLE){
            isShowing = false;
        }

        if(checkBox2.isChecked() && seekBar2.getVisibility() != View.VISIBLE){
            isShowing = false;
        }

        if(!checkBox2.isChecked() && seekBar2.getVisibility() == View.VISIBLE){
            isShowing = false;
        }

        if(checkBox3.isChecked() && seekBar3.getVisibility() != View.VISIBLE){
            isShowing = false;
        }

        if(!checkBox3.isChecked() && seekBar3.getVisibility() == View.VISIBLE){
            isShowing = false;
        }

        Assert.assertTrue(isShowing);
    }

    @Test
    public void receivesCoordinates() throws Exception{

        CreatePoiPodActivity cpp = new CreatePoiPodActivity();

        EditText editText1 = cpp.findViewById(R.id.gpsdataX);
        String lat = editText1.getText().toString();

        EditText editText2 = cpp.findViewById(R.id.gpsdataY);
        String lng = editText2.getText().toString();

        GPSData data = new GPSData();
        data.setxGPS(lat);
        data.setyGPS(lng);

        GPSData nullData = new GPSData("0", "0");

        Assert.assertNotEquals(data, nullData);

    }
}
