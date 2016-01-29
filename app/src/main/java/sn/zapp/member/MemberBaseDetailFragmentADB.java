package sn.zapp.member;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import sn.zapp.databinding.FragmentMemberDetailAdbBinding;
import sn.zapp.event.DatePickerEvent;
import sn.zapp.model.Member;

/**
 * Created by Steppo on 20.01.2016.
 */
public class MemberBaseDetailFragmentADB extends BaseDetailFragment {

    private TextInputLayout vorname, nachname, email, address, date;
    private Date utilDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final FragmentMemberDetailAdbBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_member_detail_adb, container, false);
        binding.setMember((Member) getSelectedItem());
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Member member = new Member(binding.editTextFirstName.getText().toString(), binding.editTextLastName.getText().toString(), binding.editTextDate.getText().toString(), binding.editTextEmail.getText().toString(), binding.editTextAdresse.getText().toString());
                realmDBManager.insertRealmObject(member);
                onBack();
            }
        });
        return binding.getRoot();
    }

    public void onEvent(DatePickerEvent event) {
        ((Member) getSelectedItem()).setBirthday(event.getDate());
        date.getEditText().setText(event.getDate());
    }

    @Override
    public void setSelectedListObject(RealmObject argListObject) {
        selectedItem = argListObject;
    }

    @Override
    public int getDetailFragmentLayout() {
        return R.layout.fragment_member_detail_adb;
    }

    @Override
    public void initFields(View view) {
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
            return new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String date = df.format(new Date(year - 1900, month, day));
            EventBus.getDefault().post(new DatePickerEvent(date));
        }
    }

    private boolean validateEmailasd(Pattern pattern, Matcher matcher, String email) {
        matcher = pattern.matcher(email);
        boolean b = matcher.matches();
        return b;
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
    public void setField(RealmObject object) {
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
