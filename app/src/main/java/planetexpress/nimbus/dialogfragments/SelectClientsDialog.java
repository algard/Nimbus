package planetexpress.nimbus.dialogfragments;

/**
 * Created by Lia.Zadoyan on 9/29/2014.
 */

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import planetexpress.nimbus.Client;
import planetexpress.nimbus.R;
import planetexpress.nimbus.WireTaskCallback;
import planetexpress.nimbus.util.CheckedRelativeLayout;

public class SelectClientsDialog extends DialogFragment {

    //public static final int CHECKOUT_REQUEST_CODE = 0;
    //ClientRepository clientRepo;
    //Visit visit;
    private ArrayList<Client> clients;
    private int choiceMode;
    private WireTaskCallback<ArrayList<Client>> onSuccessCallback;
    private Runnable dialogCancelledCallback;
    ArrayAdapter<Client> adapter;
    @InjectView(R.id.allStaffCheckbox)
    CheckBox allStaffCheckbox;
    @InjectView(R.id.saveButton)
    Button submitButton;
    @InjectView(R.id.cancelButton) Button cancelButton;
    @InjectView(R.id.staffListView)
    ListView staffList;
    @InjectView(R.id.xButton) Button xButton;
    @InjectView(R.id.allStaffLabel)
    TextView allStaffLabel;
    @InjectView(R.id.topDiv)
    View topDivider;

    public SelectClientsDialog() {
        super();
        clients = new ArrayList<>();
    }

    public SelectClientsDialog(ArrayList<Client> client, WireTaskCallback<ArrayList<Client>> onSuccessCallback, Runnable dialogCancelledCallback) {
        this.clients = client;
        this.onSuccessCallback = onSuccessCallback;
        this.dialogCancelledCallback = dialogCancelledCallback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ArrayAdapter<Client>(getActivity(),android.R.layout.simple_list_item_multiple_choice, clients){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View newView = convertView;
                if (newView == null) {
                    LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    newView = li.inflate(R.layout.select_clients_list_item, null);
                }
                ((TextView)newView.findViewById(R.id.staffNameLabel)).setText(getItem(position).getName());

                newView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toggle, if it is checked uncheck it
                        if(((CheckedRelativeLayout)v).isChecked()) {
                            staffList.setItemChecked((Integer) v.getTag(), false);
                            allStaffCheckbox.setChecked(false);
                        }
                        else{
                            staffList.setItemChecked((Integer) v.getTag(), true);
                            if(staffList.getCheckedItemCount() == adapter.getCount()) {
                                allStaffCheckbox.setChecked(true);
                            }
                        }
                    }});
                //set the tag of the position so it can be used in the onclick
                newView.setTag(position);
                return newView;
            }
        };
        staffList.setAdapter(adapter);
        staffList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //initialize all to checked!
        allStaffCheckbox.setChecked(true);
        for (int i = 0; i < adapter.getCount(); i++) {
            staffList.setItemChecked(i, true);
        }


        allStaffCheckbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //note that the checking action happens before onclick so the isChecked is valid for the state it will be
                if(((CheckBox)v).isChecked()) {
                    for(int i = 0; i < adapter.getCount(); i ++ ) {
                        staffList.setItemChecked(i, true);
                    }
                }
                else {
                    for(int i = 0; i < adapter.getCount(); i ++ ) {
                        staffList.setItemChecked(i, false);
                    }
                }

            }});

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (getSelectedItems().size() > 0) {
                    clients = getSelectedItems();
                    onSuccessCallback.onFinished(clients);
                    dismiss();
                }
                else {

                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),"Please select at least one client.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                }

            }});

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCancelledCallback.run();
                SelectClientsDialog.this.dismiss();
            }});

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCancelledCallback.run();
                SelectClientsDialog.this.dismiss();
            }
        });

        allStaffLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allStaffCheckbox.toggle();
                if (allStaffCheckbox.isChecked()) {
                    for(int i = 0; i < adapter.getCount(); i++ ) {
                        staffList.setItemChecked(i, true);
                    }
                }
                else {
                    for(int i=0; i < adapter.getCount(); i++) {
                        staffList.setItemChecked(i, false);
                    }
                }
            }
        });

    }


    private ArrayList<Client> getSelectedItems() {
        ArrayList<Client> retVal = new ArrayList<Client>();
        SparseBooleanArray checkedPositions = staffList.getCheckedItemPositions();
        for(int i=0; i < adapter.getCount(); i++) {
            if(checkedPositions.get(i, false)) {
                retVal.add(adapter.getItem(i));
            }
        }
        return retVal;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_clients_dialog_fragment, container, false);
        ButterKnife.inject(this, v);
        return v;
    }
}
