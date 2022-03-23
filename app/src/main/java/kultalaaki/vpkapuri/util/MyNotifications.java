/*
 * Created by Kultala Aki on 16/8/2022
 * Copyright (c) 2022. All rights reserved.
 * Last modified 16/8/2022
 */

package kultalaaki.vpkapuri.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import kultalaaki.vpkapuri.FrontpageActivity;
import kultalaaki.vpkapuri.R;
import android.Manifest;
/**
 * Use this to show notification to user.
 */
public class MyNotifications {

    private static final int REQUEST_CODE = 1; // Add this line

    private final Context context;

    public MyNotifications(Context context) {
        this.context = context;
    }

    /**
     * Informational notification to user
     *
     * @param content Text to inform user what went wrong.
     */
    public void showInformationNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_INFORMATION)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("VPK Apuri")
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notifactionManager = NotificationManagerCompat.from(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, display the notification
            notifactionManager.notify(Constants.INFORMATION_NOTIFICATION_ID, builder.build());
        } else {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.VIBRATE}, REQUEST_CODE);
        }
    }

    /**
     * Meant to show what sound profile user has set
     * @param content set content to show user
     */
    public void showSoundProfileNotification(String content) {
        Intent openFrontPage = new Intent(context, FrontpageActivity.class);
        PendingIntent pendingOpenFrontPage = PendingIntent.getActivity(context, 0, openFrontPage, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_SILENCE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("VPK Apuri")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingOpenFrontPage)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setAutoCancel(false);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, display the notification
            notificationManager.notify(Constants.NOTIFICATION_ID, mBuilder.build());
        } else {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.VIBRATE}, REQUEST_CODE);
        }
    }
}
