package com.pushnotificationsgcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/*
* If token is expired then this class is called
 */

/**
 * Created by root on 3/2/16.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, GCMNotificationIntentService.class);
        startService(intent);
    }
}
