package com.group4.santour;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ibraa on 04-Dec-17.
 */

public class CoordinatesTest {
    @Test
    public void retrievingCoordinates() throws Exception
    {
        CreateTrack createTrack = new CreateTrack();
        LatLng myCoordinates = createTrack.getMyCoordinates();

        LatLng testCoordinates = new LatLng(4.44, 6.55);

        Assert.assertNotEquals(myCoordinates, testCoordinates);



    }

}
