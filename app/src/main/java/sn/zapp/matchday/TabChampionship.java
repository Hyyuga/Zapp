package sn.zapp.matchday;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.model.Championship;
import sn.zapp.model.Member;
import sn.zapp.model.MemberChampionshipValue;
import sn.zapp.model.Round;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;
import sn.zapp.util.RoundValue;

/**
 * Created by Steppo on 22.01.2016.
 */
public class TabChampionship extends Fragment {

    private Member member;
    private ZappRealmDBManager realmDBManager = null;
    private RealmList<MemberChampionshipValue> resultChampionship = null;
    private Action viewState = null;
    private TextInputLayout header;
    protected LinearLayout childRootLayout = null;
    private List<RoundValue> roundValues = null;

    public TabChampionship() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmDBManager = new ZappRealmDBManager();
//        EventBus.getDefault().register(this);
        roundValues =  new ArrayList<>();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabChampionship newInstance(Member member, RealmList<MemberChampionshipValue> championshipValues, Action viewState) {
        TabChampionship fragment = new TabChampionship();
        fragment.setMember(member);
        fragment.setResultChampionship(championshipValues);
        fragment.setViewState(viewState);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_championship, container, false);
        header = (TextInputLayout) view.findViewById(R.id.input_layout_header);
        childRootLayout = (LinearLayout) view.findViewById(R.id.layout_child);
        initFields();
        return view;
    }
//
//    public void onEvent(SaveEvent saveEvent){
//        for (RoundValue roundValue: roundValues) {
//            roundValue.getEditTextDescription().clearFocus();
//            roundValue.getEditTextRound().clearFocus();
//            roundValue.getEditTextResult().clearFocus();
//        }
//    }


    public void initFields() {
        for (Championship value: realmDBManager.list_all_championships()) {
            header.getEditText().setText(value.getName());
            for (Round round : value.getRounds()) {
                RoundValue roundValue = new RoundValue(getContext(), -12);
                roundValue.setRound(round);
                childRootLayout.addView(roundValue);
                roundValues.add(roundValue);
            }
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

    public RealmList<MemberChampionshipValue> getResultChampionship() {
        return resultChampionship;
    }

    public void setResultChampionship(RealmList<MemberChampionshipValue> resultChampionship) {
        this.resultChampionship = resultChampionship;
    }

    public Action getViewState() {
        return viewState;
    }

    public void setViewState(Action viewState) {
        this.viewState = viewState;
    }
}
