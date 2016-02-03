package sn.zapp.base;

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
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import sn.zapp.R;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 20.01.2016.
 */
public abstract class BaseListViewAdapter<T extends RealmObject> extends RealmBasedRecyclerViewAdapter<T, BaseListViewAdapter.ViewHolder> {
    private BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener = null;

    public BaseListViewAdapter(
            Context context,
            RealmResults<T> realmResults,
            boolean automaticUpdate,
            boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }


    @Override
    public BaseListViewAdapter.ViewHolder onCreateRealmViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_base_item, parent, false);

        return new ViewHolder((LinearLayout) view);
    }


    @Override
    public void onBindRealmViewHolder(final BaseListViewAdapter.ViewHolder holder, final int position) {
        CardView cardView = (CardView) holder.mView.findViewById(R.id.card_view);
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (null != getmOnFragmentListener() && action == MotionEvent.ACTION_UP) {
                    getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.EDIT);
                }
                return false;
            }
        });
        holder.mLogo.setImageResource(R.mipmap.zapplogo);
    }

    public BaseListFragment.OnListFragmentInteractionListener getmOnFragmentListener() {
        return mOnFragmentListener;
    }

    public void setOnFragmentListener(BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener) {
        this.mOnFragmentListener = mOnFragmentListener;
    }

    protected class ViewHolder extends RealmViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mLogo;
        public RealmObject mItem;
        public ViewHolder(LinearLayout view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            mLogo = (ImageView) view.findViewById(R.id.logo);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
