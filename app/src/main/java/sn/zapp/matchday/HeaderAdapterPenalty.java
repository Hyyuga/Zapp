package sn.zapp.matchday;

import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.RealmList;
import sn.zapp.R;
import sn.zapp.ZappApplication;
import sn.zapp.event.PenaltyEvent;
import sn.zapp.model.Member;
import sn.zapp.model.MemberPenalyValue;
import sn.zapp.model.Penalty;
import sn.zapp.util.Action;

public class HeaderAdapterPenalty extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final List<Penalty> mValues;
    private Member member = null;
    private RealmList<MemberPenalyValue> resultPenalty = null;
    private VHHeader header;
    protected static Handler handler;
    private Action viewState = null;
    private Action viewStateHeader = null;

    public HeaderAdapterPenalty(List<Penalty> data, Member member, RealmList<MemberPenalyValue> resultPenalty, Action viewState) {
        this.mValues = data;
        this.setMember(member);
        this.setResultPenalty(resultPenalty);
        handler = new Handler();
        this.viewState = viewState;
        this.viewStateHeader = viewState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tab_penalty_item, parent, false);
            final ViewHolderPenalty viewHolderPenalty = new ViewHolderPenalty(view);
            if (!viewState.equals(Action.SHOW)) {
                CardView cardView = (CardView) viewHolderPenalty.mView.findViewById(R.id.card_view);
                cardView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int action = event.getActionMasked();
                        if (action == MotionEvent.ACTION_UP) {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    addAMount(viewHolderPenalty);
                                    viewHolderPenalty.addValue();
                                    PenaltyEvent events = new PenaltyEvent();
                                    events.setMember(getMember());
                                    events.setPenalty(viewHolderPenalty.mItem);
                                    events.setAction(Action.ADD);
                                    EventBus.getDefault().post(events);
                                }
                            }, 200);

                        }
                        return false;
                    }

                });
                viewHolderPenalty.mButtonMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolderPenalty.resetValue();
                        PenaltyEvent event = new PenaltyEvent();
                        event.setMember(getMember());
                        event.setPenalty(viewHolderPenalty.mItem);
                        getHeader().removeNumer(new BigDecimal(viewHolderPenalty.mItem.getAmount()));
                        event.setAction(Action.MINUS);
                        EventBus.getDefault().post(event);
                    }
                });
            } else {
                viewHolderPenalty.mButtonMinus.setVisibility(View.GONE);
            }
            return viewHolderPenalty;
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tab_penalty_header, parent, false);
            VHHeader header = new VHHeader(view);
            setHeader(header);
            return header;
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderPenalty) {
            final ViewHolderPenalty viewHolderPenalty = (ViewHolderPenalty) holder;
            viewHolderPenalty.mItem = getItem(position);
            String penaltyTotal = null;
            if (!viewState.equals(Action.CREATE) && !viewState.equals(Action.EDITCREATE)) {
                for (MemberPenalyValue result : getResultPenalty()) {
                    if (result.getPenalty().equals(viewHolderPenalty.mItem)) {
                        penaltyTotal = result.getDbValue();
                    }
                }
                if (penaltyTotal == null)
                    penaltyTotal = viewHolderPenalty.mPenaltyValue.toString();
                if (viewState.equals(Action.EDIT.name()))
                    viewState = Action.EDITCREATE;
            }
            if (penaltyTotal == null)
                penaltyTotal = viewHolderPenalty.mPenaltyValue.toString();

            viewHolderPenalty.mPenaltyValue = new BigDecimal(penaltyTotal);
            viewHolderPenalty.mContentView.setText(mValues.get(position - 1).getName() + " (" + mValues.get(position - 1).getAmount().toString() + " € )");
            viewHolderPenalty.mPenaltyTotal.setText(penaltyTotal);
        } else if (holder instanceof VHHeader) {
            VHHeader viewHolderHeader = (VHHeader) holder;
            viewHolderHeader.contentName.setText("Strafen");
            String penaltyHeaderTotal = null;
            if (!viewStateHeader.equals(Action.CREATE) && !viewStateHeader.equals(Action.EDITCREATE)) {
                BigDecimal result = new BigDecimal(0).setScale(2);
                for (MemberPenalyValue resultValue : getResultPenalty()) {
                    BigDecimal result1 = new BigDecimal(0);
                    result1 = result1.add(resultValue.getValue());
                    result1 = result1.multiply(new BigDecimal(resultValue.getPenalty().getAmount()));
                    result = result.add(result1);
                }

                penaltyHeaderTotal = result.toString();
                if (viewStateHeader.equals(Action.EDIT.name()))
                    viewStateHeader = Action.EDITCREATE;
            }
            if (penaltyHeaderTotal == null)
                penaltyHeaderTotal = viewHolderHeader.total.toString();

            viewHolderHeader.total = new BigDecimal(penaltyHeaderTotal);
            viewHolderHeader.contentTotal.setText(penaltyHeaderTotal);
        }
    }

    private void addAMount(ViewHolderPenalty viewHolderPenalty) {
        String penaltyamount = viewHolderPenalty.mItem.getAmount();
        Integer intAmount = null;
        Double doubleAmount = null;
        BigDecimal amount = null;
        if (ZappApplication.isNumeric(penaltyamount).equals(Integer.class))
            amount = new BigDecimal(Integer.parseInt(penaltyamount));
        else {
            doubleAmount = Double.parseDouble(penaltyamount);
            amount = BigDecimal.valueOf(doubleAmount);
        }

        getHeader().setNumber(amount);
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Penalty getItem(int position) {
        return mValues.get(position - 1);
    }

    public VHHeader getHeader() {
        return header;
    }

    public void setHeader(VHHeader header) {
        this.header = header;
    }

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

    public class ViewHolderPenalty extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mButtonMinus;
        public Penalty mItem;
        public final TextView mPenaltyTotal;
        public BigDecimal mPenaltyValue;
        EventBus bus = EventBus.getDefault();

        public ViewHolderPenalty(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content_penalty);
            mButtonMinus = (ImageView) view.findViewById(R.id.buttonMinus);
            mPenaltyTotal = (TextView) view.findViewById(R.id.text_view_total_penalty);
            mPenaltyValue = new BigDecimal(0);
        }

        public void addValue() {
            mPenaltyValue = mPenaltyValue.add(new BigDecimal(1));
            notifyDataSetChanged();
        }

        public void resetValue() {
            if(!mPenaltyValue.equals(new BigDecimal(0))) {
                mPenaltyValue = mPenaltyValue.subtract(new BigDecimal(1));
                notifyDataSetChanged();
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        public final TextView contentName;
        public final TextView contentTotal;
        private BigDecimal total;


        public VHHeader(View itemView) {
            super(itemView);
            total = new BigDecimal(0).setScale(2);
            contentName = (TextView) itemView.findViewById(R.id.content_header_penalty);
            contentTotal = (TextView) itemView.findViewById(R.id.content_header_amount);
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setNumber(BigDecimal number) {
            this.total = total.add(number);
            notifyDataSetChanged();
        }

        public void removeNumer(BigDecimal number) {
            this.total = total.subtract(number);
            notifyDataSetChanged();
        }
    }
}