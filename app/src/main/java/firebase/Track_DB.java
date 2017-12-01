package firebase;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group4.santour.R;

import java.util.ArrayList;
import java.util.List;

import adapters.ListAdapter;
import models.GPSData;
import models.POD;
import models.POI;
import models.Track;

/**
 * Created by Emile on 24.11.2017
 */

public class Track_DB {

    private AppCompatActivity activity;
    private DatabaseReference sanTourDatabase;
    private DatabaseReference trackCloudEndPoint;
    private ListView listViewTrack;
    private List<Track> tracks;


    public Track_DB(AppCompatActivity activity){
        sanTourDatabase =  FirebaseDatabase.getInstance().getReference();
        trackCloudEndPoint = sanTourDatabase.child("track");
        tracks = new ArrayList<>();
        listViewTrack = activity.findViewById(R.id.listViewTest);
        this.activity=activity;
    }



    //method to get data from Firebase
    public void getDataFromFirebase(){

        trackCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    Track track = noteSnapshot.getValue(Track.class);
                    tracks.add(track);
                    Log.d("*** test track", track.getNameTrack()+" "+track.getDescriptionTrack());
                }

                ListAdapter adapter = new ListAdapter(activity, tracks);
                listViewTrack.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.getMessage());
            }
        });

    }



    //send data to Firebase
    public void addInitialDataToFirebase() {

        //reset data
        trackCloudEndPoint.setValue("sanTour");

        List<Track> tracks = getTracks();

        for (Track track: tracks){
            String key = trackCloudEndPoint.push().getKey();
            track.setIdTrack(key);
            trackCloudEndPoint.child(key).setValue(track);
        }

    }

    //test method to add data manually
    public List<Track> getTracks() {

        List<Track> tracks = new ArrayList<>();

        GPSData poi_1_GPS = new GPSData("40.446","79.982");
        POI poi1 = new POI("POI river", "beautiful river", "river.jpg", poi_1_GPS);

        GPSData poi_2_GPS = new GPSData("40.442","79.988");
        POI poi2 = new POI("POI blond girl", "beautiful blond girl", "blondGirl.jpg", poi_2_GPS);

        GPSData pod_1_GPS = new GPSData("40.456","79.992");
        POD pod1 = new POD("POD steep", "steep track", "steepTrack.jpg", pod_1_GPS);

        Track track1 = new Track();
        track1.setNameTrack("Track 1");
        track1.setDescriptionTrack("Yo, this is the first track");
        track1.setPoiTrack(poi1);
        track1.setPoiTrack(poi2);
        track1.setPodTrack(pod1);
        tracks.add(track1);

        Track track2 = new Track();
        track2.setNameTrack("Track 2");
        track2.setDescriptionTrack("Ho, this is the second track");
        tracks.add(track2);

        Track track3 = new Track();
        track3.setNameTrack("Track 3");
        track3.setDescriptionTrack("hey, this is the third track");
        tracks.add(track3);


        return tracks;

    }

}
