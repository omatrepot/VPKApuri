package kultalaaki.vpkapuri.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHandler {
    private final Activity activity;
    private static final String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            "com.google.android.gms.auth.api.phone.permission.SMS_RETRIEVE",
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int MY_PERMISSIONS_REQUEST = 1;

    public PermissionHandler(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissions() {
        boolean allPermissionsGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }
        return allPermissionsGranted;
    }


    public void requestPermissions() {
        ActivityCompat.requestPermissions(activity, permissions, MY_PERMISSIONS_REQUEST);
    }
}