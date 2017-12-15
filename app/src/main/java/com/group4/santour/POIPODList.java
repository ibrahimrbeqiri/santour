package com.group4.santour;

import android.app.ActionBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poipodlist);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        List<POI> poilist = new ArrayList<>();
        //poilist = (ArrayList<POD>)getIntent().getSerializableExtra("PoiList");

        poilistview = (ListView) findViewById(R.id.listpoi);
        podlistview = (ListView) findViewById(R.id.listpod);
        ArrayList listado2 = new ArrayList();
        listado2.add("uno");
        listado2.add("dos");
        listado2.add("tres");

        for(int i=0 ; i < listado2.size(); i++){
            System.out.print("hahahhahahahhhhhhhhhhaaaaa");
            System.out.print(listado2.toString());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listado2);
        poilistview.setAdapter(adapter);
        podlistview.setAdapter(adapter);

    }
}
