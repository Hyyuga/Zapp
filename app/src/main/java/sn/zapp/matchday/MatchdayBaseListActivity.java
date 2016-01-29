package sn.zapp.matchday;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import de.greenrobot.event.EventBus;
import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.base.BaseDetailFragment;
import sn.zapp.base.BaseListActivity;
import sn.zapp.base.BaseListFragment;
import sn.zapp.event.MatchdayEvent;
import sn.zapp.model.Matchday;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MatchdayBaseListActivity extends BaseListActivity implements BaseListFragment.OnListFragmentInteractionListener{

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
        realmDBManager.deleteMatchday((Matchday) item);
    }

    @Override
    public void onListFragmentInteraction(RealmObject item, Action action) {
        Matchday matchday = (Matchday) item;
        if (action.equals(Action.DELETE))
            deleteListObject(matchday);
        else openListItemDetailView(item, action);
    }

    @Override
    protected void openListItemDetailView(RealmObject object, Action action) {
        final MatchdayFragment fragment = new MatchdayFragment();
        fragment.setMatchday((Matchday) object);
        fragment.setViewState(action);
        EventBus.getDefault().post(new MatchdayEvent((Matchday) object));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.activity_fragment_content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fab.hide();
            }
        }, 350);
    }

    @Override
    protected String getActivityTitle() {
        return "Ergebnisse";
    }

    @Override
    protected BaseListFragment getListFragment() {
        return new MatchdayBaseListFragment();
    }

    @Override
    protected BaseDetailFragment getBaseDetailFragment() {
        return new MatchdayFragment();
    }
}
