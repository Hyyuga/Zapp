package sn.zapp.event;

import sn.zapp.model.Member;
import sn.zapp.model.Penalty;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 17.01.2016.
 */
public class PenaltyEvent {
    private Member member;
    private Penalty penalty;
    private Action action;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Penalty getPenalty() {
        return penalty;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
