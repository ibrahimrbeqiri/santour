package com.group4.santour;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import models.POD;
import models.POI;


public class POIPODList extends AppCompatActivity {

    private ListView poilistview;
    private ListView podlistview;
    private ArrayList<POI> poiArrayList;
    private ArrayList<POD> podArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poipodlist);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        poiArrayList = (ArrayList<POI>) intent.getSerializableExtra("poilist");
        podArrayList = (ArrayList<POD>) intent.getSerializableExtra("podlist");


        poilistview = (ListView) findViewById(R.id.listpoi);
        podlistview = (ListView) findViewById(R.id.listpod);
        ArrayList listado2 = new ArrayList();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,poiArrayList);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1,podArrayList);
        poilistview.setAdapter(adapter);
        podlistview.setAdapter(adapter2);

    }
}
