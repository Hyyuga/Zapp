package sn.zapp.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.ZappApplication;
import sn.zapp.model.Championship;
import sn.zapp.model.Matchday;
import sn.zapp.model.Member;
import sn.zapp.model.Penalty;
import sn.zapp.model.Score;

/**
 * Created by Steppo on 17.01.2016.
 */
public class ZappRealmDBManager {

    private Realm realm;

    public ZappRealmDBManager() {
        realm = Realm.getInstance(ZappApplication.getAppContext());
    }

    public long insertRealmObject(RealmObject object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        return 1;
    }

    public void deleteMatchday(Matchday item) {
        realm.beginTransaction();
        realm.where(Matchday.class).equalTo("datum", item.getDatum()).findAll().clear();
        realm.commitTransaction();
    }

    public void deleteMember(Member member) {
        realm.beginTransaction();
        realm.where(Member.class).equalTo("email", member.getEmail()).findAll().clear();
        realm.commitTransaction();
    }

    public void deletePenalty(Penalty penalty) {
        realm.beginTransaction();
        realm.where(Penalty.class).equalTo("name", penalty.getName()).findAll().clear();
        realm.commitTransaction();
    }


    public void deleteScore(Score score) {
        realm.beginTransaction();
        realm.where(Score.class).equalTo("name", score.getName()).findAll().clear();
        realm.commitTransaction();
    }

    public void deleteChampionship(Championship championship) {
        realm.beginTransaction();
        realm.where(Score.class).equalTo("name", championship.getName()).findAll().clear();
        realm.commitTransaction();
    }

    public RealmResults<Member> list_all_members() {
        return realm.where(Member.class).findAll();
    }

    public RealmResults<Penalty> list_all_penalties() {
        return realm.where(Penalty.class).findAll();
    }

    public RealmResults<Score> list_all_scores() {
        return realm.where(Score.class).findAll();
    }


    public void close() {
        if (realm != null)
            realm.close();
    }

    public RealmResults<Matchday> list_all_matchdays() {
        return realm.where(Matchday.class).findAll();
    }

    public RealmResults<Championship> list_all_championships() {
        return realm.where(Championship.class).findAll();
    }

    public Member findMember(String email) {
        return realm.where(Member.class).equalTo("email", email).findFirst();
    }

    public Championship findChampionship(String name) {
        return realm.where(Championship.class).equalTo("name", name).findFirst();
    }
}