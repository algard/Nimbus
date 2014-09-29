package planetexpress.nimbus.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;
import planetexpress.nimbus.adapters.ChallengeListAdapter;

/**
 */
public class ChallengeListFragment extends ListFragment {
    private OnFragmentInteractionListener mListener;

    private static final String ARG_CLIENT_ID = "clientID";
    private String mClientID;

    private ArrayList<Challenge> mUserChallenges;
    private ChallengeListAdapter mChallengeListAdapter;


    // TODO: Rename and change types of parameters
    public static ChallengeListFragment newInstance(String clientID) {
        ChallengeListFragment fragment = new ChallengeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CLIENT_ID, clientID);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChallengeListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mClientID = getArguments().getString(ARG_CLIENT_ID);
        }

        //force it to get all challenges for now, TODO remove this line
        mClientID = null;

        mUserChallenges = new ArrayList<>();
        Challenge fake = new Challenge();
        fake.setName("Fake");
        fake.setId("aosdasdasd");
        mUserChallenges.add(fake);
        mChallengeListAdapter = new ChallengeListAdapter(getActivity(), android.R.id.text1, mUserChallenges);
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
        MindbodyRepository repo = new MindbodyRepository(getActivity());
        if(mClientID != null) {
            repo.getChallengesForUser(mClientID, new MindbodyRepository.ChallengeDataListener() {
                @Override
                public void onData(ArrayList<Challenge> result) {
                    mUserChallenges.addAll(result);
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
            mListener.onChallengeSelected(mUserChallenges.get(position).getId());
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
        public void onChallengeSelected(String id);
    }

}
