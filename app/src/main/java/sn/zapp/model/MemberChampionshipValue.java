package sn.zapp.model;

import java.math.BigDecimal;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by Steppo on 24.01.2016.
 */
public class MemberChampionshipValue extends RealmObject{

    private Championship championship;
    private String dbValue;
    @Ignore
    private BigDecimal value;
    private RealmList<ChampionshipRoundValue> roundResult;

    public BigDecimal getValue() {
        return new BigDecimal(getDbValue());
    }

    public void setValue(BigDecimal value) {
        setDbValue(value.toString());
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship score) {
        this.championship = score;
    }

    public String getDbValue() {
        return dbValue;
    }

    public void setDbValue(String dbValue) {
        this.dbValue = dbValue;
    }

    public RealmList<ChampionshipRoundValue> getRoundResult() {
        return roundResult;
    }

    public void setRoundResult(RealmList<ChampionshipRoundValue> roundResult) {
        this.roundResult = roundResult;
    }
}
