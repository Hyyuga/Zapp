package sn.zapp.model;

import java.math.BigDecimal;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Steppo on 17.01.2016.
 */
public class MemberPenalyValue extends RealmObject {

    private Penalty penalty;
    private String dbValue;
    @Ignore
    private BigDecimal value;

    public MemberPenalyValue(){}

    public String getDbValue() {
        return dbValue;
    }

    public void setDbValue(String dbValue) {
        this.dbValue = dbValue;
    }

    public BigDecimal getValue() {
        return new BigDecimal(getDbValue());
    }

    public void setValue(BigDecimal value) {
        setDbValue(value.toString());
    }
    public Penalty getPenalty() {
        return penalty;
    }

    public void setPenalty(Penalty penalty) {
        this.penalty = penalty;
    }
}
