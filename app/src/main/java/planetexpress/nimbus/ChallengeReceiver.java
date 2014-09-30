package planetexpress.nimbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import planetexpress.nimbus.activities.ClientChallengeActivity;

public class ChallengeReceiver extends BroadcastReceiver {
    private static final String TAG = "ChallengeReceiver";

    public ChallengeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean found = false;
        //Ignore if you are a staff
        if(StaticInstance.mIsStaff) return;
        try {
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
            Iterator itr = json.keys();
            while (itr.hasNext()) {
                String key = (String) itr.next();
                if(key.contains("objectId")) {
                   if(StaticInstance.gClientLoggedIn.equals(json.getString(key))) {
                        found = true;
                    }
                }
                Log.d(TAG, "..." + key + " => " + json.getString(key));
                if(!found) return;
            }

        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }
}
