package sn.zapp.score;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import sn.zapp.base.BaseListFragment;
import sn.zapp.model.Score;

/**
 * Created by Steppo on 20.01.2016.
 */
public class ScoreBaseListFragment extends BaseListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RealmResults<Score> list = realmDBManager.list_all_scores();

        ScoreBaseListViewAdapter adapter = new ScoreBaseListViewAdapter(this.getActivity(), list, true, true);
        adapter.setOnFragmentListener(mListener);
        mUiRecyclerView.setAdapter(adapter);

        return view;
    }
}
