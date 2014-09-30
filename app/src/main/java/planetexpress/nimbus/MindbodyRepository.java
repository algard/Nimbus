package planetexpress.nimbus;

import android.content.Context;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import planetexpress.nimbus.data.ClientsInChallenge;

public class MindbodyRepository {

    private final Context mContext;

    public interface ChallengeDataListener {
        public void onData(ArrayList<Challenge> result);

        public void onError();
    }

    public interface ClientDataListener {
        public void onData(ArrayList<Client> result);

        public void onError();
    }

    public MindbodyRepository(Context context){
//        ParseObject testObject = new ParseObject(Challenge.PARSE_CLASS);
//        testObject.put(Challenge.PARSE_NAME, "my name");
//        testObject.put(Challenge.PARSE_TIME, "2");
//        testObject.saveInBackground();
        this.mContext = context;
    }

    public void getAllChallenges(final ChallengeDataListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Challenge.PARSE_CLASS);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    listener.onData(Challenge.fromParseObjects(parseObjects));
                } else {
                    // something went wrong
                }
            }
        });
    }

    //TODO get the list of challenges from Parse
    public ArrayList<Challenge> getChallengesForUser(String mClientID, final ChallengeDataListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ClientsInChallenge.PARSE_CLASS);
        query.whereEqualTo(ClientsInChallenge.PARSE_CLIENT_ID, mClientID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null) {
                    ArrayList<ClientsInChallenge> clients = ClientsInChallenge.fromParseObjects(parseObjects);

                    //TODO
                    //listener.onData();
                }else {
                    // WELL FUCK YOU THEN
                }
            }
        });

        return new ArrayList<>();
    }

//    //TODO get the list of clients from Parse
    public void getAllClients(final ClientDataListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Client.PARSE_CLASS);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    listener.onData(Client.fromParseObjects(parseObjects));
                } else {
                    // something went wrong
                }
            }
        });
    }

    public void putNewChallengeParticipants(ArrayList<Client> clients, ParseObject challenge){
        ArrayList<ParseObject> entries = ClientsInChallenge.toParseObjects(clients, challenge.getString(Challenge.PARSE_NAME));
        for(ParseObject object: entries){
            object.saveInBackground();
        }
    }

    public void getClientsInChallenge(String challengeName){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Challenge.PARSE_CLASS);
        query.whereEqualTo(Challenge.PARSE_NAME, challengeName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    listener.onData(Challenge.fromParseObjects(parseObjects));
                } else {
                    // something went wrong
                }
            }
        });
    }

    public int getJawboneStepsInPastDay(){
        //TODO
            // get # steps ! return as int!
        return 0;
    }
}
