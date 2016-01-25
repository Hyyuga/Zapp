package sn.zapp.championship;

import android.os.Bundle;
import android.view.View;

import io.realm.RealmObject;
import sn.zapp.base.BaseDetailFragment;
import sn.zapp.base.BaseListActivity;
import sn.zapp.base.BaseListFragment;
import sn.zapp.model.Championship;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 20.01.2016.
 */
public class ChampionshipBaseListActivity extends BaseListActivity implements BaseListFragment.OnListFragmentInteractionListener{

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
        realmDBManager.deleteChampionship((Championship) item);
    }

    @Override
    public void onListFragmentInteraction(RealmObject item, Action action) {
        Championship championship = (Championship) item;
        if (action.equals(Action.DELETE))
            deleteListObject(championship);
        else openListItemDetailView(item, action);
    }

    @Override
    protected String getActivityTitle() {
        return "Meisterschaften";
    }

    @Override
    protected BaseListFragment getListFragment() {
        return new ChampionshipBaseListFragment();
    }

    @Override
    protected BaseDetailFragment getBaseDetailFragment() {
        return new ChampionshipBaseDetailFragment();
    }
}
