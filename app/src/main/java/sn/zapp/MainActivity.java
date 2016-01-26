package sn.zapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import sn.zapp.championship.ChampionshipBaseListActivity;
import sn.zapp.location.LocationActivity;
import sn.zapp.matchday.MatchdayBaseListActivity;
import sn.zapp.member.MemberBaseListActivity;
import sn.zapp.penalty.PenaltyBaseListActivity;
import sn.zapp.score.ScoreBaseListActivity;
import sn.zapp.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;

    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    private static Handler mHandler;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() Restoring previous state");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        //Set the fragment initially
        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_members) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startIntent(MemberBaseListActivity.class);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

            View mainContent = findViewById(R.id.main_content);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }

            // Handle the camera action
        }  else if (id == R.id.nav_championship) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startIntent(ChampionshipBaseListActivity.class);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

            View mainContent = findViewById(R.id.main_content);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }
        } else if (id == R.id.nav_penalties) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startIntent(PenaltyBaseListActivity.class);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

            View mainContent = findViewById(R.id.main_content);
            if (mainContent != null) {
                mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
            }


        } else if (id == R.id.nav_share) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/106903569375159/?fref=ts"));
            startActivity(browserIntent);
        } else if (id == R.id.nav_location) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startIntent(LocationActivity.class);
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.nav_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.nav_score) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startIntent(ScoreBaseListActivity.class);
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        } else if (id == R.id.nav_matchday) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   startIntent(MatchdayBaseListActivity.class);
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startIntent(Class clazzz){
        Intent newIntent = new Intent(this, clazzz);
        startActivity(newIntent);
    }
}
