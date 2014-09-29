package planetexpress.nimbus;

import com.parse.ParseObject;

import java.util.ArrayList;

public class Challenge {
    private static final String PARSE_NAME = "name";
    private static final String PARSE_ID = "id";

    private String mId;
    private String mName;
    private ArrayList<Client> mParticipants;
    private String endTime;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public ArrayList<Client> getParticipants() {
        return mParticipants;
    }

    public void setParticipants(ArrayList<Client> mParticipants) {
        this.mParticipants = mParticipants;
    }

    public String getEndTime() {
        return endTime;
    }

    public static Challenge fromParseObject(ParseObject parseChallenge) {
        Challenge challenge = new Challenge();
        challenge.setName(parseChallenge.getString(Challenge.PARSE_NAME));
        challenge.setId(parseChallenge.getString(Challenge.PARSE_ID));
        return challenge;
    }
}
