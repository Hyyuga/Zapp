package sn.zapp.score;

import android.content.Context;

import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.base.BaseListViewAdapter;
import sn.zapp.model.Score;

/**
 * Created by Steppo on 20.01.2016.
 */
public class ScoreBaseListViewAdapter extends BaseListViewAdapter {
    public ScoreBaseListViewAdapter(Context context, RealmResults<Score> realmResults, boolean automaticUpdate, boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        final RealmObject object = realmResults.get(position);
        Score score = (Score) object;
        holder.mItem = score;
        holder.mContentView.setText(score.getName());
        super.onBindRealmViewHolder(holder, position);
    }

    @Override
    protected void setBindHolderContentText() {
    }
}
