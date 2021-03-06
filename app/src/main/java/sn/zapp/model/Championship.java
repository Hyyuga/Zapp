package sn.zapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steppo on 13.01.2016.
 */
public class Championship extends RealmObject{
    @PrimaryKey
    private String name;

    private RealmList<Round> rounds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Round> getRounds() {
        return rounds;
    }

    public void setRounds(RealmList<Round> rounds) {
        this.rounds = rounds;
    }
}
