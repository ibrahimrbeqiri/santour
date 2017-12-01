package firebase;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import models.POI;
import models.Track;



public class FirebaseQueries {

    private AppCompatActivity activity;
    private DatabaseReference sanTourDatabase;
    private DatabaseReference trackCloudEndPoint;
    private List<Track> tracksList;


    public FirebaseQueries(){

        //get database reference
        sanTourDatabase =  FirebaseDatabase.getInstance().getReference();

        //list of tracks
        tracksList = new ArrayList<>();


    }

    //add a new track to the list
    public void insertTrack(Track track) {

        //reset data
        //sanTourDatabase.setValue("sanTour");

        trackCloudEndPoint = sanTourDatabase.child("track");
        tracksList.add(track);
        String key = trackCloudEndPoint.push().getKey();
        track.setIdTrack(key);
        trackCloudEndPoint.child(key).setValue(track);

    }


    //insert poi in a track
    public void insertPOI(POI poi, String path) {
        trackCloudEndPoint = sanTourDatabase.child(path);
        trackCloudEndPoint.setValue(poi);
    }

}
