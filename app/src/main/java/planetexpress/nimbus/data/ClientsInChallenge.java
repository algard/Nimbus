package planetexpress.nimbus.data;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class ClientsInChallenge {
    public static final String PARSE_CLASS = "ClientsInChallenge";

    public static final String PARSE_CLIENT_ID = "ClientID";
    public static final String PARSE_ACCEPTED_CHALLENGE = "AcceptedChallenge";
    public static final String PARSE_CHALLENGE_ID = "ChallengeID";
    public static final String PARSE_COMPLETED = "Completed";

    public boolean AcceptedChallenge;
    public String ChallengeId;
    public String ClientID;
    public boolean Completed;

    public static ClientsInChallenge fromParseObject(ParseObject parseChallenge) {
        ClientsInChallenge challenge = new ClientsInChallenge();
        challenge.AcceptedChallenge = parseChallenge.getBoolean(ClientsInChallenge.PARSE_ACCEPTED_CHALLENGE);
        challenge.ChallengeId =  parseChallenge.getString(ClientsInChallenge.PARSE_CHALLENGE_ID);
        challenge.ClientID =  parseChallenge.getString(ClientsInChallenge.PARSE_CLIENT_ID);
        challenge.Completed = parseChallenge.getBoolean(PARSE_COMPLETED);
        return challenge;
    }

    public static ArrayList<ClientsInChallenge> fromParseObjects(List<ParseObject> parseObjectList){
        ArrayList<ClientsInChallenge> result = new ArrayList<>();
        for(ParseObject challenge : parseObjectList){
            result.add(ClientsInChallenge.fromParseObject(challenge));
        }
        return result;
    }

}
