package sn.zapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.realm.ZappRealmDBManager;

/**
 * Created by Steppo on 19.01.2016.
 */
public abstract class BaseDetailFragment extends Fragment{

    protected ZappRealmDBManager realmDBManager = null;
    protected BaseListActivity baseListActivity;
    protected RealmObject selectedItem = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getDetailFragmentLayout(), container, false);

        realmDBManager = new ZappRealmDBManager();
        if (getSelectedItem() != null) {
            initFields(view);
            setField(getSelectedItem());
            baseListActivity.getToolbar().setTitle(getEditTitle());
        } else baseListActivity.getToolbar().setTitle(getCreateTitle());

        Button buttonCancel = (Button) view.findViewById(R.id.buttonAbbrechen);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                boolean pop = manager.popBackStackImmediate();
                baseListActivity.getFab().show();
                baseListActivity.getToolbar().setTitle(getActivityTitle());

            }
        });
        return view;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            baseListActivity = (BaseListActivity) context;
        }
    }

    @Override
    public void onDestroy() {
        realmDBManager.close();
        super.onDestroy();
    }

    public RealmObject getSelectedItem() {
        return selectedItem;
    }

    protected void setSelectedItem(RealmObject selectedItem) {
        this.selectedItem = selectedItem;
    }


    public abstract void setSelectedListObject(RealmObject listObject);
    public abstract int getDetailFragmentLayout();
    public abstract void initFields(View view);
    public abstract void setField(RealmObject object);
    public abstract String getEditTitle();
    public abstract String getCreateTitle();
    public abstract String getActivityTitle();
}
