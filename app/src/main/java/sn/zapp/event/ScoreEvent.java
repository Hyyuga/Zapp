package sn.zapp.event;

import sn.zapp.model.Member;
import sn.zapp.model.Score;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 17.01.2016.
 */
public class ScoreEvent {
    private Member member;
    private Score penalty;
    private Action action;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Score getScore() {
        return penalty;
    }

    public void setScore(Score penalty) {
        this.penalty = penalty;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
