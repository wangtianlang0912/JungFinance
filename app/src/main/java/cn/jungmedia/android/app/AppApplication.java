package cn.jungmedia.android.app;

import com.leon.common.baseapp.BaseApplication;
import com.leon.common.commonutils.LogUtils;

import cn.jungmedia.android.BuildConfig;
import cn.jungmedia.android.utils.PerfrenceHelper;

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
