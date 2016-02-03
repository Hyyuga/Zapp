package sn.zapp.util;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.math.BigDecimal;

import de.greenrobot.event.EventBus;
import sn.zapp.R;
import sn.zapp.event.RoundValueEvent;
import sn.zapp.model.Round;

public class RoundValueNew extends LinearLayout {
    private int id;

    private Round round;

    private String stringResult = "";

    private TextInputLayout textInputLayoutResult;
    private EditText editTextResult;

    private final String championshipName;
    private final String memberEmail;

    private final EventBus bus = EventBus.getDefault();

    public RoundValueNew(Context context, String name, String memberEmail, int id) {
        super(context);
        initViews(context);
        this.championshipName = name;
        this.memberEmail = memberEmail;
        setId(id);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tab_championship_round_value_new, this);

        setEditTextResult((EditText) this.findViewById(R.id.editText_Score));
        setTextInputLayoutResult((TextInputLayout) this.findViewById(R.id.input_layout_score));

        initListener();

    }

    private void initListener() {
        getEditTextResult().setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    EditText text = (EditText) v;
                    setStringResult(text.getText().toString());
                    if(getStringResult().length()>0) {
                        RoundValueEvent event = new RoundValueEvent();
                        BigDecimal value = new BigDecimal(getStringResult());
                        BigDecimal multiplier = new BigDecimal(round.getMultiplier());
                        event.setRound(round);
                        event.setValue(value.multiply(multiplier).toString());
                        event.setChampionship(championshipName);
                        event.setMemberEmail(memberEmail);
                        bus.post(event);
                    }
                }
            }
        });
//        getEditTextResult().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (getEditTextResult().getText().toString().compareTo(s.toString()) != 0) {
//                    setStringResult(s.toString());
//                    if (getStringResult().length() > 0) {
//                        RoundValueEvent event = new RoundValueEvent();
//                        BigDecimal value = new BigDecimal(getStringResult());
//                        BigDecimal multiplier = new BigDecimal(round.getMultiplier());//spielleiter gibt direkt endergebnis ein
//                        event.setRound(round);
//                        event.setValue(value.multiply(multiplier).toString());
//                        event.setChampionship(championshipName);
//                        event.setMemberEmail(memberEmail);
//                        bus.post(event);
//                    }
//                }
//            }
//        });
    }


    public String getStringResult() {
        return stringResult;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
        if (this.editTextResult != null)
            editTextResult.setText(stringResult);
    }


    public EditText getEditTextResult() {
        return editTextResult;
    }

    public void setEditTextResult(EditText editTextResult) {
        this.editTextResult = editTextResult;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
//        getEditTextResult().setHint("x " +String.valueOf(round.getMultiplier()));
        getTextInputLayoutResult().setHint("x " +String.valueOf(round.getMultiplier()));
//        getTextInputLayoutResult().setHint("Runde " + String.valueOf(round.getRoundnumber()));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public TextInputLayout getTextInputLayoutResult() {
        return textInputLayoutResult;
    }

    public void setTextInputLayoutResult(TextInputLayout textInputLayoutResult) {
        this.textInputLayoutResult = textInputLayoutResult;
    }
}