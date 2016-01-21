package sn.zapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.realm.ZappRealmDBManager;
import sn.zapp.util.Action;

/**
 * Created by Steppo on 19.01.2016.
 */
public abstract class BaseListFragment extends Fragment{

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    protected OnListFragmentInteractionListener mListener;
    protected ZappRealmDBManager realmDBManager;
    protected RealmRecyclerView mUiRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_recycler_list, container, false);
        realmDBManager = new ZappRealmDBManager();
        mUiRecyclerView = (RealmRecyclerView) view.findViewById(R.id.recycler_base_view);

        return view;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        realmDBManager.close();
        super.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(RealmObject item, Action action);
    }

}
