package sn.zapp.matchday;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmResults;
import sn.zapp.R;
import sn.zapp.model.Matchday;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;

/**
 * A placeholder fragment containing a simple view.
 */
public class MatchdayListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private static final int VERTICAL_ITEM_SPACE = 48;
    private OnListFragmentInteractionListener mListener;
    private ZappRealmDBManager realmDBManager;
    private RealmRecyclerView mUiRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    public MatchdayListFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_matchday_recycler_list, container, false);
        realmDBManager = new ZappRealmDBManager();
        mUiRecyclerView = (RealmRecyclerView) view.findViewById(R.id.recycler_view_matchday);

        RealmResults<Matchday> list = realmDBManager.list_all_matchdays();
        MatchdaylistViewAdapter adapter = new MatchdaylistViewAdapter(this.getActivity(), list, true, true);
        adapter.setOnFragmentListener(mListener);
        mUiRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Matchday item, Action action);
    }
}
