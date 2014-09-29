package planetexpress.nimbus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import planetexpress.nimbus.R;

public class ClientModeChallengeDetailsActivity extends Activity {
    public static final String EXTRA_CHALLENGE_ID = "challenge_id";

    private String mChallengeName = "500 Steps Challenge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent() != null){
            mChallengeName = getIntent().getStringExtra(EXTRA_CHALLENGE_ID);
        }
        if(mChallengeName == null){
            mChallengeName = "Steps Challenge!";
        }

        getActionBar().setTitle(mChallengeName);
        setContentView(R.layout.activity_client_mode_challenge_details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_mode_challenge_details, menu);
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
