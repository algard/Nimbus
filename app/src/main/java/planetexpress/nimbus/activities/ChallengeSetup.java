package planetexpress.nimbus.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.fragments.ChallengeDetailsFragment;


public class ChallengeSetup extends Activity {

    private static final String TAG = "ChallengeSetupActivity";

    @InjectView(R.id.fabbutton) protected Button fabButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_setup);
        ButterKnife.inject(this);
        ParseAnalytics.trackAppOpened(getIntent());

        fabButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                ParsePush push = new ParsePush();
//
                JSONObject data = null;
                try {
                    data = new JSONObject("{\"action\": \"planetexpress.nimbus.UPDATE_STATUS\", \"name\": \"Test!!\" }");

                    push.setChannel("Test");
                    push.setData(data);
                    push.setMessage("Testing the test");
                    push.sendInBackground(new SendCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.d(TAG, "Message Done Sending -- Challenge Created");

                        }
                    });
                    onAddButtonPressed(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        /* Sets up the fab button outlines and shape */
        Outline mOutlineCircle;
        int shapeSize = getResources().getDimensionPixelSize(R.dimen.fab_button_size);
        mOutlineCircle = new Outline();
        mOutlineCircle.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);

        fabButton.setOutline(mOutlineCircle);
        fabButton.setClipToOutline(true);

        getChallenges();
    }

    private void onAddButtonPressed(View v) {
        Fragment detailsFragment = new ChallengeDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailsFragment);
        transaction.commit();
    }

    public void getChallenges(){
        MindbodyRepository repository = new MindbodyRepository(this);
        repository.getAllChallenges(new MindbodyRepository.ChallengeDataListener() {
            @Override
            public void onData(ArrayList<Challenge> result) {
                // do something with result
            }

            @Override
            public void onError() {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.challenge_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
