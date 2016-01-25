package sn.zapp.championship;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import sn.zapp.base.BaseListFragment;
import sn.zapp.model.Championship;

/**
 * Created by Steppo on 20.01.2016.
 */
public class ChampionshipBaseListFragment extends BaseListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        RealmResults<Championship> list = realmDBManager.list_all_championships();

        ChampionshipBaseListViewAdapter adapter = new ChampionshipBaseListViewAdapter(this.getActivity(), list, true, true);
        adapter.setOnFragmentListener(mListener);
        mUiRecyclerView.setAdapter(adapter);

        return view;
    }

//    private RealmResults<Member> asyncLoadMoreQuotes() {
//        RealmResults<Member> results = null;
//        AsyncTask<Void, Void, RealmResults<Member>> remoteItem = new AsyncTask<Void, Void, RealmResults<Member>>() {
//            @Override
//            protected RealmResults<Member> doInBackground(Void... params) {
//                // Add some delay to the refresh/remove action.
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                }
//                return realmDBManager.list_all_members();
//            }
//        };
//        try {
//            results = remoteItem.execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        return  results;
//    }
}
