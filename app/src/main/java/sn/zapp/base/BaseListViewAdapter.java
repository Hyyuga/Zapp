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
public abstract class BaseListViewAdapter extends RealmBasedRecyclerViewAdapter<RealmObject, BaseListViewAdapter.ViewHolder> {
    private BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener = null;


    public BaseListViewAdapter(
            Context context,
            RealmResults<RealmObject> realmResults,
            boolean automaticUpdate,
            boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }


    @Override
    public BaseListViewAdapter.ViewHolder onCreateRealmViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_member_item, parent, false);

        return new ViewHolder((LinearLayout) view);
    }

    @Override
    public void onBindRealmViewHolder(final ViewHolder holder, final int position) {
        final RealmObject object = realmResults.get(position);
        holder.mItem = object;
        holder.mContentView.setText(getBindHolderContentText());
        CardView cardView = (CardView) holder.mView.findViewById(R.id.card_view);
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (null != getmOnFragmentListener() && action == MotionEvent.ACTION_DOWN) {
                    getmOnFragmentListener().onListFragmentInteraction(holder.mItem, Action.EDIT);
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
    }

    public BaseListFragment.OnListFragmentInteractionListener getmOnFragmentListener() {
        return mOnFragmentListener;
    }

    public void setOnFragmentListener(BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener) {
        this.mOnFragmentListener = mOnFragmentListener;
    }

    abstract protected String getBindHolderContentText();

    class ViewHolder extends RealmViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mButtonEdit;
        public final ImageView mButtonDelete;
        public RealmObject mItem;
        public ViewHolder(LinearLayout view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            mButtonEdit = (ImageView) view.findViewById(R.id.buttonEdit);
            mButtonDelete = (ImageView) view.findViewById(R.id.buttonDelete);
        }
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
