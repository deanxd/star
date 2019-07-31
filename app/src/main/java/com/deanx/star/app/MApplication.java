package com.deanx.star.app;

import android.app.Application;
import android.content.Context;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Context baseContext = getBaseContext();
        Context applicationContext = getApplicationContext();

    }
}
