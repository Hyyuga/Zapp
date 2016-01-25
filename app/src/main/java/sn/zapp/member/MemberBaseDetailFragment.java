package sn.zapp.member;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.View;
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
public class MemberBaseDetailFragment extends BaseDetailFragment {

    private TextInputLayout vorname, nachname, email, address, date;
    private Date utilDate;

    @Override
    public void setSelectedListObject(RealmObject argListObject) {
        selectedItem = argListObject;
    }

    @Override
    public int getDetailFragmentLayout() {
        return R.layout.fragment_member_detail;
    }

    public void onEvent(DatePickerEvent event) {
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
            String date = df.format(new Date(year-1900, month, day));
            EventBus.getDefault().post(new DatePickerEvent(date));
        }
    }

    private boolean validateEmailasd(Pattern pattern, Matcher matcher, String email) {
        matcher = pattern.matcher(email);
        boolean b = matcher.matches();
        return b;
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

    private boolean validateEmail() {
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
    public String getTagText() {
        return null;
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

    @Override
    public RealmObject createDBObject() {
        return new Member(vorname.getEditText().getText().toString(), nachname.getEditText().getText().toString(), date.getEditText().getText().toString(), email.getEditText().getText().toString(), address.getEditText().getText().toString());
    }
}

//Calendar now = Calendar.getInstance();
//final com.wdullaer.materialdatetimepicker.date.DatePickerDialog d = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
//        new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
//                Calendar checkedCalendar = Calendar.getInstance();
//                checkedCalendar.set(year, monthOfYear, dayOfMonth);
//                utilDate = checkedCalendar.getTime();
//                date.getEditText().setText(DateFormatter.convertDateToString(utilDate));
//            }
//        },
//        now.get(Calendar.YEAR),
//        now.get(Calendar.MONTH),
//        now.get(Calendar.DAY_OF_MONTH)
//);
//d.setMaxDate(now);
//        d.show((getActivity()).getFragmentManager(), this.getClass().getName());
//