package sn.zapp.event;

import sn.zapp.model.Championship;
import sn.zapp.model.Member;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 19.01.2016.
 */
public class ChampionshipEvent {
    private Member member;
    private Championship championship;
    private Action action;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship penalty) {
        this.championship = penalty;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
