package sn.zapp.member;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import io.realm.RealmObject;
import sn.zapp.R;
import sn.zapp.base.BaseDetailFragment;
import sn.zapp.event.DatePickerEvent;
import sn.zapp.model.Member;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MemberBaseDetailFragment extends BaseDetailFragment{

    private TextInputLayout vorname, nachname, email, address, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(getSelectedItem() == null)setSelectedItem(new Member());
        Button buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
        date = (TextInputLayout) view.findViewById(R.id.input_layout_date);
        date.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getChildFragmentManager(), "datePicker");
                }
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFields(null);
                setSelectedListObject(new Member(vorname.getEditText().getText().toString(), nachname.getEditText().getText().toString(), date.getEditText().getText().toString(), email.getEditText().getText().toString(), address.getEditText().getText().toString()));
                realmDBManager.insertRealmObject((Member)getSelectedItem());
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

//    public final Member getSelectedMember(){
//        return (Member)getSelectedItem();
//    }

    public void onEvent(DatePickerEvent event){
        ((Member) getSelectedItem()).setGeburtstag(event.getDate());
        date.getEditText().setText(event.getDate());
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
        date = (TextInputLayout) aView.findViewById(R.id.input_layout_date);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog_MinWidth, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String date = df.format(new Date(year, month, day));
            EventBus.getDefault().post(new DatePickerEvent(date));
        }
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
        date.getEditText().setText(member.getGeburtstag());
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
