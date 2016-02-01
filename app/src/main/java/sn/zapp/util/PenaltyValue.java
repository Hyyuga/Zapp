package sn.zapp.util;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import de.greenrobot.event.EventBus;
import sn.zapp.R;
import sn.zapp.event.PenaltyEvent;
import sn.zapp.matchday.TabPenaltyNew;
import sn.zapp.model.Member;
import sn.zapp.model.Penalty;

public class PenaltyValue extends LinearLayout {
    private Penalty penalty;

    private String stringTotalPenalty = "";
    private String stringContentPenalty = "";
    private int penaltyValue = 0;

    private ZappCardView zappCardView;
    private TextView textViewTotalPenalty;
    private TextView textViewContentPenalty;
    private ImageView imageViewMinus;
    protected static Handler handler;
    private TabPenaltyNew parentView;

    private final Member member;

    private final EventBus bus = EventBus.getDefault();

    public PenaltyValue(Context context, Member member, TabPenaltyNew view) {
        super(context);
//        LayoutInflater.from(context).inflate(R.layout.tab_penalty_value, this);
        initViews(context);
        handler = new Handler();
        this.member = member;
        this.setParentView(view);
    }
    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tab_penalty_value, this);

        setTextViewTotalPenalty((TextView) this.findViewById(R.id.text_view_total_penalty));
        getTextViewTotalPenalty().setText(getStringTotalPenalty());

        setTextViewContentPenalty((TextView) this.findViewById(R.id.text_view_content_penalty));
        getTextViewContentPenalty().setText(getStringContentPenalty());

        setImageViewMinus((ImageView) this.findViewById(R.id.buttonMinus));
        getImageViewMinus().setImageResource(R.drawable.ic_delete_black_48dp);

        setZappCardView((ZappCardView) this.findViewById(R.id.card_view));

        initListener();

    }

    private void initListener() {
        getZappCardView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (action == MotionEvent.ACTION_UP) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addNumber();
                            getParentView().addPenaltyTotal(new BigDecimal(penalty.getAmount()).setScale(2));
                            PenaltyEvent events = new PenaltyEvent();
                            events.setMember(getMember());
                            events.setPenalty(penalty);
                            events.setAction(Action.ADD);
                            EventBus.getDefault().post(events);
                        }
                    }, 200);

                }
                return false;
            }

        });
        getImageViewMinus().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            minusNumber();
                            getParentView().minusPenaltyTotal(new BigDecimal(penalty.getAmount()).setScale(2));
                            PenaltyEvent events = new PenaltyEvent();
                            events.setMember(getMember());
                            events.setPenalty(penalty);
                            events.setAction(Action.ADD);
                            EventBus.getDefault().post(events);
                        }
                    }, 200);

            }

        });
    }

    public String getStringTotalPenalty() {
        return stringTotalPenalty;
    }

    public void setStringTotalPenalty(String stringTotalPenalty) {
        this.stringTotalPenalty = stringTotalPenalty;
        if(this.textViewTotalPenalty != null)
            textViewTotalPenalty.setText("Nr. " + stringTotalPenalty);
    }

    public String getStringContentPenalty() {
        return stringContentPenalty;
    }

    public void setStringContentPenalty(String stringContentPenalty) {
        this.stringContentPenalty = stringContentPenalty;
        if(this.textViewContentPenalty != null)
            textViewContentPenalty.setText(stringContentPenalty);
    }

    public TextView getTextViewTotalPenalty() {
        return textViewTotalPenalty;
    }

    public void setTextViewTotalPenalty(TextView textViewTotalPenalty) {
        this.textViewTotalPenalty = textViewTotalPenalty;
    }

    public TextView getTextViewContentPenalty() {
        return textViewContentPenalty;
    }

    public void setTextViewContentPenalty(TextView textViewContentPenalty) {
        this.textViewContentPenalty = textViewContentPenalty;
    }

    public ImageView getImageViewMinus() {
        return imageViewMinus;
    }

    public void setImageViewMinus(ImageView imageViewMinus) {
        this.imageViewMinus = imageViewMinus;
    }

    public Penalty getPenalty() {
        return penalty;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
        setStringContentPenalty(penalty.getName() + " " + penalty.getAmount() + "€");
        setStringTotalPenalty(String.valueOf(penaltyValue));
    }

    public ZappCardView getZappCardView() {
        return zappCardView;
    }

    public void setZappCardView(ZappCardView zappCardView) {
        this.zappCardView = zappCardView;
    }

    public Member getMember() {
        return member;
    }

    public int getPenaltyValue() {
        return penaltyValue;
    }

    public void setPenaltyValue(int penaltyValue) {
        setStringTotalPenalty(String.valueOf(penaltyValue));
        this.penaltyValue = penaltyValue;
    }

    public void addNumber(){
        setPenaltyValue(this.penaltyValue += 1);
    }

    public void minusNumber(){
        if(getPenaltyValue() != 0) setPenaltyValue(this.penaltyValue += 1);
    }

    public TabPenaltyNew getParentView() {
        return parentView;
    }

    public void setParentView(TabPenaltyNew parentView) {
        this.parentView = parentView;
    }
}