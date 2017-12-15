package firebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import models.Track;

public class FirebaseQueries {

    private AppCompatActivity activity;
    private DatabaseReference sanTourDatabase;
    private DatabaseReference trackCloudEndPoint;
    private List<Track> tracksList;
    private StorageReference sanTourStorage;
    private FirebaseDatabase firebaseDatabase;

    public FirebaseQueries(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);


        //get database instance and reference
        sanTourDatabase = firebaseDatabase.getReference();

        //get storage instance and reference
        sanTourStorage = FirebaseStorage.getInstance().getReference();

        //list of tracks
        tracksList = new ArrayList<>();

    }

    //add a new track to the list
    public void insertTrack(Track track) {

        //reset data
        //sanTourDatabase.setValue("sanTour");

        trackCloudEndPoint = sanTourDatabase.child("tracks");
        tracksList.add(track);
        String key = trackCloudEndPoint.push().getKey();
        track.setIdTrack(key);
        trackCloudEndPoint.child(key).setValue(track);

    }

    //add picture to Firebase storage to check if file is correctly stored
    public void insertPicture(String image){

        //Decode the string value to a bitmap file
        Bitmap bitmap = null;
        try {
            bitmap = decodeFromBase64(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Create a reference to the image file using the encoded string value
        StorageReference mountainsRef = sanTourStorage.child(date+":"+month+":"+year+": "+hour+":"+minute+":"+second);

        // Get the data from bitmap as bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    //method to decode a string value to bitmap
    public Bitmap decodeFromBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    //method to encode bitmap to string
    public String encodeToBase64(Bitmap bitmap) {

        String imageString;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        return imageString;

    }
}
