package sn.zapp.member;

import android.content.Context;

import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.base.BaseListViewAdapter;
import sn.zapp.model.Member;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MemberBaseListViewAdapter extends BaseListViewAdapter {
    public MemberBaseListViewAdapter(Context context, RealmResults<Member> realmResults, boolean automaticUpdate, boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        final RealmObject object = realmResults.get(position);
        Member member = (Member) object;
        holder.mItem = member;
        holder.mContentView.setText(member.getVorname() + " " + member.getNachname());
        super.onBindRealmViewHolder(holder, position);
    }
}
