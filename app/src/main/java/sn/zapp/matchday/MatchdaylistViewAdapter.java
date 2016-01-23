package sn.zapp.matchday;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import sn.zapp.R;
import sn.zapp.model.Matchday;
import sn.zapp.util.Action;

public class MatchdaylistViewAdapter extends RealmBasedRecyclerViewAdapter<Matchday, MatchdaylistViewAdapter.ViewHolder> {

    private MatchdayListFragment.OnListFragmentInteractionListener mOnFragmentListener = null;


    public MatchdaylistViewAdapter(
            Context context,
            RealmResults<Matchday> realmResults,
            boolean automaticUpdate,
            boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }


    @Override
    public MatchdaylistViewAdapter.ViewHolder onCreateRealmViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_matchday_item, parent, false);

        return new ViewHolder((LinearLayout)view);
    }

    @Override
    public void onBindRealmViewHolder(final ViewHolder holder, final int position) {
        final Matchday matchday = realmResults.get(position);
        holder.mItem = matchday;
        holder.mContentView.setText(matchday.getDatum());
        CardView cardView = (CardView) holder.mView.findViewById(R.id.card_view);
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (null != getmOnFragmentListener() &&  action == MotionEvent.ACTION_UP) {
                    getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.SHOW);
                }
                return false;
            }
        });
        holder.mButtonEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != getmOnFragmentListener()) {
                    getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.EDIT);
                }
            }
        });
        holder.mButtonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.DELETE);
            }
        });
        holder.mLogo.setImageResource(R.mipmap.zapplogo);
    }

    public MatchdayListFragment.OnListFragmentInteractionListener getmOnFragmentListener() {
        return mOnFragmentListener;
    }

    public void setOnFragmentListener(MatchdayListFragment.OnListFragmentInteractionListener mOnFragmentListener) {
        this.mOnFragmentListener =  mOnFragmentListener;
    }

    public static class ViewHolder extends RealmViewHolder{
        public final View mView;
        public final TextView mContentView;
        public final ImageView mButtonEdit;
        public final ImageView mButtonDelete;
        public final ImageView mLogo;
        public Matchday mItem;

        public ViewHolder(LinearLayout view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            mButtonEdit = (ImageView) view.findViewById(R.id.buttonEdit);
            mButtonDelete = (ImageView) view.findViewById(R.id.buttonDelete);
            mLogo = (ImageView) view.findViewById(R.id.logo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
