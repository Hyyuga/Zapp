package sn.zapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Steppo on 13.01.2016.
 */
public class ZappApplication extends Application{
    private static ZappApplication sInstance;
    private static String viewState;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        viewState = "Show";
    }
    public static void setViewState(String arGviewState){
        viewState = arGviewState;
    }

    public static ZappApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return  sInstance.getApplicationContext();
    }
    public static String getViewState(){
        return  sInstance.viewState;
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
