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
import sn.zapp.base.BaseListFragment;
import sn.zapp.model.Matchday;
import sn.zapp.util.Action;

public class MatchdaylistViewAdapter extends RealmBasedRecyclerViewAdapter<Matchday, MatchdaylistViewAdapter.ViewHolder> {

    private BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener = null;


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
                .inflate(R.layout.fragment_base_item, parent, false);

        return new ViewHolder((LinearLayout) view);
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
                if (null != getmOnFragmentListener() && action == MotionEvent.ACTION_UP) {
                    getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.SHOW);
                }
                return false;
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.EDIT);
                return false;
            }
        });
//        holder.mButtonEdit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (null != getmOnFragmentListener()) {
//                    getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.EDIT);
//                }
//            }
//        });
        holder.mLogo.setImageResource(R.mipmap.zapplogo);
    }

    public BaseListFragment.OnListFragmentInteractionListener getmOnFragmentListener() {
        return mOnFragmentListener;
    }

    public void setOnFragmentListener(BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener) {
        this.mOnFragmentListener = mOnFragmentListener;
    }

    public static class ViewHolder extends RealmViewHolder {
        public final View mView;
        public final TextView mContentView;
//        public final ImageView mButtonEdit;
        public final ImageView mLogo;
        public Matchday mItem;

        public ViewHolder(LinearLayout view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
//            mButtonEdit = (ImageView) view.findViewById(R.id.buttonEdit);
            mLogo = (ImageView) view.findViewById(R.id.logo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
