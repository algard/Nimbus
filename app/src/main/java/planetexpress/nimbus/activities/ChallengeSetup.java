package planetexpress.nimbus.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ChallengeSetup extends Activity {

    @InjectView(R.id.fabbutton) protected Button fabButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_setup);
        ButterKnife.inject(this);

        fabButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                onAddButtonPressed(v);
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
        Intent challengeDetailsIntent = new Intent(this, ChallengeDetailsActivity.class);
        startActivity(challengeDetailsIntent);
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
