package planetexpress.nimbus;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david.algar on 9/27/2014.
 */
public class Client {
    public static final String PARSE_CLASS = "Client";
    public static final String PARSE_NAME = "name";
    public static final String PARSE_ID = "name";
    public String mId;
    public String mName;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


    public String getId(){
        return mId;
    }

    public void setId(String id){
        this.mId = id;
    }

    public static Client fromParseObject(ParseObject object){
        Client client = new Client();
        client.setName(object.getString(PARSE_NAME));
        client.setId(object.getString(PARSE_ID));
        return client;
    }

    public static ArrayList<Client> fromParseObjects(List<ParseObject> parseObjects) {
        ArrayList<Client> result = new ArrayList<>();
        for(ParseObject challenge : parseObjects){
            result.add(Client.fromParseObject(challenge));
        }
        return result;
    }
}
