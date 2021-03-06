package planetexpress.nimbus.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.Client;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;
import planetexpress.nimbus.data.ClientsInChallenge;

public class ChallengeRosterActivity extends Activity {

    private String mChallengeName;
    public static final String EXTRA_NAME = "challenge_name";
    MindbodyRepository mbRepo;
    @InjectView(R.id.participatingNumber)TextView numberAccepted;
    @InjectView(R.id.pendingNumber)TextView numberPending;
    @InjectView(R.id.participantsList)ListView participantsList;
    @InjectView(R.id.pendingList)ListView pendingList;

    private List<String> nameArrayList = new ArrayList<String>();
    private List<String> pendingArrayList = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_roster);
        ButterKnife.inject(this);

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
                int accepted = 0;
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).AcceptedChallenge) {
                        accepted++;
                        nameArrayList.add(result.get(i).ClientID);
                    }
                    else{
                        pendingArrayList.add(result.get(i).ClientID);
                    }
                }
                numberAccepted.setText(accepted + " Participating");
                numberPending.setText(result.size()-accepted + " Pending");
                ArrayAdapter<String> participantAdapter = new ArrayAdapter<String>(ChallengeRosterActivity.this, android.R.layout.simple_list_item_1, nameArrayList);
                ArrayAdapter<String> pendingAdapter = new ArrayAdapter<String>(ChallengeRosterActivity.this, android.R.layout.simple_list_item_1, pendingArrayList);
                participantsList.setAdapter(participantAdapter);
                pendingList.setAdapter(pendingAdapter);
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
