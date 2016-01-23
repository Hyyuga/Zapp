package sn.zapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Steppo on 13.01.2016.
 */
public class ZappApplication extends Application{
    private static ZappApplication sInstance;
    private static String viewStatePenalty;
    private static String viewStateScore;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        viewStatePenalty = "Show";
        viewStateScore = "Show";
    }
    public static void setViewStatePenalty(String arGviewState){
        viewStatePenalty = arGviewState;
    }

    public static String getViewStatePenalty(){
        return  sInstance.viewStatePenalty;
    }

    public static void setViewStateScore(String arGviewState){
        viewStateScore = arGviewState;
    }

    public static String getViewStateScore(){
        return  sInstance.viewStateScore;
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
