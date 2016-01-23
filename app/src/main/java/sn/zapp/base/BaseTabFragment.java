package sn.zapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmList;
import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.realm.ZappRealmDBManager;

/**
 * Created by Steppo on 22.01.2016.
 */
public abstract class BaseTabFragment extends Fragment {

    private RealmObject member;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ZappRealmDBManager realmDBManager = null;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mUiRecyclerView;
    private RealmList<RealmObject> objectResult = null;
    public BaseTabFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public abstract BaseTabFragment newInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_penalty, container, false);
        realmDBManager = new ZappRealmDBManager();
        mUiRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_penalty);

        mUiRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mUiRecyclerView.setLayoutManager(mLinearLayoutManager);
        mUiRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        if(realmDBManager != null) realmDBManager.close();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public RealmObject getRealmObject() {
        return member;
    }

    public void setRealmObject(RealmObject member) {
        this.member = member;
    }

    public RealmList<RealmObject> getObjectResult() {
        return objectResult;
    }

    public void setObjectResult(RealmList<RealmObject> objectResult) {
        this.objectResult = objectResult;
    }
    
}
