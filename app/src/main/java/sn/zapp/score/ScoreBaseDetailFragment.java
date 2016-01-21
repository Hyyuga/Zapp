package sn.zapp.score;

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
import sn.zapp.model.Score;

/**
 * Created by Steppo on 20.01.2016.
 */
public class ScoreBaseDetailFragment extends BaseDetailFragment{

    private TextInputLayout name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Button buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFields(null);
                Score newScore = new Score(name.getEditText().getText().toString());
                realmDBManager.insertRealmObject(newScore);
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
        return R.layout.fragment_score_detail;
    }

    @Override
    public void initFields(View view) {
        View aView = null;
        if (view != null)
            aView = view;
        else aView = getView();

        name = (TextInputLayout) aView.findViewById(R.id.input_layout_name);
    }

    @Override
    public void setField(RealmObject object) {
        Score score = (Score) object;
        name.getEditText().setText(score.getName());
    }

    @Override
    public String getEditTitle() {
        return "Erfolg bearbeiten";
    }

    @Override
    public String getCreateTitle() {
        return "Erfolg anlegen";
    }

    @Override
    public String getActivityTitle() {
        return "Erfolge";
    }
}
