package planetexpress.nimbus.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import planetexpress.nimbus.Challenge;

public class ChallengeListAdapter extends ArrayAdapter<Challenge> {
    private final List<Challenge> mItems;

    public ChallengeListAdapter(Context context, int resource, List<Challenge> dataSet) {
        super(context, resource, dataSet);
        this.mItems = dataSet;
    }
}
