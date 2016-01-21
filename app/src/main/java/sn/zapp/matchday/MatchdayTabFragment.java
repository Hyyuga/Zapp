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

import sn.zapp.R;
import sn.zapp.model.Member;

public class MatchdayTabFragment extends Fragment{

    private Member member;
    private TabLayout tabLayout;


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

    public static MatchdayTabFragment newInstance(int sectionNumber, Member member) {
        MatchdayTabFragment fragment = new MatchdayTabFragment();
        fragment.setMember(member);
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
                    TabPenalty tab1 = TabPenalty.newInstance(getMember());
                    return tab1;
                case 1:
                    TabScore tab2 = TabScore.newInstance(getMember());
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
