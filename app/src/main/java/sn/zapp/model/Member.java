package sn.zapp.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steppo on 10.01.2016.
 */
public class Member extends RealmObject{

    private String firstName;
    private String lastName;
    private String address;
    private String imageurl;
    @PrimaryKey
    private String email;
    private String birthday;

    public Member() {
    }

    public Member(String firstName, String lastName, String birthday, String email, String address) {
        this.firstName = firstName;
        this.setBirthday(birthday);
        this.lastName = lastName;
        this.address = address;
        this.setEmail(email);
    }
//    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
