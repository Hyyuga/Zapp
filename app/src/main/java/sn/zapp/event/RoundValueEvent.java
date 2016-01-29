package sn.zapp.event;

import sn.zapp.model.Round;

/**
 * Created by Steppo on 27.01.2016.
 */
public class RoundValueEvent {
    private String memberEmail;
    private Round round;
    private String championship;
    private String value;

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getChampionship() {
        return championship;
    }

    public void setChampionship(String championship) {
        this.championship = championship;
    }
}
