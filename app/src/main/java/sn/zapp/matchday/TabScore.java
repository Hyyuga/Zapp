package sn.zapp.matchday;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.model.Member;
import sn.zapp.model.MemberScoreValue;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabScore extends Fragment {

    private Member member;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ZappRealmDBManager realmDBManager = null;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mUiRecyclerView;
    private RealmList<MemberScoreValue> resultScore = null;
    private Action viewState = null;
    HeaderAdapterScore adapterScore = null;

    public TabScore() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabScore newInstance(Member member, RealmList<MemberScoreValue> resultScore, Action viewState) {
        TabScore fragment = new TabScore();
        fragment.setMember(member);
        fragment.setResultScore(resultScore);
        fragment.setViewState(viewState);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_score, container, false);
        realmDBManager = new ZappRealmDBManager();
        mUiRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_score);

        mUiRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mUiRecyclerView.setLayoutManager(mLinearLayoutManager);
        mUiRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapterScore = new HeaderAdapterScore(realmDBManager.list_all_scores(), getMember(), getResultScore(), viewState);
        mUiRecyclerView.setAdapter(adapterScore);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        if (realmDBManager != null) realmDBManager.close();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public RealmList<MemberScoreValue> getResultScore() {
        return resultScore;
    }

    public void setResultScore(RealmList<MemberScoreValue> resultScore) {
        this.resultScore = resultScore;
    }

    public Action getViewState() {
        return viewState;
    }

    public void setViewState(Action viewState) {
        this.viewState = viewState;
    }
}
