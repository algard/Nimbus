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
    private LayoutInflater inflater;

    public ChallengeListAdapter(Context context, int resource, List<Challenge> dataSet) {
        super(context, resource, dataSet);
        this.mItems = dataSet;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Challenge challenge = mItems.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.challenge_list_item, null);
        }

        TextView challengeName = (TextView)convertView.findViewById(R.id.challenge_title);
        TextView challengeTime = (TextView)convertView.findViewById(R.id.challenge_time);

        challengeName.setText(challenge.getName());
        challengeTime.setText(challenge.getEndTime());

        return convertView;
    }
}
