package com.deanx.app.order.debug;

import android.util.Log;

import com.deanx.lib.commom.base.BaseApplication;

import static com.deanx.lib.commom.constant.Constant.STAR_TAG;

public class Debug_OrderApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(STAR_TAG, "Debug_OrderApplication onCreate");
    }
}
