package planetexpress.nimbus;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static final String PARSE_CLASS = "Client";
    public static final String PARSE_NAME = "Name";

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
        if(object == null) return null;

        Client client = new Client();
        client.setId(object.getObjectId());
        client.setName(object.getString(PARSE_NAME));

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
