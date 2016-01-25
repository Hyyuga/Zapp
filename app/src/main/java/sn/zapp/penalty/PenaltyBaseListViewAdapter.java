package sn.zapp.penalty;

import android.content.Context;

import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.base.BaseListViewAdapter;
import sn.zapp.model.Penalty;

/**
 * Created by Steppo on 20.01.2016.
 */
public class PenaltyBaseListViewAdapter extends BaseListViewAdapter {
    public PenaltyBaseListViewAdapter(Context context, RealmResults<Penalty> realmResults, boolean automaticUpdate, boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        final RealmObject object = realmResults.get(position);
        Penalty penalty = (Penalty) object;
        holder.mItem = penalty;
        holder.mContentView.setText(penalty.getName() + " ( " + penalty.getAmount() + " € )");
        super.onBindRealmViewHolder(holder, position);
    }
}
