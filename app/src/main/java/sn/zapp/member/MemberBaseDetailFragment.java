package sn.zapp.member;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.base.BaseDetailFragment;
import sn.zapp.model.Member;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MemberBaseDetailFragment extends BaseDetailFragment{

    private TextInputLayout vorname, nachname, email, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Button buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFields(null);
                Member newMember = new Member(vorname.getEditText().getText().toString(), nachname.getEditText().getText().toString(), null, email.getEditText().getText().toString(), address.getEditText().getText().toString());
                realmDBManager.insertRealmObject(newMember);
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
        return R.layout.fragment_member_detail;
    }

    @Override
    public void initFields(View view) {
        View aView = null;
        if (view != null)
            aView = view;
        else aView = getView();

        vorname = (TextInputLayout) aView.findViewById(R.id.input_layout_firstname);
        nachname = (TextInputLayout) aView.findViewById(R.id.input_layout_lastname);
        email = (TextInputLayout) aView.findViewById(R.id.input_layout_email);
        address = (TextInputLayout) aView.findViewById(R.id.input_layout_adress);

    }

    private boolean validateEmailasd(Pattern pattern, Matcher matcher, String email) {
        matcher = pattern.matcher(email);
        boolean b =  matcher.matches();
        return  b;
    }

    @Override
    public void setField(RealmObject object) {
        Member member = (Member) object;
        vorname.getEditText().setText(member.getVorname());
        nachname.getEditText().setText(member.getNachname());
        email.getEditText().setText(member.getEmail());
        address.getEditText().setText(member.getAdresse());
    }

    private boolean validateEmail(){
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = null;
        if (!validateEmailasd(pattern, matcher, email.getEditText().toString())) {
            email.setError("Not a valid email address!");
            return true;
        }
        return true;
    }

    @Override
    public String getEditTitle() {
        return "Mitglied bearbeiten";
    }

    @Override
    public String getCreateTitle() {
        return "Mitglieder anlegen";
    }

    @Override
    public String getActivityTitle() {
        return "Mitglieder";
    }
}