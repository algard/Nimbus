package planetexpress.nimbus;

import android.content.Context;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

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

    public ArrayList<Challenge> getAllChallenges(DataListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String bar = object.getString("Name");
                    Toast.makeText(mContext, bar, Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                }
            }
        });
        return new ArrayList<>();
    }
}
