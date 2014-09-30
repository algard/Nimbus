package planetexpress.nimbus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import planetexpress.nimbus.Client;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;
import planetexpress.nimbus.data.ClientsInChallenge;

public class ChallengeRosterActivity extends Activity {

    private String mChallengeName;
    public static final String EXTRA_NAME = "challenge_name";
    MindbodyRepository mbRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_roster);

        if(getIntent() != null){
            mChallengeName = getIntent().getStringExtra(EXTRA_NAME);
        }
        if(mChallengeName == null){
            mChallengeName = "Challenge";
        }

        getActionBar().setTitle(mChallengeName+" Roster");
        mbRepo =  new MindbodyRepository(this);
        mbRepo.getClientsInChallenge(mChallengeName, new MindbodyRepository.ClientsInChallengeListener() {
            @Override
            public void onData(ArrayList<ClientsInChallenge> result) {
                Log.d("TAG", "Size: "+result.size());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.challenge_roster, menu);
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
