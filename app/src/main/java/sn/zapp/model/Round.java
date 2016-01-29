package sn.zapp.model;

import io.realm.RealmObject;

/**
 * Created by Steppo on 22.01.2016.
 */
public class Round extends RealmObject {
    private int roundnumber;
    private int multiplier;
    private String description;

    public int getRoundnumber() {
        return roundnumber;
    }

    public void setRoundnumber(int roundnumber) {
        this.roundnumber = roundnumber;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
