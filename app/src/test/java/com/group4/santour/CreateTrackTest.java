package com.group4.santour;

import android.view.View;
import android.widget.EditText;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Joey on 04.12.2017.
 */

public class CreateTrackTest {

    @Test
    public void receivesPictureTaken() throws Exception{

        CreateTrack ct = new CreateTrack();

        EditText trackname = ct.findViewById(R.id.editText);

        Assert.assertNotNull(trackname);

    }
    
}
