package kultalaaki.vpkapuri.util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseEventLogger {

    private FirebaseAnalytics firebaseAnalytics;

    public FirebaseEventLogger(Context context) {
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void logEvent(String eventName, String eventDescription) {
        Bundle params = new Bundle();
        params.putString("event_name", eventName);
        params.putString("event_description", eventDescription);
        firebaseAnalytics.logEvent("my_custom_event", params);
    }
}
