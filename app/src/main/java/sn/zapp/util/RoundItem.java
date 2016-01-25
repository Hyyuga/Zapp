package sn.zapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import sn.zapp.R;
import sn.zapp.model.Round;

public class RoundItem extends LinearLayout {
    private int id;

    private Round round;

    private String stringRound = "";
    private String stringMultiplier = "";
    private String stringDescription = "";

    private EditText editTextRound;
    private EditText editTextMultiplier;
    private EditText editTextDescription;

    public RoundItem(Context context, int id) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.fragment_round_item, this);
        initViews(context);
        if(id != -12){
            this.setId(id);
            this.setStringRound(String.valueOf(id));
        }

    }
//    public RoundItem(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initViews(context, attrs);
//    }
//
//    public RoundItem(Context context, AttributeSet attrs, int defStyle) {
//        this(context, attrs);
//        initViews(context, attrs);
//    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.fragment_round_item, this);

        setEditTextRound((EditText) this.findViewById(R.id.editText_number));
        getEditTextRound().setText(getStringRound());

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
        if(this.editTextRound != null)
            editTextRound.setText(stringRound);
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

    public EditText getEditTextRound() {
        return editTextRound;
    }

    public void setEditTextRound(EditText editTextRound) {
        this.editTextRound = editTextRound;
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
        setStringRound(String.valueOf(round.getRound()));
        setStringDescription(round.getDescription());

    }

//    public String getLeftLabel() {
//        return leftLabel;
//    }
//
//    public void setLeftLabel(String leftLabel) {
//        this.leftLabel = leftLabel;
//        if(editTextRound !=null){
//            editTextRound.setText(leftLabel);
//        }
//    }
//
//    public String getRightLabel() {
//        return rightLabel;
//    }
//
//    public void setRightLabel(String rightLabel) {
//        this.rightLabel = rightLabel;
//        if(editTextMultiplier !=null){
//            editTextMultiplier.setText(rightLabel);
//        }
//    }
}