package sn.zapp.matchday;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.RealmList;
import io.realm.RealmResults;
import sn.zapp.R;
import sn.zapp.event.RoundValueEvent;
import sn.zapp.event.SaveEvent;
import sn.zapp.model.Championship;
import sn.zapp.model.ChampionshipRoundValue;
import sn.zapp.model.Member;
import sn.zapp.model.MemberChampionshipValue;
import sn.zapp.model.Round;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;
import sn.zapp.util.RoundDescription;
import sn.zapp.util.RoundValueNew;

/**
 * Created by Steppo on 22.01.2016.
 */
public class TabChampionshipNew extends Fragment {

    private Member member;
    private ZappRealmDBManager realmDBManager = null;
    private RealmList<MemberChampionshipValue> resultChampionship = null;
    private Action viewState = null;
    private TextInputLayout header;
    protected LinearLayout childRootLayout = null;
    private List<RoundValueNew> roundValues = null;
    private final EventBus bus = EventBus.getDefault();
    private RoundValueNew currentRound = null;
    private View tabChampionship = null;
    private LayoutInflater inflater = null;
    private static final int NUMBER = 3;

    public TabChampionshipNew() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmDBManager = new ZappRealmDBManager();
        roundValues = new ArrayList<>();
        bus.register(this);
    }

    public void onEvent(RoundValueEvent event) {
//        header(event.getMemberEmail(), event.getChampionship(), event.getValue(), event.getRound());
//        hier header aktualisierenDann bis Sam
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabChampionshipNew newInstance(Member member, RealmList<MemberChampionshipValue> championshipValues, Action viewState) {
        TabChampionshipNew fragment = new TabChampionshipNew();
        fragment.setMember(member);
        fragment.setResultChampionship(championshipValues);
        fragment.setViewState(viewState);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setInflater(inflater);

        tabChampionship = getInflater().inflate(R.layout.tab_championship, container, false);

        childRootLayout = (LinearLayout) tabChampionship.findViewById(R.id.layout_child);
        initFields(inflater, container);

        return tabChampionship;
    }

    //
    public void onEvent(SaveEvent saveEvent) {
        for (RoundValueNew roundValue : roundValues) {
            roundValue.getEditTextResult().clearFocus();
        }
    }


    public void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Reset...");
        alertDialog.setMessage("Are you sure?");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setIcon(R.drawable.zapplogo);
        alertDialog.show();
    }
    protected void showAbout(Championship championship) {
        // Inflate the dialog_round_description message contents
        View messageView = new RoundDescription(getContext(), championship);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setIcon(R.drawable.zapplogo);
        builder.setTitle(championship.getName());
        builder.setView(messageView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
    }
    public void initFields(LayoutInflater inflater, ViewGroup container) {
        RealmResults list = realmDBManager.list_all_championships();
        for (final Championship value : realmDBManager.list_all_championships()) {
            View roundValueMaster = inflater.inflate(R.layout.tab_championship_master_card, null);//, false);

            ImageView iconInfo = (ImageView) roundValueMaster.findViewById(R.id.icon_info);

            iconInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAbout(value);//realmDBManager.list_all_championships().first());
                }
            });

            List<Integer> breakpoints = getBreakpoints(value.getRounds().size());

            RelativeLayout roundValueMasterChild = (RelativeLayout) roundValueMaster.findViewById(R.id.layout_child);
            header = (TextInputLayout) roundValueMaster.findViewById(R.id.input_layout_header);
            header.getEditText().setText(value.getName());
            for (Round round : value.getRounds()) {
                RoundValueNew roundValue = new RoundValueNew(getContext(), value.getName(), member.getEmail(), round.getRoundnumber());
                roundValue.setRound(round);
                RelativeLayout.LayoutParams rlp = null;
                roundValueMasterChild.addView(roundValue);


//                for (Integer breakpoint: breakpoints) {
//                    boolean in = breakpoints.contains(new Integer(round.getRoundnumber()));
//                    if ( in) {//hier noch ändern right of auch für die über 3 dinger machen
//                        int breakpoint = 0;
//                        for (Integer breaks : breakpoints){
//                                if(breaks.equals(new Integer(round.getRoundnumber())))
//                                    breakpoint = breaks;
//
//                        }
//                        int index = breakpoint - NUMBER;
//                        Log.d("Index", round.getRoundnumber() + "_BELOW_" + index);
//                        rlp = (RelativeLayout.LayoutParams) roundValue.getLayoutParams();
//                        rlp.addRule(RelativeLayout.BELOW, roundValues.get(index).getId());
//                    }else {
//                        if(currentRound!=null) {
//                            rlp = (RelativeLayout.LayoutParams) roundValue
//                                    .getLayoutParams();
//                            Log.d("Index", round.getRoundnumber() + "_RIGHT OF_" + currentRound.getId());
//                            rlp.addRule(RelativeLayout.RIGHT_OF, currentRound.getId());
//                        }
//                    }


            if (currentRound != null && round.getRoundnumber() != 4 && round.getRoundnumber() != 7) {
                rlp = (RelativeLayout.LayoutParams) roundValue
                        .getLayoutParams();
                rlp.addRule(RelativeLayout.RIGHT_OF, currentRound.getId());
                Log.d("RIGHT OF", round.getRoundnumber() + "_RIGHT OF_" + currentRound.getId());
            }

            if (round.getRoundnumber() == 4) {//hier noch ändern right of auch für die über 3 dinger machen
                rlp = (RelativeLayout.LayoutParams) roundValue
                        .getLayoutParams();
                rlp.removeRule(RelativeLayout.RIGHT_OF);
                rlp.addRule(RelativeLayout.BELOW, roundValues.get(0).getId());
                Log.d("BELOW", round.getRoundnumber() + "_BELOW_" + roundValues.get(0).getId());
            } else if (round.getRoundnumber() == 7) {
                rlp = (RelativeLayout.LayoutParams) roundValue
                        .getLayoutParams();
                rlp.removeRule(RelativeLayout.RIGHT_OF);
                rlp.addRule(RelativeLayout.BELOW, roundValues.get(3).getId());
                Log.d("BELOW", round.getRoundnumber() + "_BELOW_" + roundValues.get(3).getId());
            }

                roundValue.setLayoutParams(rlp != null ? rlp :  new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                currentRound = roundValue;
                roundValues.add(roundValue);
                if (getResultChampionship() != null) {
                    for (MemberChampionshipValue valueResult : getResultChampionship()) {
                        if (value.getName().equals(valueResult.getChampionship().getName())) {
                            if (valueResult.getRoundResult() != null) {
                                for (ChampionshipRoundValue roundResultValue : valueResult.getRoundResult()) {
                                    if (roundResultValue.getRound().getRoundnumber() == round.getRoundnumber()) {
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
            currentRound = null;
        }


    }

    private List<Integer> getBreakpoints(int size) {
        List<Integer> result = new ArrayList<>();

        Integer ints = size / NUMBER;

        for (int i = 1; i < ints + 1 ; i++) {
            result.add(new Integer((i*NUMBER))+1);
        }

        return result;
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
    public void onPause() {
        super.onPause();
        currentRound = null;
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

    public void setInflater(LayoutInflater argInflater){
        inflater = argInflater;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }
}
