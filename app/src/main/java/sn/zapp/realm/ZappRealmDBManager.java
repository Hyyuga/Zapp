package sn.zapp.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.ZappApplication;
import sn.zapp.model.Matchday;
import sn.zapp.model.Member;
import sn.zapp.model.Penalty;
import sn.zapp.model.Score;

/**
 * Created by Steppo on 17.01.2016.
 */
public class ZappRealmDBManager {

    private Realm realm;

    public ZappRealmDBManager(){
        realm = Realm.getInstance(ZappApplication.getAppContext());
    }

    public long update_member(Member toMerge, Member newMember){
        return -1;
    }

    public long insertRealmObject(RealmObject object){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        return 1;
    }

    public void deleteMember(Member member){
        realm.beginTransaction();
        realm.where(Member.class).equalTo("email", member.getEmail()).findAll().clear();
        realm.commitTransaction();
    }

    public void deletePenalty(Penalty penalty){
        realm.beginTransaction();
        realm.where(Penalty.class).equalTo("name", penalty.getName()).findAll().clear();
        realm.commitTransaction();
    }


    public void deleteScore(Score score){
        realm.beginTransaction();
        realm.where(Score.class).equalTo("name", score.getName()).findAll().clear();
        realm.commitTransaction();
    }

    public int delete_member(Member member){
        return -1;
    }
    public RealmResults<Member> list_all_members(){
        return realm.where(Member.class).findAll();
    }
    public RealmResults<Penalty> list_all_penalties(){
        return realm.where(Penalty.class).findAll();
    }

    public RealmResults<Score> list_all_scores(){
        return realm.where(Score.class).findAll();
    }


    public void close() {
        if(realm != null)
            realm.close();
    }

    public RealmResults<Matchday> list_all_matchdays() {
        return realm.where(Matchday.class).findAll();
    }
}
//        Member mMember = realm.createObject(Member.class);
//        mMember.setAdresse(member.getAdresse());
//        mMember.setEmail(member.getEmail());
//        mMember.setGeburtstag(member.getGeburtstag());
//        mMember.setVorname(member.getVorname());
//        mMember.setNachname(member.getNachname());