package planetexpress.nimbus;

import android.content.Context;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Challenge.PARSE_CLASS);
        query.whereEqualTo("", "Dan Stemkoski");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                //TODO see above for getAll()
            }
        });

        return new ArrayList<>();
    }

    //TODO get the list of clients from Parse
    public void getAllClients(final ClientDataListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Client.PARSE_CLASS);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    String bar = parseObjects.get(0).getString(Client.PARSE_NAME);
                    listener.onData(Client.fromParseObjects(parseObjects));
                } else {
                    // something went wrong
                }
            }
        });
    }
}
