package sn.zapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.math.BigDecimal;

import de.greenrobot.event.EventBus;
import sn.zapp.R;
import sn.zapp.event.RoundValueEvent;
import sn.zapp.model.Round;

public class RoundValue extends LinearLayout {
    private int id;

    private Round round;

    private String stringRound = "";
    private String stringResult = "";
    private String stringDescription = "";

    private EditText editTextRound;
    private EditText editTextResult;
    private EditText editTextDescription;

    private final String championshipName;
    private final String memberEmail;

    private final EventBus bus = EventBus.getDefault();

    public RoundValue(Context context, String name, String memberEmail) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.tab_championship_round_value, this);
        initViews(context);
        this.championshipName = name;
        this.memberEmail = memberEmail;
    }
    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.fragment_championship_round_item, this);

        setEditTextRound((EditText) this.findViewById(R.id.text_view_round));
        getEditTextRound().setText(getStringRound());

        setEditTextResult((EditText) this.findViewById(R.id.editText_result));
        getEditTextResult().setText(getStringResult());

        setEditTextDescription((EditText) this.findViewById(R.id.editText_description));
        getEditTextDescription().setText(getStringDescription());

        initListener();

    }

    private void initListener() {
        getEditTextResult().setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    EditText text = (EditText) v;
                    setStringResult(text.getText().toString());
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
        });
    }

    public String getStringRound() {
        return stringRound;
    }

    public void setStringRound(String stringRound) {
        this.stringRound = stringRound;
        if(this.editTextRound != null)
            editTextRound.setText("Runde " + stringRound);
    }

    public String getStringResult() {
        return stringResult;
    }

    public void setStringResult(String stringResult) {
        this.stringResult = stringResult;
        if(this.editTextResult != null)
            editTextResult.setText(stringResult);
    }

    public String getStringDescription() {
        return stringDescription;
    }

    public void setStringDescription(String stringDescription) {
        this.stringDescription = stringDescription;
        if(this.editTextDescription != null)
            editTextDescription.setText(stringDescription);
    }

    public EditText getEditTextRound() {
        return editTextRound;
    }

    public void setEditTextRound(EditText editTextRound) {
        this.editTextRound = editTextRound;
    }

    public EditText getEditTextResult() {
        return editTextResult;
    }

    public void setEditTextResult(EditText editTextResult) {
        this.editTextResult = editTextResult;
    }

    public EditText getEditTextDescription() {
        return editTextDescription;
    }

    public void setEditTextDescription(EditText editTextDescription) {
        this.editTextDescription = editTextDescription;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
        setStringRound(String.valueOf(round.getRoundnumber()));
        setStringDescription(round.getDescription());
    }
}