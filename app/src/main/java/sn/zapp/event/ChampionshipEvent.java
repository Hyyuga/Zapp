package sn.zapp.event;

import sn.zapp.model.Member;
import sn.zapp.model.MemberChampionshipValue;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 19.01.2016.
 */
public class ChampionshipEvent {
    private Member member;
    private MemberChampionshipValue memberChampionshipValue;
    private Action action;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MemberChampionshipValue getMemberChampionshipValue() {
        return memberChampionshipValue;
    }

    public void setMemberChampionshipValue(MemberChampionshipValue penalty) {
        this.memberChampionshipValue = penalty;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
