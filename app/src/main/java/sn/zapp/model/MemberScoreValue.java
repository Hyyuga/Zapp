package sn.zapp.model;

import java.math.BigDecimal;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Steppo on 18.01.2016.
 */
public class MemberScoreValue extends RealmObject {

    private Score score;
    private String dbValue;
    @Ignore
    private BigDecimal value;


    public BigDecimal getValue() {
        return new BigDecimal(getDbValue());
    }

    public void setValue(BigDecimal value) {
        setDbValue(value.toString());
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public String getDbValue() {
        return dbValue;
    }

    public void setDbValue(String dbValue) {
        this.dbValue = dbValue;
    }
}
