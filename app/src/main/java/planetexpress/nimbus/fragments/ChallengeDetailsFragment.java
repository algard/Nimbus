package planetexpress.nimbus.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.Client;
import planetexpress.nimbus.R;
import planetexpress.nimbus.WireTaskCallback;
import planetexpress.nimbus.dialogfragments.SelectClientsDialog;

/**
 * Created by Lia.Zadoyan on 9/29/2014.
 */
public class ChallengeDetailsFragment extends Fragment {
    private static final String TAG = ChallengeDetailsFragment.class.getSimpleName();


    @InjectView(R.id.addClientsButton) protected Button addClientsButton;
    @InjectView(R.id.numberOfStepsSpinner) protected Spinner numberOfStepsSpinner;
    @InjectView(R.id.rewardSpinner) protected Spinner rewardSpinner;
    @InjectView(R.id.typeOfChallengeSpinner) protected Spinner typeOfChallengeSpinner;
    @InjectView(R.id.startChallengeButton) protected Button startChallenge;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_challenge_details, null);
        ButterKnife.inject(this, rootView);
        getActivity().setTitle("Challenge Details");

        addClientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddClientsDialog(view);
            }
        });

        startChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseObject createChallenge = new ParseObject("Challenge");
                createChallenge.put("Name", "Sean Plott");
                createChallenge.put("Description", "This is just a test");
                //Create new challenge
                createChallenge.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        //Once challenge is done Push to all Clients
                        ParsePush push = new ParsePush();
                        JSONObject data = null;
                        try {
                            //Create challenge object here -- pass forward it's ID
                            data = new JSONObject("{\"action\": \"planetexpress.nimbus.CREATE_CHALLENGE\"," +
                                    " \"name\": \"" + createChallenge.get("Name")
                                    +"\", \"objectId\": \"" + createChallenge.getObjectId() + "\" }");

                            push.setChannel("ChallengeCreated");
                            push.setData(data);
                            push.setMessage("You have been challenged, can you conquer today's \'"
                                            + createChallenge.getString("Name") + "' challenge!?");
                            push.sendInBackground(new SendCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Log.d(TAG, "Challenge Created");

                                }
                            });
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                });


            }
        });

        return rootView;
    }

    private void showAddClientsDialog(View view) {
        ArrayList<Client> clientList = new ArrayList<>();
        SelectClientsDialog selectClientsDialog = new SelectClientsDialog(clientList, new WireTaskCallback<ArrayList<Client>>() {
            @Override
            public void onFinished(ArrayList<Client> result) {

            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        });
        selectClientsDialog.show(getFragmentManager(), null);
    }
}
