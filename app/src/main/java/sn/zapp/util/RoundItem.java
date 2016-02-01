package sn.zapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import sn.zapp.R;
import sn.zapp.model.Round;

public class RoundItem extends LinearLayout {
    private int id;

    private Round round;

    private String stringRound = "";
    private String stringMultiplier = "";
    private String stringDescription = "";

    private TextView textViewRound;
    private EditText editTextMultiplier;
    private EditText editTextDescription;

    public RoundItem(Context context, int id) {
        super(context);
        initViews(context);
        if(id != -12){
            this.setId(id);
            this.setStringRound(String.valueOf(id));
        }

    }
    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.fragment_championship_round_item, this);

        setTextViewRound((TextView) this.findViewById(R.id.editText_number));
        getTextViewRound().setText(getStringRound());

        setEditTextMultiplier((EditText) this.findViewById(R.id.editText_multiplier));
        getEditTextMultiplier().setText(getStringMultiplier());

        setEditTextDescription((EditText) this.findViewById(R.id.editText_description));
        getEditTextDescription().setText(getStringDescription());

        initListener();

    }

    private void initListener() {
        getEditTextDescription().setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    EditText text = (EditText) v;
                    setStringDescription(text.getText().toString());
                }
            }
        });
        getEditTextMultiplier().setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    EditText text = (EditText) v;
                    setStringMultiplier(text.getText().toString());
                }
            }
        });
    }

    public String getStringRound() {
        return stringRound;
    }

    public void setStringRound(String stringRound) {
        this.stringRound = stringRound;
        if(this.textViewRound != null)
            textViewRound.setText(stringRound);
    }

    public String getStringMultiplier() {
        return stringMultiplier;
    }

    public void setStringMultiplier(String stringMultiplier) {
        this.stringMultiplier = stringMultiplier;
        if(this.editTextMultiplier != null)
            editTextMultiplier.setText(stringMultiplier);
    }

    public String getStringDescription() {
        return stringDescription;
    }

    public void setStringDescription(String stringDescription) {
        this.stringDescription = stringDescription;
        if(this.editTextDescription != null)
            editTextDescription.setText(stringDescription);
    }

    public TextView getTextViewRound() {
        return textViewRound;
    }

    public void setTextViewRound(TextView textViewRound) {
        this.textViewRound = textViewRound;
    }

    public EditText getEditTextMultiplier() {
        return editTextMultiplier;
    }

    public void setEditTextMultiplier(EditText editTextMultiplier) {
        this.editTextMultiplier = editTextMultiplier;
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
        setStringMultiplier(String.valueOf(round.getMultiplier()));
        setStringRound(String.valueOf(round.getRoundnumber()));
        setStringDescription(round.getDescription());

    }
}