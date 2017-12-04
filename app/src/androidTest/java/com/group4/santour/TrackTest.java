package com.group4.santour;

import android.support.test.rule.ActivityTestRule;
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

    public ActivityTestRule<CreateTrack> activityRule = new ActivityTestRule<>(CreateTrack.class);
    CreateTrack createTrack;

    @Test
    public void testCurrentLocationIsNotNull() throws Exception
    {

        LatLng test = createTrack.getCoordinates();

        assertNotNull(test);
    }

    @Test
    public void testReceivesPictureTaken() throws Exception{


        EditText trackname = createTrack.findViewById(R.id.editText);

        assertNotNull(trackname);

    }
}
