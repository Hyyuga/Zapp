package sn.zapp.matchday;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.RealmList;
import io.realm.RealmResults;
import sn.zapp.R;
import sn.zapp.event.PenaltyEvent;
import sn.zapp.event.ScoreEvent;
import sn.zapp.model.Matchday;
import sn.zapp.model.Member;
import sn.zapp.model.MemberPenalyValue;
import sn.zapp.model.MemberResult;
import sn.zapp.model.MemberScoreValue;
import sn.zapp.model.Penalty;
import sn.zapp.model.Score;
import sn.zapp.realm.ZappRealmDBManager;

public class MatchdayActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private MatchdayPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    //    private ZappDBManager dbManager;
    private ZappRealmDBManager realmDBManager = null;
    //    private List<Member> memberList;
//    private HashMap<Member, Matchday> hashMapMemberMatchday;
    private EventBus bus = EventBus.getDefault();
    private Matchday matchday = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchday_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MatchdayPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // By using this method the tabs will be populated according to viewPager's count and
        // with the name from the pagerAdapter getPageTitle()
        tabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        realmDBManager = new ZappRealmDBManager();

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMatchday();
            }
        });

        RealmResults<Matchday> list = realmDBManager.list_all_matchdays();

        setMatchday(new Matchday());
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        getMatchday().setDatum(date);

        bus.register(this);
    }

    private void createMatchday() {
        realmDBManager.insertRealmObject(getMatchday());
    }

    public void onEvent(PenaltyEvent event) {
        updateMemberPenaltyResult(event.getMember(), event.getPenalty());
    }

    public void onEvent(ScoreEvent event) {
        updateMemberScoreResult(event.getMember(), event.getScore());
    }

    private void updateMemberPenaltyResult(Member member, Penalty penalty) {
        RealmList<MemberResult> memberResultList = getMatchday().getMemberResults();
        BigDecimal resultPenaltyValue = null;
        if (memberResultList == null)
            getMatchday().setMemberResults(new RealmList<MemberResult>());

        MemberResult result = null;
        for (MemberResult mResult : getMatchday().getMemberResults()) {
            if (mResult.getMember().equals(member.getEmail())) {
                result = mResult;
                break;
            }
        }

        if (result == null || (result != null && result.getResultsPenalty() == null)) {
            boolean addPenalty = result == null;
            resultPenaltyValue = new BigDecimal(2);
            if(result == null) result = new MemberResult();
            MemberPenalyValue resultPenalty = new MemberPenalyValue();
            resultPenalty.setPenalty(penalty);
            resultPenalty.setValue(resultPenaltyValue);
            result.setResultsPenalty(new RealmList<MemberPenalyValue>());
            result.getResultsPenalty().add(resultPenalty);
            result.setMember(member.getEmail());
            if(addPenalty) getMatchday().getMemberResults().add(result);
        } else {
            MemberPenalyValue resultPenalty = null;
            for (MemberPenalyValue value : result.getResultsPenalty()) {
                if (value.getPenalty().equals(penalty)) {
                    resultPenalty = value;
                    break;
                }
            }

            if (resultPenalty == null) {
                resultPenalty = new MemberPenalyValue();
                resultPenalty.setPenalty(penalty);
                resultPenalty.setValue(resultPenaltyValue);
                result.getResultsPenalty().add(resultPenalty);
            } else resultPenalty.setValue(resultPenalty.getValue().add(new BigDecimal(2)));

        }
    }

    private void updateMemberScoreResult(Member member, Score score) {
        RealmList<MemberResult> memberResultList = getMatchday().getMemberResults();
        BigDecimal resultScoreValue = null;
        if (memberResultList == null)
            getMatchday().setMemberResults(new RealmList<MemberResult>());

        MemberResult result = null;
        for (MemberResult mResult : getMatchday().getMemberResults()) {
            if (mResult.getMember().equals(member.getEmail())) {
                result = mResult;
                break;
            }
        }

        if (result == null || (result != null && result.getResultsScore() == null)) {
            boolean addResult = result == null;
            resultScoreValue = new BigDecimal(1);
            if(result == null) result =  new MemberResult();
            MemberScoreValue resultScore = new MemberScoreValue();
            resultScore.setScore(score);
            resultScore.setValue(resultScoreValue);
            result.setResultsScore(new RealmList<MemberScoreValue>());
            result.getResultsScore().add(resultScore);
            result.setMember(member.getEmail());
            if(addResult) getMatchday().getMemberResults().add(result);
        } else {
            MemberScoreValue resultScore = null;
            for (MemberScoreValue value : result.getResultsScore()) {
                if (value.getScore().equals(score)) {
                    resultScore = value;
                    break;
                }
            }

            if (resultScore == null) {
                resultScore = new MemberScoreValue();
                resultScore.setScore(score);
                resultScore.setValue(resultScoreValue);
                result.getResultsScore().add(resultScore);
            } else resultScore.setValue(resultScore.getValue().add(new BigDecimal(1)));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds mValues to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_matchday, menu);
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

    @Override
    public boolean onNavigateUp() {
        realmDBManager.close();
        Runtime.getRuntime().gc();
        return super.onNavigateUp();
    }

    @Override
    protected void onDestroy() {
        realmDBManager.close();
        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    public Matchday getMatchday() {
        return matchday;
    }

    public void setMatchday(Matchday matchday) {
        this.matchday = matchday;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class MatchdayPagerAdapter extends FragmentStatePagerAdapter {

        //        private ZappDBManager dbManager;
        private ZappRealmDBManager realmDBManager = null;
        private List<Member> memberlist;

        public MatchdayPagerAdapter(FragmentManager fm) {
            super(fm);
//            dbManager = ZappDBManager.getInstance();
            realmDBManager = new ZappRealmDBManager();
            memberlist = realmDBManager.list_all_members();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return null;//MatchdayTabFragment.newInstance(position + 1, memberlist.get(position));
        }

        @Override
        public int getCount() {
            return realmDBManager.list_all_members().size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return memberlist.get(position).getVorname() + " " + memberlist.get(position).getNachname();
        }
    }
}
