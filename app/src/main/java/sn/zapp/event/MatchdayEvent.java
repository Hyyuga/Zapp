package sn.zapp.event;

import sn.zapp.model.Matchday;

/**
 * Created by Steppo on 27.01.2016.
 */
public class MatchdayEvent {
    private Matchday matchday;

    public MatchdayEvent(Matchday argMatchday){
        this.matchday = argMatchday;
    }

    public Matchday getMatchday() {
        return matchday;
    }

    public void setMatchday(Matchday matchday) {
        this.matchday = matchday;
    }
}
