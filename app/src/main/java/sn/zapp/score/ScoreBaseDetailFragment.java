package sn.zapp.score;

import android.support.design.widget.TextInputLayout;
import android.view.View;

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

    @Override
    public RealmObject createDBObject() {
        return new Score(name.getEditText().getText().toString());
    }
}
