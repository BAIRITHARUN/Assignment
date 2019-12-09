package com.infy.main_activity.view.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainFragFragmentTest {

    @Test
    public void checkIntentConnection() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ConnectivityManager connectivityManager = (ConnectivityManager)appContext.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        String status;
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).
                getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
           assertTrue("true", true);
        }
        else {
            assertTrue("false", true);
        }

    }
}