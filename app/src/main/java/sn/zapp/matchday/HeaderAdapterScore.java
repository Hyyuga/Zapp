package sn.zapp.matchday;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.event.ScoreEvent;
import sn.zapp.model.Member;
import sn.zapp.model.MemberPenalyValue;
import sn.zapp.model.MemberScoreValue;
import sn.zapp.model.Score;
import sn.zapp.util.Action;

public class HeaderAdapterScore extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private final List<Score> mValues;
    private RealmList<MemberScoreValue> resultScore;
    private Member member = null;

    public HeaderAdapterScore(List<Score> data, Member member, RealmList<MemberScoreValue> resultPenalty) {
        this.mValues = data;
        this.setMember(member);
        this.setResultScore(resultPenalty);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tab_score_item, parent, false);

        final ViewHolderScore viewHolderScore = new ViewHolderScore(view);
        if(getResultScore()== null) {
            viewHolderScore.mButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolderScore.addValue();
                    ScoreEvent event = new ScoreEvent();
                    event.setMember(getMember());
                    event.setScore(viewHolderScore.mItem);
                    event.setAction(Action.ADD);
                    EventBus.getDefault().post(event);
                }
            });
            viewHolderScore.mButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolderScore.resetValue();
                    ScoreEvent event = new ScoreEvent();
                    event.setMember(getMember());
                    event.setScore(viewHolderScore.mItem);
                    event.setAction(Action.MINUS);
                }
            });
        }
        return viewHolderScore;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderScore) {
            final ViewHolderScore viewHolderScore = (ViewHolderScore) holder;
            Score score = getItem(position);
            viewHolderScore.mItem = getItem(position);
            String scoreTotal = null;
            if(getResultScore() != null) {
                for (MemberScoreValue result : getResultScore()) {
                    if(result.getScore().equals(viewHolderScore.mItem)){
                        scoreTotal = result.getDbValue();
                    }
                }
                if(scoreTotal == null) scoreTotal = viewHolderScore.mScoreValue.toString();
            }
            if(scoreTotal == null)scoreTotal =  viewHolderScore.mScoreValue.toString();
            viewHolderScore.mScoreText.setText(score.getName());
            viewHolderScore.mScoreTotal.setText(scoreTotal);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    private Score getItem(int position) {
        return mValues.get(position);
    }

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

    public class ViewHolderScore extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mScoreText;
        public final TextView mScoreTotal;
        public BigDecimal mScoreValue;
        public final ImageView mButtonAdd;
        public final ImageView mButtonMinus;
        public Score mItem;

        public ViewHolderScore(View view) {
            super(view);
            mScoreValue = new BigDecimal(0);
            mView = view;
            mScoreText = (TextView) view.findViewById(R.id.content_score);
            mButtonAdd = (ImageView) view.findViewById(R.id.buttonAdd);
            mButtonMinus = (ImageView) view.findViewById(R.id.buttonMinus);
            mScoreTotal = (TextView) view.findViewById(R.id.text_view_total_score);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mScoreText.getText() + "'";
        }

        public void addValue(){
            mScoreValue = mScoreValue.add(new BigDecimal(1));
            notifyDataSetChanged();
        }
        public void resetValue(){
            mScoreValue = new BigDecimal(0);
            notifyDataSetChanged();
        }

    }
}