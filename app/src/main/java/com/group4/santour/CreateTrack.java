package com.group4.santour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateTrack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_track);
    }

    public void createTrack(View v) {
        String trackname = ((EditText) findViewById(R.id.editText)).getText().toString();
        System.out.println(trackname);
        System.out.println("pressed");
    }

}
