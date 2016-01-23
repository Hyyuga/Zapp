package sn.zapp.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 19.01.2016.
 */
public abstract class BaseListActivity extends AppCompatActivity{

    protected Toolbar toolbar = null;
    protected FloatingActionButton fab = null;
    protected ZappRealmDBManager realmDBManager = null;
    protected static Handler handler;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_base);

        handler = new Handler();

        realmDBManager = new ZappRealmDBManager();

        BaseListFragment fragment = getListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_fragment_content, fragment).commit();

        setToolbar((Toolbar) findViewById(R.id.toolbar));
        getToolbar().setTitle(getActivityTitle());
        setSupportActionBar(getToolbar());

        setFab((FloatingActionButton) findViewById(R.id.fab));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    @Override
    public boolean onNavigateUp() {
        realmDBManager.close();
        toolbar = null;
        Runtime.getRuntime().gc();
        return super.onNavigateUp();
    }

    @Override
    protected void onDestroy() {
        realmDBManager.close();
        toolbar = null;
        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    protected void openListItemDetailView(RealmObject object, Action action){
        final BaseDetailFragment fragment = getBaseDetailFragment();
        fragment.setSelectedListObject(object);
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
    };

    abstract protected void deleteListObject(RealmObject item);

    protected abstract String getActivityTitle();

    protected abstract BaseListFragment getListFragment();

    protected abstract BaseDetailFragment getBaseDetailFragment();

}
