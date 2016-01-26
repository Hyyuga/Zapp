package sn.zapp.championship;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import io.realm.RealmList;
import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.base.BaseDetailFragment;
import sn.zapp.model.Championship;
import sn.zapp.model.Round;
import sn.zapp.util.RoundItem;

/**
 * Created by Steppo on 23.01.2016.
 */
public class ChampionshipBaseDetailFragment extends BaseDetailFragment {

    private List<RoundItem> rounds = new ArrayList<>();
    private  int counter;
    private TextInputLayout header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = 1;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  super.onCreateView(inflater, container, savedInstanceState);

        Button buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (RoundItem roundItem: rounds) {
                    roundItem.getEditTextDescription().clearFocus();
                    roundItem.getEditTextRound().clearFocus();
                    roundItem.getEditTextMultiplier().clearFocus();
                }
                initFields(null);
                RealmObject persistObject = createDBObject();
                realmDBManager.insertRealmObject(persistObject);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                boolean pop = manager.popBackStackImmediate();
                baseListActivity.getFab().show();
                baseListActivity.getToolbar().setTitle(getCreateTitle());
                baseListActivity.setSelectedFragment(null);
            }
        });
        Button buttonAdd = (Button) view.findViewById(R.id.buttonAdd);
//        Button buttonRemove = (Button) view.findViewById(R.id.buttonRemove);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView(view, inflater);
            }
        });
//        buttonRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeView(view, inflater);
//            }
//        });


        return view;
    }

    private void addView(View rootView, LayoutInflater inflater) {
        RoundItem layout = new RoundItem(getContext(), counter);
        childRootLayout.addView(layout);
        rounds.add(layout);
        counter +=1;
    }

    @Override
    public void setSelectedListObject(RealmObject listObject) {
        selectedItem = listObject;
    }

    @Override
    public int getDetailFragmentLayout() {
        return R.layout.fragment_championship_detail;
    }

    @Override
    public void initFields(View view) {
        View aView = null;
        if (view != null)
            aView = view;
        else aView = getView();

        header = (TextInputLayout) aView.findViewById(R.id.input_layout_header);
    }

    @Override
    public void setField(RealmObject object) {

        Championship ship = (Championship)object;
        header.getEditText().setText(ship.getName());
        for (Round round : ship.getRounds()) {
            RoundItem roundItem = new RoundItem(getContext(), -12);
            roundItem.setRound(round);
            childRootLayout.addView(roundItem);
            rounds.add(roundItem);
        }
        counter = rounds.size() + 1;
    }

    @Override
    public String getEditTitle() {
        return "Meisterschaft bearbeiten";
    }

    @Override
    public String getCreateTitle() {
        return "Meisterschaft anlegen";
    }

    @Override
    public String getActivityTitle() {
        return "Meisterschaft";
    }

    @Override
    public RealmObject createDBObject() {
        Championship champ = new Championship();
        champ.setName(header.getEditText().getText().toString());
        champ.setRounds(new RealmList<Round>());
        for (RoundItem round : rounds) {
            Round domRound = new Round();
            String number = round.getStringRound();
            String multiplier = round.getStringMultiplier();
            String description = round.getStringDescription();
            domRound.setDescription(description);
            domRound.setMultiplier(Integer.parseInt(multiplier));
            domRound.setRound(Integer.parseInt(number));
            champ.getRounds().add(domRound);
        }
        return champ;
    }
}
