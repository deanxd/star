package com.deanx.brouter.api;

import android.util.Log;
import android.util.LruCache;

import com.deanx.brouter.api.core.IParameterLoad;

public class ParameterManager {

    private static final String PARAMETER_FILE_NAME = "$$Parameter";
    private static final String TAG = "parameter >>>";

    private LruCache<String, IParameterLoad> mLoadCache;
    private static ParameterManager mInstance;

    private ParameterManager() {
        mLoadCache = new LruCache<>(100);
    }

    public static ParameterManager getInstance() {
        if (mInstance == null) {
            synchronized (ParameterManager.class) {
                if (mInstance == null) {
                    mInstance = new ParameterManager();
                }
            }
        }
        return mInstance;
    }

    public void loadParameter(Object obj) {
        String loadClassName = obj.getClass().getName() + PARAMETER_FILE_NAME;
        try {
            IParameterLoad parameterLoad = mLoadCache.get(loadClassName);
            if (parameterLoad == null) {
                Class<?> clazz = Class.forName(loadClassName);
                parameterLoad = (IParameterLoad) clazz.newInstance();
                mLoadCache.put(loadClassName, parameterLoad);
            }
            parameterLoad.loadParameter(obj);

        } catch (Exception e) {
            Log.e(TAG, loadClassName + "load  error:" + e.getMessage());
        }
    }
}
