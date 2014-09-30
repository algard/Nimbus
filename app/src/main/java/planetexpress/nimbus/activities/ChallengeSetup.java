package planetexpress.nimbus.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.OnClick;
import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.fragments.ChallengeDetailsFragment;
import planetexpress.nimbus.fragments.ChallengeListFragment;


public class ChallengeSetup extends Activity implements ChallengeListFragment.OnFragmentInteractionListener, ChallengeDetailsFragment.OnFragmentInteractionListener {
    private static final String TAG = "ChallengeSetupActivity";

    @InjectView(R.id.fabbutton) protected Button fabButton;
    @InjectView(R.id.ongoing_view_selector) protected TextView ongoingTab;
    @InjectView(R.id.addnew_view_selector) protected TextView addNewTab;

    @OnClick(R.id.ongoing_view_selector)
    public void selectOngoing() {
        ongoingTab.setActivated(true);
        addNewTab.setActivated(false);
        fabButton.setVisibility(View.VISIBLE);
        setTitle("Ongoing Challenges");
        loadChallengeList();
    }

    @OnClick(R.id.addnew_view_selector)
    public void selectAddNew() {
        ongoingTab.setActivated(false);
        addNewTab.setActivated(true);
        onAddButtonPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_setup);
        getActionBar().setHomeButtonEnabled(true);
        ButterKnife.inject(this);
        ParseAnalytics.trackAppOpened(getIntent());
        ongoingTab.setActivated(true);
        addNewTab.setActivated(false);
        setTitle("Ongoing Challenges");
        fabButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                    onAddButtonPressed();
            }
        });

        /* Sets up the fab button outlines and shape */
        Outline mOutlineCircle;
        int shapeSize = getResources().getDimensionPixelSize(R.dimen.fab_button_size);
        mOutlineCircle = new Outline();
        mOutlineCircle.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);

        fabButton.setOutline(mOutlineCircle);
        fabButton.setClipToOutline(true);

        //start with ongoing tab loaded
        selectOngoing();
    }

    private void onAddButtonPressed() {
        fabButton.setVisibility(View.GONE);
        ongoingTab.setActivated(false);
        addNewTab.setActivated(true);
        Fragment detailsFragment = new ChallengeDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailsFragment);
        transaction.commit();
    }

    private void loadChallengeList(){
        fabButton.setVisibility(View.VISIBLE);
        Fragment listFragment = ChallengeListFragment.newInstance(null);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, listFragment);
        transaction.commit();
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

    @Override
    public void onChallengeSelected(String id) {
        //TODO intent to ChallengeDetailsFragment ... etc
        Toast.makeText(this, "Challenge Clicked: " + id, Toast.LENGTH_SHORT).show();

        Intent rosterIntent = new Intent(this, ChallengeRosterActivity.class);
        startActivity(rosterIntent);
    }

    @Override
    public void onStartChallenge() {
        selectOngoing();
    }
}
