package sn.zapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sn.zapp.R;
import sn.zapp.model.Championship;
import sn.zapp.model.Round;

/**
 * Created by Steppo on 01.02.2016.
 */
public class RoundDescription extends LinearLayout {

    private Championship championship = null;

    public RoundDescription(Context context, Championship championship) {
        super(context);
        this.championship = championship;
        initViews(context);
    }

    private void initViews(Context context) {
        View masterView = LayoutInflater.from(context).inflate(R.layout.dialog_round_description, this);

        LinearLayout roundValueMasterChild = (LinearLayout) masterView.findViewById(R.id.layout_master);

        for (Round round: championship.getRounds()) {
            LayoutParams lparams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            TextView tv=new TextView(context);
            tv.setLayoutParams(lparams);
            tv.setText("Runde " + String.valueOf(round.getRoundnumber()) + ": " + round.getDescription() + (round.getMultiplier() != 0 ? " Multiplikator :" + String.valueOf(round.getMultiplier()) : ""));
            roundValueMasterChild.addView(tv);
        }

    }


    public RoundDescription(Context context) {
        super(context);
    }



}
