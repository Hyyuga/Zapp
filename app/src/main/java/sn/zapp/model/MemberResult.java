package sn.zapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Steppo on 18.01.2016.
 */
public class MemberResult extends RealmObject {

    private String member;

    private RealmList<MemberPenalyValue> resultsPenalty;
    private RealmList<MemberScoreValue> resultsScore;
    private RealmList<MemberChampionshipValue> resultsChampionship;

    private boolean attended;
    private boolean paid;


    public RealmList<MemberPenalyValue> getResultsPenalty() {
        return resultsPenalty;
    }

    public void setResultsPenalty(RealmList<MemberPenalyValue> resultsPenalty) {
        this.resultsPenalty = resultsPenalty;
    }

    public RealmList<MemberScoreValue> getResultsScore() {
        return resultsScore;
    }

    public void setResultsScore(RealmList<MemberScoreValue> resultsScore) {
        this.resultsScore = resultsScore;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public RealmList<MemberChampionshipValue> getResultsChampionship() {
        return resultsChampionship;
    }

    public void setResultsChampionship(RealmList<MemberChampionshipValue> resultsChampionship) {
        this.resultsChampionship = resultsChampionship;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
