package sn.zapp.matchday;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.event.SaveEvent;
import sn.zapp.model.Member;
import sn.zapp.model.MemberScoreValue;
import sn.zapp.model.Score;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;
import sn.zapp.util.ScoreValue;

/**
 * Created by Steppo on 29.01.2016.
 */
public class TabScoreNew extends Fragment {

    private Member member;
    private ZappRealmDBManager realmDBManager = null;
    private RealmList<MemberScoreValue> resultScore = null;
    private Action viewState = null;
    protected LinearLayout childRootLayout = null;
    private final EventBus bus = EventBus.getDefault();
    private List<ScoreValue> penalties;
    View tabScore = null;

    public TabScoreNew() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmDBManager = new ZappRealmDBManager();
        bus.register(this);
        penalties = new ArrayList<>();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabScoreNew newInstance(Member member, RealmList<MemberScoreValue> penaltyValues, Action viewState) {
        TabScoreNew fragment = new TabScoreNew();
        fragment.setMember(member);
        fragment.setResultScore(penaltyValues);
        fragment.setViewState(viewState);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         tabScore = inflater.inflate(R.layout.tab_score_master, container, false);

        childRootLayout = (LinearLayout) tabScore.findViewById(R.id.layout_child);
        initFields(inflater, container);
        return tabScore;
    }

    //
    public void onEvent(SaveEvent saveEvent) {
    }


    public void initFields(LayoutInflater inflater, ViewGroup container) {

        LinearLayout roundValueMasterChild = (LinearLayout) tabScore.findViewById(R.id.layout_child);
        for (Score value : realmDBManager.list_all_scores()) {
            ScoreValue scoreValue = new ScoreValue(getContext(), member);
            scoreValue.setScore(value);
            if (getResultScore() != null) {
                for (MemberScoreValue valueResult : getResultScore()) {
                    if (valueResult.getScore().equals(value)) {
                        BigDecimal result = valueResult.getValue();
                        scoreValue.setStringTotalScore(result.toString());
                        scoreValue.setScoreValue(result.intValue());
                    }
                }
            }
            penalties.add(scoreValue);
            roundValueMasterChild.addView(scoreValue);
        }
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
