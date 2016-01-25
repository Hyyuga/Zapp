package sn.zapp.model;

import java.math.BigDecimal;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Steppo on 24.01.2016.
 */
public class ChampionshipRoundValue extends RealmObject {

    private Round round;
    private String dbValue;
    @Ignore
    private BigDecimal value;


    public BigDecimal getValue() {
        return new BigDecimal(getDbValue());
    }

    public void setValue(BigDecimal value) {
        setDbValue(value.toString());
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public String getDbValue() {
        return dbValue;
    }

    public void setDbValue(String dbValue) {
        this.dbValue = dbValue;
    }
    
}
