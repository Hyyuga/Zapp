package sn.zapp.penalty;

import android.os.Bundle;
import android.view.View;

import io.realm.RealmObject;
import sn.zapp.base.BaseDetailFragment;
import sn.zapp.base.BaseListActivity;
import sn.zapp.base.BaseListFragment;
import sn.zapp.model.Penalty;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 20.01.2016.
 */
public class PenaltyBaseListActivity extends BaseListActivity implements BaseListFragment.OnListFragmentInteractionListener{

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListItemDetailView(null, Action.CREATE);
            }
        });
    }

    @Override
    protected void deleteListObject(RealmObject item) {
        realmDBManager.deletePenalty((Penalty) item);
    }

    @Override
    public void onListFragmentInteraction(RealmObject item, Action action) {
        Penalty penalty = (Penalty) item;
        if (action.equals(Action.DELETE))
            deleteListObject(penalty);
        else openListItemDetailView(item, action);
    }

    @Override
    protected String getActivityTitle() {
        return "Strafen";
    }

    @Override
    protected BaseListFragment getListFragment() {
        return new PenaltyBaseListFragment();
    }

    @Override
    protected BaseDetailFragment getBaseDetailFragment() {
        return new PenaltyBaseDetailFragment();
    }
}
