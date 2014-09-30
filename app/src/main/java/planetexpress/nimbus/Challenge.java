package planetexpress.nimbus;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Calendar;
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

    private Calendar startDate;
    private Calendar endDate;

    private int currentSteps;
    private int currentCalories;

    private int goalSteps;
    private int goalCalories;

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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getGoalSteps() {
        return goalSteps;
    }

    public void setGoalSteps(int goalSteps) {
        this.goalSteps = goalSteps;
    }

    public int getGoalCalories() {
        return goalCalories;
    }

    public void setGoalCalories(int goalCalories) {
        this.goalCalories = goalCalories;
    }

    public void setCurrentSteps(int currentSteps) {
        this.currentSteps = currentSteps;
    }

    public void setCurrentCalories(int currentCalories) {
        this.currentCalories = currentCalories;
    }

    public static Challenge fromParseObject(ParseObject parseChallenge) {
        Challenge challenge = new Challenge();
        challenge.setName(parseChallenge.getString(Challenge.PARSE_NAME));
        challenge.setId(parseChallenge.getString(Challenge.PARSE_ID));
        challenge.setDescription(parseChallenge.getString(Challenge.PARSE_DESCRIPTION));
        challenge.setEndTime("9/30 9:00AM");
        Calendar date = Calendar.getInstance();
        date.roll(Calendar.HOUR_OF_DAY, 12);
        challenge.setStartDate(Calendar.getInstance());
        challenge.setEndDate(date);
        challenge.setGoalSteps(10000);
        challenge.setGoalCalories(3000);
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
