package com.deanx.app.presonal.debug;

import android.util.Log;

import com.deanx.lib.commom.base.BaseApplication;

import static com.deanx.lib.commom.constant.Constant.STAR_TAG;

public class Debug_PersonalApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(STAR_TAG, "Debug_PersonalApplication onCreate");
    }
}
