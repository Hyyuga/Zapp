package sn.zapp.championship;

import android.content.Context;

import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.base.BaseListViewAdapter;
import sn.zapp.model.Championship;

/**
 * Created by Steppo on 20.01.2016.
 */
public class ChampionshipBaseListViewAdapter extends BaseListViewAdapter {
    public ChampionshipBaseListViewAdapter(Context context, RealmResults<Championship> realmResults, boolean automaticUpdate, boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        final RealmObject object = realmResults.get(position);
        Championship championship = (Championship) object;
        holder.mItem = championship;
        holder.mContentView.setText(championship.getName());
        super.onBindRealmViewHolder(holder, position);
    }
}
