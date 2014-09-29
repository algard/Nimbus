package planetexpress.nimbus;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MindbodyRepository {

    private final Context mContext;

    public interface DataListener{
        public void onData(ArrayList<Challenge> result);

        public void onError();
    }

    public MindbodyRepository(Context context){
        ParseObject testObject = new ParseObject("Challenge");
        testObject.put("Name", "my name");
        testObject.put("Time", "2");
        testObject.saveInBackground();


        this.mContext = context;
    }

    public ArrayList<Challenge> getAllChallenges(final DataListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    String bar = parseObjects.get(0).getString("Name");
                    Toast.makeText(mContext, bar, Toast.LENGTH_SHORT).show();

                    listener.onData(createChallengesFromParseObjects(parseObjects));
                } else {
                    // something went wrong
                }
            }
        });
        return new ArrayList<>();
    }

    private ArrayList<Challenge> createChallengesFromParseObjects(List<ParseObject> parseObjects) {
        ArrayList<Challenge> result = new ArrayList<>();
        for(ParseObject challenge : parseObjects){
            result.add(Challenge.fromParseObject(challenge));
        }
        return result;
    }

    //TODO get the list of challenges from Parse
    public ArrayList<Challenge> getChallengesForUser(String mClientID) {

        return new ArrayList<>();
    }

    //TODO get the list of clients from Parse
    public ArrayList<Client> getAllClients(){

        return new ArrayList<>();
    }
}
