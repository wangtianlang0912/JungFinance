package com.jung.android.app;

import com.jung.finance.BuildConfig;
import com.jung.android.utils.PerfrenceHelper;
import com.leon.common.baseapp.BaseApplication;
import com.leon.common.commonutils.LogUtils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
        PerfrenceHelper.initialize(getPackageName());
    }
}
