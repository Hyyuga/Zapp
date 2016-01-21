package sn.zapp.penalty;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.base.BaseDetailFragment;
import sn.zapp.model.Penalty;

/**
 * Created by Steppo on 20.01.2016.
 */
public class PenaltyBaseDetailFragment extends BaseDetailFragment{

    private TextInputLayout name, amount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Button buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFields(null);
                Penalty newPenalty = new Penalty(name.getEditText().getText().toString(), amount.getEditText().getText().toString());
                realmDBManager.insertRealmObject(newPenalty);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                boolean pop = manager.popBackStackImmediate();
                baseListActivity.getFab().show();
                baseListActivity.getToolbar().setTitle(getCreateTitle());
            }
        });
        return view;
    }

    @Override
    public void setSelectedListObject(RealmObject argListObject) {
        selectedItem = argListObject;
    }

    @Override
    public int getDetailFragmentLayout() {
        return R.layout.fragment_penalty_detail;
    }

    @Override
    public void initFields(View view) {
        View aView = null;
        if (view != null)
            aView = view;
        else aView = getView();

        name = (TextInputLayout) aView.findViewById(R.id.input_layout_name);
        amount = (TextInputLayout) aView.findViewById(R.id.input_layout_amount);
    }

    @Override
    public void setField(RealmObject object) {
        Penalty penalty = (Penalty) object;
        name.getEditText().setText(penalty.getName());
        amount.getEditText().setText(penalty.getAmount());
    }

    @Override
    public String getEditTitle() {
        return "Strafe bearbeiten";
    }

    @Override
    public String getCreateTitle() {
        return "Strafe anlegen";
    }

    @Override
    public String getActivityTitle() {
        return "Strafen";
    }
}
