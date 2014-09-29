package planetexpress.nimbus.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
    @InjectView(R.id.addClientsButton) protected Button addClientsButton;
    @InjectView(R.id.numberOfStepsSpinner) protected Spinner numberOfStepsSpinner;
    @InjectView(R.id.rewardSpinner) protected Spinner rewardSpinner;
    @InjectView(R.id.typeOfChallengeSpinner) protected Spinner typeOfChallengeSpinner;

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
