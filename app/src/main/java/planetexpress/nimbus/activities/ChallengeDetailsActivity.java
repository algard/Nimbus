package planetexpress.nimbus.activities;

import android.app.Activity;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.R;

/**
 * Created by Lia.Zadoyan on 9/29/2014.
 */
public class ChallengeDetailsActivity extends Activity {
    @InjectView(R.id.addClientsButton) protected Button addClientsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);
        ButterKnife.inject(this);
        setTitle("Challenge Details");

        addClientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "AddClients", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
