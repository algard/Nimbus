package planetexpress.nimbus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.R;

public class ChallengeListAdapter extends ArrayAdapter<Challenge> {
    private final List<Challenge> mItems;
    private final boolean mIsClientMode;
    private LayoutInflater inflater;

    public ChallengeListAdapter(Context context, int resource, List<Challenge> dataSet, boolean isClientMode) {
        super(context, resource, dataSet);
        this.mItems = dataSet;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mIsClientMode = isClientMode;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Challenge challenge = mItems.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.challenge_list_item, null);
        }

        TextView challengeName = (TextView)convertView.findViewById(R.id.challenge_title);
        TextView challengeTime = (TextView)convertView.findViewById(R.id.time_remaining);

        if(mIsClientMode){
            (convertView.findViewById(R.id.roster_button)).setVisibility(View.GONE);
            (convertView.findViewById(R.id.edit_button)).setVisibility(View.GONE);

            (convertView.findViewById(R.id.roster_button)).setVisibility(View.GONE);
            (convertView.findViewById(R.id.edit_button)).setVisibility(View.GONE);

        }

        challengeName.setText(challenge.getName());
        challengeTime.setText("5 days remaining");

        return convertView;
    }
}
