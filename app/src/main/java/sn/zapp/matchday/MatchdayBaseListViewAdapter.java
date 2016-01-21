package sn.zapp.matchday;

import android.content.Context;

import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.base.BaseListViewAdapter;
import sn.zapp.model.Matchday;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MatchdayBaseListViewAdapter extends BaseListViewAdapter {
    public MatchdayBaseListViewAdapter(Context context, RealmResults<Matchday> realmResults, boolean automaticUpdate, boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        final RealmObject object = realmResults.get(position);
        Matchday matchday = (Matchday) object;
        holder.mItem = matchday;
        holder.mContentView.setText(matchday.getDatum());
        super.onBindRealmViewHolder(holder, position);
    }

    @Override
    protected void setBindHolderContentText() {
    }
}
