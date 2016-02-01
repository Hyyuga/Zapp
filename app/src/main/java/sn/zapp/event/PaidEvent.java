package sn.zapp.event;

import sn.zapp.model.Member;

/**
 * Created by Steppo on 30.01.2016.
 */
public class PaidEvent {

    private Member member;

    private boolean paid;

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
