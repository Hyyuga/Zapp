package sn.zapp.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.RealmObject;
import io.realm.RealmResults;
import sn.zapp.R;
import sn.zapp.base.BaseListViewAdapter;
import sn.zapp.model.Member;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MemberBaseListViewAdapter extends BaseListViewAdapter {
    public MemberBaseListViewAdapter(Context context, RealmResults<Member> realmResults, boolean automaticUpdate, boolean animateIdType) {
        super(context, realmResults, automaticUpdate, animateIdType);
    }

//    @Override
//    public ViewHolder onCreateRealmViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_member_item, parent, false);
//
//        return new ViewHolder((LinearLayout) view);
//    }

    @Override
    public void onBindRealmViewHolder(ViewHolder holder, int position) {
        final RealmObject object = realmResults.get(position);
//        NewViewHolder newHolder = (NewViewHolder) holder;
        Member member = (Member) object;
        holder.mItem = member;
        holder.mContentView.setText(member.getVorname() + " " + member.getNachname());
//        newHolder.mTextViewEmail.setText(member.getVorname() + " " + member.getEmail());
//        newHolder.mTextViewBirthday.setText(member.getVorname() + " " + member.getGeburtstag());
        super.onBindRealmViewHolder(holder, position);
    }

//    public class NewViewHolder extends BaseListViewAdapter<Member>.ViewHolder{
//        public final TextView mTextViewEmail;
//        public final TextView mTextViewBirthday;
//        public NewViewHolder(LinearLayout view) {
//            super(view);
//            mTextViewEmail = (TextView) view.findViewById(R.id.tv_member_email);
//            mTextViewBirthday = (TextView) view.findViewById(R.id.tv__member_birthday);
//        }
//    }

    @Override
    protected void setBindHolderContentText() {
    }
}
