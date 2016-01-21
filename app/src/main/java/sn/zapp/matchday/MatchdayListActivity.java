package sn.zapp.matchday;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import sn.zapp.R;
import sn.zapp.model.Matchday;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;

public class MatchdayListActivity extends AppCompatActivity implements MatchdayListFragment.OnListFragmentInteractionListener{

    private Toolbar toolbar = null;
    private FloatingActionButton fab = null;
    private ZappRealmDBManager realmDBManager = null;
    private static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchday1);
        handler = new Handler();

        realmDBManager = new ZappRealmDBManager();

        MatchdayListFragment fragment = new MatchdayListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();

        setToolbar((Toolbar) findViewById(R.id.toolbar_matchday));
        getToolbar().setTitle("Spieltage");
        setSupportActionBar(getToolbar());

        setFab((FloatingActionButton) findViewById(R.id.fab));
        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMatchdayView(null, Action.CREATE);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onListFragmentInteraction(Matchday item, Action action) {
        if (action.equals(Action.DELETE))
            deleteMatchday(item);
        else openMatchdayView(item, action);
    }
    private void deleteMatchday(Matchday item) {
        realmDBManager.deleteMatchday(item);
    }

    private void openMatchdayView(Matchday matchday, Action action) {
        final MatchdayFragment fragment = new MatchdayFragment();
        fragment.setMatchday(matchday);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fab.hide();
            }
        }, 350);

    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

}
