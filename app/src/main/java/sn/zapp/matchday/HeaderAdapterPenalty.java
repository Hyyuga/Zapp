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
import sn.zapp.R;
import sn.zapp.ZappApplication;
import sn.zapp.event.PenaltyEvent;
import sn.zapp.model.Member;
import sn.zapp.model.Penalty;
import sn.zapp.util.Action;

public class HeaderAdapterPenalty extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final List<Penalty> mValues;
    private Member member = null;
    private VHHeader header;

    public HeaderAdapterPenalty(List<Penalty> data, Member member) {
        this.mValues = data;
        this.setMember(member);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tab_penalty_item, parent, false);
            final ViewHolderPenalty viewHolderPenalty = new ViewHolderPenalty(view);
            viewHolderPenalty.mButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addAMount(viewHolderPenalty);
                    viewHolderPenalty.addValue();
                    PenaltyEvent event = new PenaltyEvent();
                    event.setMember(getMember());
                    event.setPenalty(viewHolderPenalty.mItem);
                    event.setAction(Action.ADD);
                    EventBus.getDefault().post(event);
                }
            });
            viewHolderPenalty.mButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   getHeader().removeNumer();
                    viewHolderPenalty.resetValue();
                    PenaltyEvent event = new PenaltyEvent();
                    event.setMember(getMember());
                    event.setPenalty(viewHolderPenalty.mItem);
                    event.setAction(Action.MINUS);
                    EventBus.getDefault().post(event);
                }
            });
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
            viewHolderPenalty.mContentView.setText(mValues.get(position - 1).getName() + " (" + mValues.get(position - 1).getAmount().toString() + " € )");
            viewHolderPenalty.mPenaltyTotal.setText(viewHolderPenalty.mPenaltyValue.toString());
        } else if (holder instanceof VHHeader) {
            VHHeader viewHolderHeader = (VHHeader) holder;
            viewHolderHeader.contentName.setText("Strafen");
            String string = viewHolderHeader.getTotal().toString();
            viewHolderHeader.contentTotal.setText(string + " €");
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

    public class ViewHolderPenalty extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mButtonAdd;
        public final ImageView mButtonMinus;
        public Penalty mItem;
        public final TextView mPenaltyTotal;
        public BigDecimal mPenaltyValue;

        public ViewHolderPenalty(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content_penalty);
            mButtonAdd = (ImageView) view.findViewById(R.id.buttonAdd);
            mButtonMinus = (ImageView) view.findViewById(R.id.buttonMinus);
            mPenaltyTotal = (TextView) view.findViewById(R.id.text_view_total_penalty);
            mPenaltyValue = new BigDecimal(0);
        }

        public void addValue(){
            mPenaltyValue = mPenaltyValue.add(new BigDecimal(1));
            notifyDataSetChanged();
        }
        public void resetValue(){
            mPenaltyValue = new BigDecimal(0);
            notifyDataSetChanged();
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

        public void removeNumer() {
            this.total = new BigDecimal(0).setScale(2);
            notifyDataSetChanged();
        }
    }
}