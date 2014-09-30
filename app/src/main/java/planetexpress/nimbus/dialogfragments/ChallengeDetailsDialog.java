package planetexpress.nimbus.dialogfragments;

/**
 * Created by Matt.Butlar on 9/30/2014.
 */

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.Challenge;
import planetexpress.nimbus.Client;
import planetexpress.nimbus.R;
import planetexpress.nimbus.WireTaskCallback;
import planetexpress.nimbus.util.CheckedRelativeLayout;

public class ChallengeDetailsDialog extends DialogFragment {
    private Challenge challenge;
    private Drawable bg;

    public ChallengeDetailsDialog() {
        super();
    }

    public ChallengeDetailsDialog(Challenge challenge, Drawable bg) {
        super();
        this.challenge = challenge;
        this.bg = bg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.challenge_item_details, container, false);
        ButterKnife.inject(this, v);

        ImageView img = (ImageView) v.findViewById(R.id.card_header_bg);
        img.setImageDrawable(bg);

        TextView title = (TextView) v.findViewById(R.id.challenge_title);
        title.setText(challenge.getName());
        TextView time = (TextView) v.findViewById(R.id.time_remaining);
        time.setText("5 days remaining");
        TextView progress = (TextView) v.findViewById(R.id.challenge_progress);
        progress.setText("Progress: " + challenge.getCurrentSteps() + "/" + challenge.getGoalSteps());
        TextView desc = (TextView) v.findViewById(R.id.challenge_description);
        desc.setText(challenge.getDescription());

        return v;
    }
}
