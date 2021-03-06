package planetexpress.nimbus.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.jawbone.upplatformsdk.api.ApiManager;
import com.jawbone.upplatformsdk.api.response.OauthAccessTokenResponse;
import com.jawbone.upplatformsdk.oauth.OauthUtils;
import com.jawbone.upplatformsdk.oauth.OauthWebViewActivity;
import com.jawbone.upplatformsdk.utils.UpPlatformSdkConstants;
import com.parse.PushService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.Client;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;

import planetexpress.nimbus.fragments.ChallengeListFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ClientChallengeActivity extends Activity implements ChallengeListFragment.OnFragmentInteractionListener {
    private static final String TAG = "jibbityJawbone";
    private static final String JAWBONE_CLIENT_ID = "340xbpM7UNY";
    private static final String JAWBONE_CLIENT_SECRET = "9a2148dc6e935d0c40cac7af5bd47f089e301a50";

    // This has to be identical to the OAuth redirect url setup in Jawbone Developer Portal
    private static final String OAUTH_CALLBACK_URL = "up-platform://redirect";

    private ArrayList<UpPlatformSdkConstants.UpPlatformAuthScope> authScope;

    private String accessToken;
    private String refreshToken;

    //TODO get this value from the user
    private String mClientID = "david";
    private String mClientParseID;
    private ChallengeListFragment mFragment;

    public class StepData {
        String id;
        int totalSteps;
        int totalCalories;
    }

    private ArrayList<StepData> stepData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(getString(R.string.client_mode_title));
        setContentView(R.layout.activity_client_challenge);

        // Set required levels of permissions here, for demonstration purpose
        // we are requesting all permissions
        authScope  = new ArrayList<UpPlatformSdkConstants.UpPlatformAuthScope>();
        authScope.add(UpPlatformSdkConstants.UpPlatformAuthScope.ALL);

        //TODO Matt - this button was hidden, moved to the actionbar, handled in OnOptionItemClicked() down below
//        Button oAuthAuthorizeButton = (Button) findViewById(R.id.authorizeButton);
//        oAuthAuthorizeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        //PushService.subscribe(this, "NimbusChallengeChannel", ClientChallengeActivity.class);
        PushService.subscribe(this, "ChallengeCreated", ClientChallengeActivity.class);

        //TODO setup clientID / jawbone interaction first, as a sort of FTU-style popup?

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ClientChallengeActivity.this);
        accessToken = preferences.getString(UpPlatformSdkConstants.UP_PLATFORM_ACCESS_TOKEN, "");
        refreshToken = preferences.getString(UpPlatformSdkConstants.UP_PLATFORM_REFRESH_TOKEN, "");

        if (!accessToken.isEmpty()) {
            ApiManager.getRequestInterceptor().setAccessToken(accessToken);
            pullStepData();
        }

        stepData = new ArrayList<StepData>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UpPlatformSdkConstants.JAWBONE_AUTHORIZE_REQUEST_CODE && resultCode == RESULT_OK) {

            String code = data.getStringExtra(UpPlatformSdkConstants.ACCESS_CODE);
            if (code != null) {
                //first clear older accessToken, if it exists..
                ApiManager.getRequestInterceptor().clearAccessToken();

                ApiManager.getRestApiInterface().getAccessToken(
                        JAWBONE_CLIENT_ID,
                        JAWBONE_CLIENT_SECRET,
                        code,
                        accessTokenRequestListener);

                //TODO @matt - maybe we can show the list fragment?
            }
        }
    }

    private Callback accessTokenRequestListener = new Callback<OauthAccessTokenResponse>() {
        @Override
        public void success(OauthAccessTokenResponse result, Response response) {

            if (result.access_token != null) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ClientChallengeActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(UpPlatformSdkConstants.UP_PLATFORM_ACCESS_TOKEN, result.access_token);
                editor.putString(UpPlatformSdkConstants.UP_PLATFORM_REFRESH_TOKEN, result.refresh_token);
                editor.commit();

                ApiManager.getRequestInterceptor().setAccessToken(result.access_token);

                pullStepData();

                Log.e(TAG, "accessToken:" + result.access_token);
            } else {
                Log.e(TAG, "accessToken not returned by Oauth call, exiting...");
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.e(TAG, "failed to get accessToken:" + retrofitError.getMessage());
        }
    };

    private Intent getIntentForWebView() {
        Uri.Builder builder = OauthUtils.setOauthParameters(JAWBONE_CLIENT_ID, OAUTH_CALLBACK_URL, authScope);

        Intent intent = new Intent(this, OauthWebViewActivity.class);
        intent.putExtra(UpPlatformSdkConstants.AUTH_URI, builder.build());
        return intent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_challenge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_jawbone) {
            Intent intent = getIntentForWebView();
            startActivityForResult(intent, UpPlatformSdkConstants.JAWBONE_AUTHORIZE_REQUEST_CODE);
            return true;
        }else if(id == R.id.action_username){
            //TODO set username somehow
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mClientID = input.getText().toString();
                    MindbodyRepository repo = new MindbodyRepository(ClientChallengeActivity.this);
                    repo.getAllClients(new MindbodyRepository.ClientDataListener() {
                        @Override
                        public void onData(ArrayList<Client> result) {
                            for(Client client : result){
                                if(client.getName().equals(mClientID)){
                                    mClientParseID = client.getName();
                                }
                            }
                            if(mClientParseID != null) {
                                mFragment = ChallengeListFragment.newInstance(mClientParseID);
                                getFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();
                                pullStepData();
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                    //TODO maybe *now* we can show the fragment?
                    // donno how to wait on Jawbone finishing ....
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            return true;
        } else if (id == R.id.action_refresh) {
            pullStepData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChallengeSelected(String id) {
        Toast.makeText(this,"Challenge Clicked: "+id, Toast.LENGTH_SHORT).show();
        Intent challengeDetails = new Intent(this, ClientModeChallengeDetailsActivity.class);
        challengeDetails.putExtra(ClientModeChallengeDetailsActivity.EXTRA_CHALLENGE_ID, id);
        startActivity(challengeDetails);
    }

    private void refreshViews() {
        if (mFragment != null) {
            for (StepData steps : stepData) {
                mFragment.setSteps(steps.id, steps.totalSteps);
                mFragment.setCalories(steps.id, steps.totalCalories);
            }
        }
    }

    private void pullStepData() {
        if (mFragment != null) {
            ArrayList<Challenge> challenges = mFragment.getUserChallenges();
            if (challenges != null) {
                for (Challenge challenge : challenges) {
                    pullStepData(challenge.getId(), challenge.getStartDate(), challenge.getEndDate());
                }
            }
        }
    }

    private void pullStepData(final String id, Calendar startDate, Calendar endDate) {
        ApiManager.getRestApiInterface().getMoveEventsList(UpPlatformSdkConstants.API_VERSION_STRING, getMoveEventsListRequestParams(startDate, endDate), new Callback<Object>() {
            @Override
            public void success(Object o, Response response) {
                Log.e(TAG, "api call successful, json output: " + o.toString());
                processStepData(id, o);
                refreshViews();
                //Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, "api call failed, error message: " + retrofitError.getMessage());
                Toast.makeText(getApplicationContext(), retrofitError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private static HashMap<String, Integer> getMoveEventsListRequestParams(Calendar startDate, Calendar endDate) {
        HashMap<String, Integer> queryHashMap = new HashMap<String, Integer>();
        SimpleDateFormat date = new SimpleDateFormat("yyyymmdd");

        //uncomment to add as needed parameters
//        queryHashMap.put("date", Integer.valueOf(date.format(startDate.getTime())));
//      queryHashMap.put("page_token", "<insert-page-token>");
//        queryHashMap.put("start_time", startDate.getTimeInMillis());
//        queryHashMap.put("end_time", endDate.getTimeInMillis());
//      queryHashMap.put("updated_after", "<insert-time>");

        return queryHashMap;
    }

    private void processStepData(String id, Object o) {
        int totalSteps = 0;
        int totalCalories = 0;

        try {
            JSONObject obj = new JSONObject(o.toString());
            JSONArray items = obj.getJSONObject("data").getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                totalSteps += items.getJSONObject(i).getInt("steps");
                totalCalories += items.getJSONObject(i).getInt("calories");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StepData steps = new StepData();
        steps.id = id;
        steps.totalSteps = totalSteps;
        steps.totalCalories = totalCalories;

        stepData.add(steps);
    }
}
