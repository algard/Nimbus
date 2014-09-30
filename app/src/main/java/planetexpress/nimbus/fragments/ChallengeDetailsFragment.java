package planetexpress.nimbus.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import planetexpress.nimbus.MindbodyRepository;
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
    @InjectView(R.id.startDateSpinner) protected Spinner startDateSpinner;
    @InjectView(R.id.startTimeSpinner) protected Spinner startTimeSpinner;
    @InjectView(R.id.endDateSpinner) protected Spinner endDateSpinner;
    @InjectView(R.id.endTimeSpinner) protected Spinner endTimeSpinner;
    @InjectView(R.id.startChallengeButton) protected Button startChallenge;


    private ArrayAdapter<String> mChallengeTypeArrayAdapter;
    private ArrayAdapter<String> mNumberOfStepsArrayAdapter;
    private ArrayAdapter<String> mStartDatesArrayAdapter;
    private ArrayAdapter<String> mStartTimesArrayAdapter;
    private ArrayAdapter<String> mEndDatesArrayAdapter;
    private ArrayAdapter<String> mEndTimesArrayAdapter;
    private ArrayAdapter<String> mRewardsArrayAdapter;

    private ArrayList<String> mChallengeTypeOptions= new ArrayList<String>();
    private ArrayList<String> mNumberOfSteps= new ArrayList<String>();
    private ArrayList<String> mStartDates= new ArrayList<String>();
    private ArrayList<String> mStartTimes= new ArrayList<String>();
    private ArrayList<String> mEndDates= new ArrayList<String>();
    private ArrayList<String> mEndTimes= new ArrayList<String>();
    private ArrayList<String> mRewards= new ArrayList<String>();

    private ArrayList<Client> clientList = new ArrayList<Client>();


    private MindbodyRepository rep = new MindbodyRepository(getActivity());


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
                                    + "\", \"objectId\": \"1wue2MwxEQ\" }");
//                                    +"\", \"objectId\": \"" + createChallenge.getObjectId() + "\" }");

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

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        setupSpinners();
    }

    private void setupSpinners() {
        mChallengeTypeOptions.add("Weekly Steps");
        mChallengeTypeOptions.add("Monthly Steps");
        mChallengeTypeArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mChallengeTypeOptions);
        mChallengeTypeArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        typeOfChallengeSpinner.setAdapter(mChallengeTypeArrayAdapter);


        mNumberOfSteps.add("10,000");
        mNumberOfSteps.add("20,000");
        mNumberOfSteps.add("40,000");
        mNumberOfStepsArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mNumberOfSteps);
        mNumberOfStepsArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        numberOfStepsSpinner.setAdapter(mNumberOfStepsArrayAdapter);

        mStartDates.add("October 1, 2014");
        mStartDates.add("October 2, 2014");
        mStartDates.add("October 3, 2014");
        mStartDatesArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mStartDates);
        mStartDatesArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        startDateSpinner.setAdapter(mStartDatesArrayAdapter);

        mStartTimes.add("8:00 AM");
        mStartTimesArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mStartTimes);
        mStartTimesArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        startTimeSpinner.setAdapter(mStartTimesArrayAdapter);

        mEndDates.add("October 8, 2014");
        mEndDates.add("October 31, 2014");
        mEndDatesArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mEndDates);
        mEndDatesArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        endDateSpinner.setAdapter(mEndDatesArrayAdapter);

        mEndTimes.add("10:00 PM");
        mEndTimesArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mEndTimes);
        mEndTimesArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        endTimeSpinner.setAdapter(mEndTimesArrayAdapter);

        mRewards.add("Free Class");
        mRewards.add("Free Friend Pass");
        mRewards.add("Free T-Shirt");
        mRewardsArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, mRewards);
        mRewardsArrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        rewardSpinner.setAdapter(mRewardsArrayAdapter);
    }

    private void showAddClientsDialog(View view) {
        rep.getAllClients(new MindbodyRepository.ClientDataListener() {
            @Override
            public void onData(ArrayList<Client> result) {
                clientList = result;
            }

            @Override
            public void onError() {

            }
        });
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
