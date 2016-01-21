package sn.zapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Steppo on 13.01.2016.
 */
public class ZappApplication extends Application{
    private static ZappApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static ZappApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return  sInstance.getApplicationContext();
    }

    public static Class isNumeric(String str) {

        try {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return Integer.class;
        }
        return Double.class;
    }

}
