package sn.zapp.matchday.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sn.zapp.R;
import sn.zapp.model.Penalty;

public class PenaltyListViewAdapter extends RecyclerView.Adapter<PenaltyListViewAdapter.ViewHolder> {

    private final List<Penalty> mValues;


    public PenaltyListViewAdapter(List<Penalty> mValues) {
        this.mValues = mValues;
    }

    @Override
    public PenaltyListViewAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tab_penalty_item, parent, false);


            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getName() + " (" + mValues.get(position).getAmount().toString() +")");
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<Penalty> getmValues(){
        return mValues;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        //        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mButtonAdd;
        public final ImageView mButtonMinus;
        public Penalty mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content_penalty);
            mButtonAdd = (ImageView) view.findViewById(R.id.buttonAdd);
            mButtonMinus = (ImageView) view.findViewById(R.id.buttonMinus);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
