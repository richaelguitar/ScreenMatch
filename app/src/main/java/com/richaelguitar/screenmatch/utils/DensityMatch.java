package com.richaelguitar.screenmatch.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class DensityMatch {
    private static DensityMatch densityMatch;

    private float targetDensity;

    private float targetScaleDensity;

    private int targetDensityDpi;

    private float appDensity;

    private float appScaleDensity;

    private float standWidth=360.0f;//以360dp为标准

    private    DisplayMetrics displayMetrics = new DisplayMetrics();

    private DensityMatch(final Application application){
         WindowManager windowManager = (WindowManager) application.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
         windowManager.getDefaultDisplay().getMetrics(displayMetrics);
         appDensity = displayMetrics.density;
         appScaleDensity = displayMetrics.scaledDensity;
         application.registerComponentCallbacks(new ComponentCallbacks() {
             @Override
             public void onConfigurationChanged(Configuration configuration) {
                 //监听字体大小改变
                 if(configuration!=null && configuration.fontScale>0){
                     appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                 }
             }

             @Override
             public void onLowMemory() {

             }
         });

    }

    public static DensityMatch getInstance(Application application){
        if(densityMatch == null){
            synchronized (DensityMatch.class){
                if(densityMatch == null){
                    densityMatch = new DensityMatch(application);
                }
            }
        }

        return densityMatch;
    }

    public void applyDensityMatch(Activity activity,String orientation){

        //获取状态栏高度
        int barHeight = AppUtils.getStatusBarHeight(activity);
        if (orientation.equals("height")) {
            targetDensity = (displayMetrics.heightPixels - barHeight) / 667f;
        } else {
            targetDensity = displayMetrics.widthPixels / 360f;
        }
        targetScaleDensity = targetDensity*(appScaleDensity/appDensity);
        targetDensityDpi = (int)targetDensity*160;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
