package planetexpress.nimbus.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.PushService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import planetexpress.nimbus.Client;
import planetexpress.nimbus.R;
import planetexpress.nimbus.StaticInstance;

public class StartActivity extends Activity {
    private Button mStaffSideButton;
    private Button mClientSideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mStaffSideButton = (Button) findViewById(R.id.staff_side_button);
        mClientSideButton = (Button) findViewById(R.id.client_side_button);

        mStaffSideButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ChallengeSetup.class);
                StaticInstance.mIsStaff = true;
                startActivity(intent);
            }
        });

        mClientSideButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Client");
                //TODO: For reality -- create a client based off of Name given
                //Hard-Coded to Reece Engle
                try {
                    ParseObject returned = query.get("1wue2MwxEQ");
                    if(returned == null) {
                        Log.e(StartActivity.class.getSimpleName(),
                                "Something went wrong when retrieving hard-coded Reece client object.");
                        return;
                    } else {
                        StaticInstance.gClientLoggedIn = (Client)Client.fromParseObject(returned);
                        Intent intent = new Intent(StartActivity.this, ClientChallengeActivity.class);
                        startActivity(intent);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}



