package sn.zapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steppo on 13.01.2016.
 */
public class Penalty extends RealmObject{

    @PrimaryKey
    private String name;
    private String amount;

    public Penalty(){}

    public Penalty(String name, String amount){
        this.setName(name);
        this.setAmount(amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
