package sn.zapp.matchday;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import io.realm.RealmResults;
import sn.zapp.R;
import sn.zapp.event.SaveEvent;
import sn.zapp.model.Championship;
import sn.zapp.model.ChampionshipRoundValue;
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
    private final EventBus bus = EventBus.getDefault();

    public TabChampionship() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmDBManager = new ZappRealmDBManager();
        roundValues =  new ArrayList<>();
        bus.register(this);
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
        View tabChampionship = inflater.inflate(R.layout.tab_championship, container, false);

        childRootLayout = (LinearLayout) tabChampionship.findViewById(R.id.layout_child);
        initFields(inflater, container);
        return tabChampionship;
    }
    //
    public void onEvent(SaveEvent saveEvent){
        for (RoundValue roundValue: roundValues) {
            roundValue.getEditTextDescription().clearFocus();
            roundValue.getEditTextRound().clearFocus();
            roundValue.getEditTextResult().clearFocus();
        }
    }


    public void initFields(LayoutInflater inflater, ViewGroup container) {
        RealmResults list = realmDBManager.list_all_championships();
        for (Championship value: realmDBManager.list_all_championships()) {
            View roundValueMaster = inflater.inflate(R.layout.tab_championship_round_value_master, container, false);
            LinearLayout roundValueMasterChild = (LinearLayout) roundValueMaster.findViewById(R.id.layout_child);
            header = (TextInputLayout) roundValueMaster.findViewById(R.id.input_layout_header);
            header.getEditText().setText(value.getName());

            for (Round round : value.getRounds()) {
                RoundValue roundValue = new RoundValue(getContext(), value.getName(), member.getEmail());
                roundValue.setRound(round);
                roundValueMasterChild.addView(roundValue);
                roundValues.add(roundValue);
                if(getResultChampionship() != null){
                    for (MemberChampionshipValue valueResult : getResultChampionship()) {
                        if(value.getName().equals(valueResult.getChampionship().getName())){
                            header.getEditText().setText(value.getName() + ": " + valueResult.getDbValue());
                            if(valueResult.getRoundResult() != null){
                                for(ChampionshipRoundValue roundResultValue : valueResult.getRoundResult()){
                                    if(roundResultValue.getRound().getRoundnumber() == round.getRoundnumber()){
                                        BigDecimal result = roundResultValue.getValue().divide(new BigDecimal(roundResultValue.getRound().getMultiplier()));
                                        roundValue.setStringResult(result.toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            childRootLayout.addView(roundValueMaster);
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
