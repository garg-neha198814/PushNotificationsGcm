package com.pushnotificationsgcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/*
* Registering Device in GCM for the first time
*/

/**
 * Created by root on 3/2/16.
 */
public class GCMNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9001;
    NotificationCompat.Builder builder;
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String GCM_TOKEN = "gcmToken";

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    String token;

    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Make a call to Instance API
        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
        String token = null;
        try {
            token = instanceID.getToken(getApplicationContext().getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Log.d(TAG, "Failed to complete token refresh", e);
        // If an exception happens while fetching the new token or updating our registration data
        // on a third-party server, this ensures that we'll attempt the update at a later time.
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();

        // save token
        sharedPreferences.edit().putString(GCM_TOKEN, token).apply();
        // pass along this data
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // send network request

        // if registration sent was successful, store a boolean that indicates whether the generated token has been sent to server
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();
    }
}


