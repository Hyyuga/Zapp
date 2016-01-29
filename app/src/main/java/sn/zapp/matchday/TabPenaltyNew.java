package sn.zapp.matchday;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.event.SaveEvent;
import sn.zapp.model.Member;
import sn.zapp.model.MemberPenalyValue;
import sn.zapp.model.Penalty;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;
import sn.zapp.util.PenaltyValue;

/**
 * Created by Steppo on 29.01.2016.
 */
public class TabPenaltyNew extends Fragment {

    private Member member;
    private ZappRealmDBManager realmDBManager = null;
    private RealmList<MemberPenalyValue> resultPenalty = null;
    private Action viewState = null;
    private TextView header;
    protected LinearLayout childRootLayout = null;
    private final EventBus bus = EventBus.getDefault();
    private List<PenaltyValue> penalties;
    private BigDecimal totalPenaltyValue = null;

    public TabPenaltyNew() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmDBManager = new ZappRealmDBManager();
        bus.register(this);
        penalties = new ArrayList<>();
        totalPenaltyValue = new BigDecimal(0).setScale(2);
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabPenaltyNew newInstance(Member member, RealmList<MemberPenalyValue> penaltyValues, Action viewState) {
        TabPenaltyNew fragment = new TabPenaltyNew();
        fragment.setMember(member);
        fragment.setResultPenalty(penaltyValues);
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
    public void onEvent(SaveEvent saveEvent) {
    }


    public void initFields(LayoutInflater inflater, ViewGroup container) {

        View roundValueMaster = inflater.inflate(R.layout.tab_penalty_new, container, false);
        LinearLayout roundValueMasterChild = (LinearLayout) roundValueMaster.findViewById(R.id.layout_child);
        header = (TextView) roundValueMaster.findViewById(R.id.textViewHeaderStrafen);
        for (Penalty value : realmDBManager.list_all_penalties()) {
            PenaltyValue penaltyValue = new PenaltyValue(getContext(), member, this);
            penaltyValue.setPenalty(value);
            if (getResultPenalty() != null) {
                for (MemberPenalyValue valueResult : getResultPenalty()) {
                    if (valueResult.getPenalty().equals(value)) {
                        BigDecimal result = valueResult.getValue();
                        penaltyValue.setStringTotalPenalty(result.toString());
                        result = result.multiply(new BigDecimal(value.getAmount()).setScale(2));
                        totalPenaltyValue = totalPenaltyValue.add(result);
                    }
                }
            }
            penalties.add(penaltyValue);
            roundValueMasterChild.addView(penaltyValue);
        }
        header.setText("Strafen: " + totalPenaltyValue.toString() + " €");
        childRootLayout.addView(roundValueMaster);
    }

    public void addPenaltyTotal(BigDecimal value){
        this.totalPenaltyValue = totalPenaltyValue.add(value);
        header.setText("Strafen: " + totalPenaltyValue.toString() + " €");
    }
    public void minusPenaltyTotal(BigDecimal value){
        if(totalPenaltyValue.compareTo(new BigDecimal(0)) == 0) {
            this.totalPenaltyValue = totalPenaltyValue.subtract(value);
            header.setText("Strafen: " + totalPenaltyValue.toString() + " €");
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

    public RealmList<MemberPenalyValue> getResultPenalty() {
        return resultPenalty;
    }

    public void setResultPenalty(RealmList<MemberPenalyValue> resultPenalty) {
        this.resultPenalty = resultPenalty;
    }

    public Action getViewState() {
        return viewState;
    }

    public void setViewState(Action viewState) {
        this.viewState = viewState;
    }
}
