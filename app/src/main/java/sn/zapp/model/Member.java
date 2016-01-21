package sn.zapp.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steppo on 10.01.2016.
 */
public class Member extends RealmObject{

    private String vorname;
    private String nachname;
    private String adresse;
    private String imageurl;
    @PrimaryKey
    private String email;
    private String geburtstag;

    public Member() {
    }

    public Member(String vorname, String nachname, String geburtstag, String email, String adresse) {
        this.vorname = vorname;
        this.setGeburtstag(geburtstag);
        this.nachname = nachname;
        this.adresse = adresse;
        this.setEmail(email);
    }
//    }

    /**
     * @return the vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * @param vorname the vorname to set
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * @return the nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the imageurl
     */
    public String getImageurl() {
        return imageurl;
    }

    /**
     * @param imageurl the imageurl to set
     */
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(String geburtstag) {
        this.geburtstag = geburtstag;
    }
}
