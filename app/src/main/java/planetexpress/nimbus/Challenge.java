package planetexpress.nimbus;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    public static final String PARSE_NAME = "Name";
    public static final String PARSE_ID = "objectId";
    public static final String PARSE_CLASS = "Challenge";
    public static final String PARSE_TIME = "endTime";
    public static final String PARSE_DESCRIPTION = "Description";

    private String mId;
    private String mName;
    private ArrayList<Client> mParticipants;
    private String endTime;
    private String description;

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

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static Challenge fromParseObject(ParseObject parseChallenge) {
        Challenge challenge = new Challenge();
        challenge.setName(parseChallenge.getString(Challenge.PARSE_NAME));
        challenge.setId(parseChallenge.getString(Challenge.PARSE_ID));
        challenge.setDescription(parseChallenge.getString(Challenge.PARSE_DESCRIPTION));
        challenge.setEndTime("9/30 9:00AM");
        return challenge;
    }

    public static ArrayList<Challenge> fromParseObjects(List<ParseObject> objectList){
        ArrayList<Challenge> result = new ArrayList<>();
        for(ParseObject challenge : objectList){
            result.add(Challenge.fromParseObject(challenge));
        }
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
