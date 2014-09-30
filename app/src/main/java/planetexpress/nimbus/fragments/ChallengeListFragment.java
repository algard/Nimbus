package planetexpress.nimbus.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;
import planetexpress.nimbus.activities.ClientChallengeActivity;
import planetexpress.nimbus.adapters.ChallengeListAdapter;

/**
 */
public class ChallengeListFragment extends ListFragment {
    private OnFragmentInteractionListener mListener;

    private static final String ARG_CLIENT_ID = "clientID";
    private String mClientName;

    private ArrayList<Challenge> mUserChallenges;
    private ChallengeListAdapter mChallengeListAdapter;

    public ChallengeListFragment() {
    }


    // TODO: Rename and change types of parameters
    public static ChallengeListFragment newInstance(String clientID) {
        ChallengeListFragment fragment = new ChallengeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CLIENT_ID, clientID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClientName = getArguments().getString(ARG_CLIENT_ID);
        }

        mUserChallenges = new ArrayList<>();
        boolean isClientMode = mClientName != null;
        mChallengeListAdapter = new ChallengeListAdapter(getActivity(), android.R.id.text1, mUserChallenges, isClientMode, mClientName);
        setListAdapter(mChallengeListAdapter);

        getChallenges();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.challenge_list_fragment, null);
        //HERE BE INJECTION!!!
        ButterKnife.inject(this, view);


        return view;
    }


    private void getChallenges(){
        final MindbodyRepository repo = new MindbodyRepository(getActivity());
        if(mClientName != null) {
            repo.getChallengesForUser(mClientName, new MindbodyRepository.ChallengeIDsListener() {
                @Override
                public void onData(final ArrayList<String> challengeNames) {
                    repo.getAllChallenges(new MindbodyRepository.ChallengeDataListener() {
                        @Override
                        public void onData(ArrayList<Challenge> result) {
                            ArrayList<Challenge> filtered = new ArrayList<Challenge>();
                            for (Challenge chlg : result) {
                                for (String challengeName : challengeNames) {
                                    if (challengeName.equals(chlg.getName())) {
                                        filtered.add(chlg);
                                    }
                                }
                            }
                            mUserChallenges.addAll(filtered);
                            mChallengeListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {

                        }
                    });
                    mChallengeListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError() {

                }
            });
        }else{
            repo.getAllChallenges(new MindbodyRepository.ChallengeDataListener() {
                @Override
                public void onData(ArrayList<Challenge> result) {
                    mUserChallenges.addAll(result);
                    mChallengeListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError() {

                }
            });
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onChallengeSelected(mUserChallenges.get(position).getName());
        }
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onChallengeSelected(String name);
    }

    public ArrayList<Challenge> getUserChallenges() {
        return mUserChallenges;
    }

    public void setSteps(int id, int steps) {
        for (Challenge challenge : mUserChallenges) {
            if (Integer.valueOf(challenge.getId()) == id) {
                challenge.setCurrentSteps(steps);
                break;
            }
        }
    }

    public void setCalories(int id, int calories) {
        for (Challenge challenge : mUserChallenges) {
            if (Integer.valueOf(challenge.getId()) == id) {
                challenge.setCurrentCalories(calories);
                break;
            }
        }
    }
}
