package planetexpress.nimbus.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.MindbodyRepository;
import planetexpress.nimbus.R;

public class ChallengeListAdapter extends ArrayAdapter<Challenge> {
    private final List<Challenge> mItems;
    private final boolean mIsClientMode;
    private final Context mContext;
    private LayoutInflater inflater;
    private final String clientName;

    public ChallengeListAdapter(Context context, int resource, List<Challenge> dataSet, boolean isClientMode, String clientName) {
        super(context, resource, dataSet);
        this.mContext = context;
        this.mItems = dataSet;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mIsClientMode = isClientMode;
        this.clientName = clientName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Challenge challenge = mItems.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.challenge_list_item, null);
        }

        final TextView challengeName = (TextView)convertView.findViewById(R.id.challenge_title);
        TextView challengeTime = (TextView)convertView.findViewById(R.id.time_remaining);

        ImageView headerImage = (ImageView)convertView.findViewById(R.id.card_header_bg);
        if(position % 3 == 0) {
            headerImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.card_1));
        }else if (position % 3 == 1){
            headerImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.card_2));
        }else{
            headerImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.card_3));
        }

        if(mIsClientMode){
            (convertView.findViewById(R.id.roster_button)).setVisibility(View.GONE);
            (convertView.findViewById(R.id.edit_button)).setVisibility(View.GONE);

            (convertView.findViewById(R.id.accept_button)).setVisibility(View.VISIBLE);
            (convertView.findViewById(R.id.share_button)).setVisibility(View.VISIBLE);
            (convertView.findViewById(R.id.more_info_button)).setVisibility(View.VISIBLE);

            Button acceptButton = (Button)convertView.findViewById(R.id.accept_button);
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final MindbodyRepository repo = new MindbodyRepository(getContext());
                    repo.acceptChallenge(clientName, challengeName.getText().toString());
                }
            });

        }else{
            (convertView.findViewById(R.id.roster_button)).setVisibility(View.VISIBLE);
            (convertView.findViewById(R.id.edit_button)).setVisibility(View.VISIBLE);

            (convertView.findViewById(R.id.accept_button)).setVisibility(View.GONE);
            (convertView.findViewById(R.id.share_button)).setVisibility(View.GONE);
            (convertView.findViewById(R.id.more_info_button)).setVisibility(View.GONE);
        }

        challengeName.setText(challenge.getName());
        challengeTime.setText("5 days remaining");


        return convertView;
    }
}
