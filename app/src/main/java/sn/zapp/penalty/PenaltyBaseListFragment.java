package sn.zapp.penalty;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import sn.zapp.base.BaseListFragment;
import sn.zapp.model.Member;

/**
 * Created by Steppo on 20.01.2016.
 */
public class PenaltyBaseListFragment extends BaseListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RealmResults<Member> list = realmDBManager.list_all_members();

        MemberBaseListViewAdapter adapter = new MemberBaseListViewAdapter(this.getActivity(), list, true, true);
        adapter.setOnFragmentListener(mListener);
        mUiRecyclerView.setAdapter(adapter);

        return view;
    }
}
