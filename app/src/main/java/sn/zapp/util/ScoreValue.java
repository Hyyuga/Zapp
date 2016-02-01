package sn.zapp.util;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import sn.zapp.R;
import sn.zapp.event.ScoreEvent;
import sn.zapp.model.Member;
import sn.zapp.model.Score;

public class ScoreValue extends LinearLayout {
    private Score score;

    private String stringTotalScore = "";
    private String stringContentScore = "";
    private int scoreValue = 0;

    private ZappCardView zappCardView;
    private TextView textViewTotalScore;
    private TextView textViewContentScore;
    private ImageView imageViewMinus;
    protected static Handler handler;

    private final Member member;

    private final EventBus bus = EventBus.getDefault();

    public ScoreValue(Context context, Member member) {
        super(context);
//        LayoutInflater.from(context).inflate(R.layout.tab_score_value, this);
        initViews(context);
        handler = new Handler();
        this.member = member;
    }
    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tab_score_value, this);

        setTextViewTotalScore((TextView) this.findViewById(R.id.text_view_total_score));
        getTextViewTotalScore().setText(getStringTotalScore());

        setTextViewContentScore((TextView) this.findViewById(R.id.text_view_content_score));
        getTextViewContentScore().setText(getStringContentScore());

        setImageViewMinus((ImageView) this.findViewById(R.id.buttonMinus));
        getImageViewMinus().setImageResource(R.drawable.ic_delete_black_48dp);

        setZappCardView((ZappCardView) this.findViewById(R.id.card_view));

        initListener();

    }

    private void initListener() {
        getZappCardView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (action == MotionEvent.ACTION_UP) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addNumber();
                            ScoreEvent events = new ScoreEvent();
                            events.setMember(getMember());
                            events.setScore(score);
                            events.setAction(Action.ADD);
                            EventBus.getDefault().post(events);
                        }
                    }, 200);

                }
                return false;
            }

        });
        getImageViewMinus().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            minusNumber();
                            ScoreEvent events = new ScoreEvent();
                            events.setMember(getMember());
                            events.setScore(score);
                            events.setAction(Action.ADD);
                            EventBus.getDefault().post(events);
                        }
                    }, 200);

            }

        });
    }

    public String getStringTotalScore() {
        return stringTotalScore;
    }

    public void setStringTotalScore(String stringTotalScore) {
        this.stringTotalScore = stringTotalScore;
        if(this.textViewTotalScore != null)
            textViewTotalScore.setText("Nr. " + stringTotalScore);
    }

    public String getStringContentScore() {
        return stringContentScore;
    }

    public void setStringContentScore(String stringContentScore) {
        this.stringContentScore = stringContentScore;
        if(this.textViewContentScore != null)
            textViewContentScore.setText(stringContentScore);
    }

    public TextView getTextViewTotalScore() {
        return textViewTotalScore;
    }

    public void setTextViewTotalScore(TextView textViewTotalScore) {
        this.textViewTotalScore = textViewTotalScore;
    }

    public TextView getTextViewContentScore() {
        return textViewContentScore;
    }

    public void setTextViewContentScore(TextView textViewContentScore) {
        this.textViewContentScore = textViewContentScore;
    }

    public ImageView getImageViewMinus() {
        return imageViewMinus;
    }

    public void setImageViewMinus(ImageView imageViewMinus) {
        this.imageViewMinus = imageViewMinus;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
        setStringContentScore(score.getName());
        setStringTotalScore(String.valueOf(scoreValue));
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

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        setStringTotalScore(String.valueOf(scoreValue));
        this.scoreValue = scoreValue;
    }

    public void addNumber(){
        setScoreValue(this.scoreValue += 1);
    }

    public void minusNumber(){
        if(getScoreValue() != 0) setScoreValue(this.scoreValue += 1);
    }
}