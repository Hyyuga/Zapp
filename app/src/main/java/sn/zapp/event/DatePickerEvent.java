package sn.zapp.event;


/**
 * Created by Steppo on 21.01.2016.
 */
public class DatePickerEvent {

    private String date;

    public DatePickerEvent(String date){
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
