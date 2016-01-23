package sn.zapp.matchday;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.Realm;
import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.ZappApplication;
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
import sn.zapp.util.Action;

public class MatchdayFragment extends Fragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private MatchdayPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ZappRealmDBManager realmDBManager = null;
    private EventBus bus = EventBus.getDefault();
    private Matchday matchday = null;
    private MatchdayListActivity baseListActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_matchday_detail, container, false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MatchdayPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        // By using this method the tabs will be populated according to viewPager's count and
        // with the name from the pagerAdapter getPageTitle()
        tabLayout.setTabsFromPagerAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMatchday();
            }
        });
        if(ZappApplication.getViewStatePenalty().equals(Action.SHOW.name()))
            fab.hide();

        FloatingActionButton fabCancel = (FloatingActionButton) view.findViewById(R.id.fab_cancel);
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(ZappApplication.getViewStatePenalty().equals(Action.EDITCREATE.name())) {
//                    try {
//                        Realm.getInstance(ZappApplication.getAppContext()).cancelTransaction();
//                    }catch (IllegalStateException e){
//                        //Do nothing
//                    }
//                }
                onBack();
            }
        });

        realmDBManager = new ZappRealmDBManager();



        if(this.matchday == null){
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            setMatchday(new Matchday());
            getMatchday().setDatum(date);
            baseListActivity.getToolbar().setTitle("Spieltag anlegen");
        } else baseListActivity.getToolbar().setTitle("Spieltag bearbeiten");
        if(ZappApplication.getViewStatePenalty().equals(Action.SHOW.name()))
            baseListActivity.getToolbar().setTitle("Spieltag ansehen");

        return view;
    }

    private void createMatchday() {
        if(!ZappApplication.getViewStatePenalty().equals(Action.EDITCREATE.name())
                && !ZappApplication.getViewStatePenalty().equals(Action.EDITCREATE.name()))
            realmDBManager.insertRealmObject(getMatchday());
        onBack();
    }

    private void onBack() {
        ZappApplication.setViewStatePenalty(Action.SHOW.name());
        ZappApplication.setViewStateScore(Action.SHOW.name());
        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean pop = manager.popBackStackImmediate();
        baseListActivity.getFab().show();
        baseListActivity.getToolbar().setTitle("Spieltage");
    }

    public void onEvent(PenaltyEvent event) {
        updateMemberPenaltyResult(event.getMember(), event.getPenalty());
    }

    public void onEvent(ScoreEvent event) {
        updateMemberScoreResult(event.getMember(), event.getScore());
    }

    private void updateMemberPenaltyResult(Member member, Penalty penalty) {
        if(ZappApplication.getViewStatePenalty().equals(Action.EDITCREATE.name()))
            Realm.getInstance(ZappApplication.getAppContext()).beginTransaction();

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
            resultPenaltyValue = new BigDecimal(1);
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
                if(resultPenaltyValue == null) resultPenaltyValue = new BigDecimal(1);
                resultPenalty = new MemberPenalyValue();
                resultPenalty.setPenalty(penalty);
                resultPenalty.setValue(resultPenaltyValue);
                result.getResultsPenalty().add(resultPenalty);
            } else resultPenalty.setValue(resultPenalty.getValue().add(new BigDecimal(1)));

        }
        if(ZappApplication.getViewStatePenalty().equals(Action.EDITCREATE.name()))
            Realm.getInstance(ZappApplication.getAppContext()).commitTransaction();

        Snackbar.make(getView(),"Hallo", Snackbar.LENGTH_LONG);
    }

    private void updateMemberScoreResult(Member member, Score score) {
        if(ZappApplication.getViewStateScore().equals(Action.EDITCREATE.name()))
            Realm.getInstance(ZappApplication.getAppContext()).beginTransaction();

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
                if(resultScoreValue == null) resultScoreValue = new BigDecimal(1);
                resultScore = new MemberScoreValue();
                resultScore.setScore(score);
                resultScore.setValue(resultScoreValue);
                result.getResultsScore().add(resultScore);
            } else resultScore.setValue(resultScore.getValue().add(new BigDecimal(1)));

        }
        if(ZappApplication.getViewStateScore().equals(Action.EDITCREATE.name()))
            Realm.getInstance(ZappApplication.getAppContext()).commitTransaction();
    }

    @Override
    public void onDestroy() {
        if(realmDBManager != null) realmDBManager.close();
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
            realmDBManager = new ZappRealmDBManager();
            memberlist = realmDBManager.list_all_members();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return MatchdayTabFragment.newInstance(position + 1, memberlist.get(position), matchday.getDatum());
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!bus.isRegistered(this))bus.register(this);
//        if(this.matchday != null) {
//            ScoreResultEvent event = new ScoreResultEvent();
//            event.matchday = matchday;
//            bus.post(event);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            baseListActivity = (MatchdayListActivity) context;
        }
    }

    @Override
    public void onStop() {
        if (bus.isRegistered(this))bus.unregister(this);
        super.onStop();
    }
}
