package firebase;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import models.POI;
import models.Track;



public class FirebaseQueries {

    private AppCompatActivity activity;
    private DatabaseReference sanTourDatabase;
    private DatabaseReference trackCloudEndPoint;


    public FirebaseQueries(){

        sanTourDatabase =  FirebaseDatabase.getInstance().getReference();


    }



    //send data to Firebase
    public void insertPOI(POI poi, String path) {
        trackCloudEndPoint = sanTourDatabase.child(path);
        trackCloudEndPoint.setValue(poi);
    }

}
