package planetexpress.nimbus;

import java.util.ArrayList;

public class Challenge {
    private int mId;
    private int mName;
    private ArrayList<Client> mParticipants;
    private String endTime;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getName() {
        return mName;
    }

    public void setName(int mName) {
        this.mName = mName;
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
}
