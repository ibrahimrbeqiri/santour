package com.group4.santour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.TestAdapter;
import models.JournalEntry;

public class MainActivity extends AppCompatActivity {

    private AppCompatActivity activity = MainActivity.this;
    private ListView listViewTest;
    private DatabaseReference journalCloudEndPoint;
    private DatabaseReference mDatabase;
    private List<JournalEntry> mJournalEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call List view
        listViewTest = findViewById(R.id.listViewTest);

        //Get database instance
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Create "child" in the database
        journalCloudEndPoint = mDatabase.child("journalentris");

        //addInitialDataToFirebase();

        getDataFromFirebase();

    }

        //method to get data from Firebase
        public void getDataFromFirebase(){

            journalCloudEndPoint.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                        JournalEntry note = noteSnapshot.getValue(JournalEntry.class);
                        mJournalEntries.add(note);
                        //Log.d("test", note.getTitle()+" "+note.getContent());
                    }

                    TestAdapter adapter = new TestAdapter(activity, mJournalEntries);
                    listViewTest.setAdapter(adapter);
                    //adapter.addAll(mJournalEntries);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("Error", databaseError.getMessage());
                }
            });

        }

        //method to send data to Firebase
        public void addInitialDataToFirebase() {

            List<JournalEntry> sampleJournalEntries = getSampleJournalEntries();

            for (JournalEntry journalEntry: sampleJournalEntries){
                String key = journalCloudEndPoint.push().getKey();
                journalEntry.setJournalId(key);
                journalCloudEndPoint.child(key).setValue(journalEntry);
            }

        }

        //method to add data manually
        public List<JournalEntry> getSampleJournalEntries() {

            List<JournalEntry> journalEnrties = new ArrayList<>();
            //create the dummy journal
            JournalEntry journalEntry1 = new JournalEntry();
            journalEntry1.setTitle("Step 1");
            journalEntry1.setContent("Yo, this is the first step");
            journalEnrties.add(journalEntry1);

            return journalEnrties;

        }

    }


