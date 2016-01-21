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
import sn.zapp.member.MemberlistFragment;
import sn.zapp.model.Member;
import sn.zapp.util.Action;

public class MatchdaylistViewAdapter extends RealmBasedRecyclerViewAdapter<Member, MatchdaylistViewAdapter.ViewHolder> {

    private MemberlistFragment.OnListFragmentInteractionListener mOnFragmentListener = null;


    public MatchdaylistViewAdapter(
            Context context,
            RealmResults<Member> realmResults,
            boolean automaticUpdate,
            boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }


    @Override
    public MatchdaylistViewAdapter.ViewHolder onCreateRealmViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_member_item, parent, false);

        return new ViewHolder((LinearLayout)view);
    }

    @Override
    public void onBindRealmViewHolder(final ViewHolder holder, final int position) {
        final Member member = realmResults.get(position);
        holder.mItem = member;
        holder.mContentView.setText(member.getVorname() + " " + member.getNachname());
        CardView cardView = (CardView) holder.mView.findViewById(R.id.card_view);
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (null != getmOnFragmentListener() &&  action == MotionEvent.ACTION_DOWN) {
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

    public MemberlistFragment.OnListFragmentInteractionListener getmOnFragmentListener() {
        return mOnFragmentListener;
    }

    public void setOnFragmentListener(MemberlistFragment.OnListFragmentInteractionListener mOnFragmentListener) {
        this.mOnFragmentListener =  mOnFragmentListener;
    }

    public static class ViewHolder extends RealmViewHolder{
        public final View mView;
//        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mButtonEdit;
        public final ImageView mButtonDelete;
        public Member mItem;

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
