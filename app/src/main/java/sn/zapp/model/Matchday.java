package sn.zapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steppo on 16.01.2016.
 */
public class Matchday extends RealmObject{
    @PrimaryKey
    private String datum;

    private RealmList<MemberResult> memberResults;

    public Matchday(){}


    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public RealmList<MemberResult> getMemberResults() {
        return memberResults;
    }

    public void setMemberResults(RealmList<MemberResult> memberResults) {
        this.memberResults = memberResults;
    }
}
