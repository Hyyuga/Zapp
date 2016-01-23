package sn.zapp.member;

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
import sn.zapp.model.Member;
import sn.zapp.util.Action;

public class MemberListViewAdapter extends RealmBasedRecyclerViewAdapter<Member, MemberListViewAdapter.ViewHolder> {

    private BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener = null;

    public MemberListViewAdapter(
            Context context,
            RealmResults<Member> realmResults,
            boolean automaticUpdate,
            boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }
    @Override
    public MemberListViewAdapter.ViewHolder onCreateRealmViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_member_item, parent, false);
        return new ViewHolder((LinearLayout)view);
    }
    @Override
    public void onBindRealmViewHolder(final ViewHolder holder, final int position) {
        final Member member = realmResults.get(position);
        holder.mItem = member;
        holder.mTextViewName.setText(member.getVorname() + " " + member.getNachname());
        holder.mTextViewEmail.setText(member.getEmail());
        holder.mTextViewBirthday.setText(member.getGeburtstag());
        holder.mImageViewPerson.setImageResource(R.mipmap.zapplogo);
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
    }
    public BaseListFragment.OnListFragmentInteractionListener getmOnFragmentListener() {
        return mOnFragmentListener;
    }

    public void setOnFragmentListener(BaseListFragment.OnListFragmentInteractionListener mOnFragmentListener) {
        this.mOnFragmentListener =  mOnFragmentListener;
    }
    public static class ViewHolder extends RealmViewHolder{
        public final View mView;
        public final ImageView mImageViewPerson;
        public final TextView mTextViewName;
        public final TextView mTextViewEmail;
        public final TextView mTextViewBirthday;
        public Member mItem;
        public ViewHolder(LinearLayout view) {
            super(view);
            mView = view;
            mTextViewEmail = (TextView) view.findViewById(R.id.tv_member_email);
            mTextViewBirthday = (TextView) view.findViewById(R.id.tv__member_birthday);
            mTextViewName = (TextView) view.findViewById(R.id.tv_member_name);
            mImageViewPerson = (ImageView) view.findViewById(R.id.imagePerson);
        }
    }
}
