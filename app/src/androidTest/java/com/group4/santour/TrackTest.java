package com.group4.santour;

import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by ibraa on 04-Dec-17.
 */

@RunWith(AndroidJUnit4.class)
public class TrackTest {
    @Test
    public void testCurrentLocationIsNotNull() throws Exception
    {
        CreateTrack ct = new CreateTrack();

        LatLng test = ct.getCoordinates();

        assertNotNull(test);
    }

    @Test
    public void testReceivesPictureTaken() throws Exception{

        CreateTrack ct = new CreateTrack();

        EditText trackname = ct.findViewById(R.id.editText);

        assertNotNull(trackname);

    }
}
