/*
 * Created by Kultala Aki on 9/7/2022
 * Copyright (c) 2022. All rights reserved.
 * Last modified 9/7/2022
 */

package kultalaaki.vpkapuri.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import kultalaaki.vpkapuri.util.FirebaseEventLogger;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class StopSMSBackgroundService extends BroadcastReceiver {

    private FirebaseEventLogger firebaseEventLogger;

    @Override
    public void onReceive(Context context, Intent intent) {

        firebaseEventLogger = new FirebaseEventLogger(context);

        try {
            Intent stopService = new Intent(context, SMSBackgroundService.class);
            context.stopService(stopService);
            firebaseEventLogger.logEvent("SERVICE_STOP_SUCCESS", "Service stopped successfully");
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            firebaseEventLogger.logEvent("SERVICE_STOP_ERROR", "Error while stopping service: " + e.getMessage());
        }
    }
}
