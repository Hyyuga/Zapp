package sn.zapp.matchday;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.ZappApplication;
import sn.zapp.model.Matchday;
import sn.zapp.model.Member;
import sn.zapp.model.MemberPenalyValue;
import sn.zapp.model.MemberResult;
import sn.zapp.model.MemberScoreValue;

public class MatchdayTabFragment extends Fragment{

    private Member member;
    private TabLayout tabLayout;
    private RealmList<MemberPenalyValue> penaltyResult = null;
    private RealmList<MemberScoreValue> resultScore = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_matchday_tablayout, container, false);

        setTabLayout((TabLayout) inflatedView.findViewById(R.id.tabLayout));
        getTabLayout().addTab(getTabLayout().newTab().setText("Penalty"));
        getTabLayout().addTab(getTabLayout().newTab().setText("Score"));
        final ViewPager viewPager = (ViewPager) inflatedView.findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter
                (getChildFragmentManager(), getTabLayout().getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(getTabLayout()));
        getTabLayout().setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return inflatedView;
    }

    public static MatchdayTabFragment newInstance(int sectionNumber, Member member, String datum) {
        MatchdayTabFragment fragment = new MatchdayTabFragment();
        fragment.setMember(member);
        Realm realm = Realm.getInstance(ZappApplication.getAppContext());
        Matchday results = realm.where(Matchday.class).equalTo("datum", datum).findFirst();
        if(results != null && results.getMemberResults() != null) {
            for (MemberResult memberResult: results.getMemberResults()) {
                if(memberResult.getMember().equals(member.getEmail())){
                    fragment.setPenaltyResult(memberResult.getResultsPenalty());
                    fragment.setResultScore(memberResult.getResultsScore());
                }
            }
        }


        return fragment;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public RealmList<MemberPenalyValue> getPenaltyResult() {
        return penaltyResult;
    }

    public void setPenaltyResult(RealmList<MemberPenalyValue> penaltyResult) {
        this.penaltyResult = penaltyResult;
    }

    public RealmList<MemberScoreValue> getResultScore() {
        return resultScore;
    }

    public void setResultScore(RealmList<MemberScoreValue> resultScore) {
        this.resultScore = resultScore;
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        int mNumOfTabs;
//        private FragmentManager manager;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
//            this.manager = fm;
        }

//        public Fragment getActiveFragment(ViewPager container, int position) {
//            String name = makeFragmentName(container.getId(), position);
//            return  manager.findFragmentByTag(name);
//        }
//
//        private  String makeFragmentName(int viewId, int index) {
//            return "android:switcher:" + viewId + ":" + index;
//        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    TabPenalty tab1 = TabPenalty.newInstance(getMember(), getPenaltyResult());
                    return tab1;
                case 1:
                    TabScore tab2 = TabScore.newInstance(getMember(),getResultScore());
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}