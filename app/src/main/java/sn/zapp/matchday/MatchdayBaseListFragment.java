package sn.zapp.matchday;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import sn.zapp.base.BaseListFragment;
import sn.zapp.model.Matchday;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MatchdayBaseListFragment extends BaseListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RealmResults<Matchday> list = realmDBManager.list_all_matchdays();

        MatchdaylistViewAdapter adapter = new MatchdaylistViewAdapter(this.getActivity(), list, true, true);
        adapter.setOnFragmentListener(mListener);
        mUiRecyclerView.setAdapter(adapter);

        return view;
    }
}
