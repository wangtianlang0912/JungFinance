package cn.jungmedia.android.update;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Environment;

import com.leon.common.commonutils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lihui38 on 2017/6/24.
 */

public class UpdateUtils {

    /**
     * 当前消息通知是否开启，
     * Android 4.1 offers the user a check box to disable notifications for a specific application.
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = context.getApplicationInfo();

        String pkg = context.getApplicationContext().getPackageName();

        int uid = appInfo.uid;

        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

        try {

            appOpsClass = Class.forName(AppOpsManager.class.getName());

            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            Integer value = (Integer) opPostNotificationValue.get(Integer.class);
            Integer res = (Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg);
            if ((res == null)) {
                res = 0;
            }
            return res == AppOpsManager.MODE_ALLOWED;

        } catch (Exception e) {
            LogUtils.loge("isNotificationEnabled", e);
        }
        return false;
    }


    /**
     * Check if SDCard can be used.
     *
     * @return boolean - true 	SDCard can be used.
     * - false	SDCard can not be used.
     */
    public static boolean SDCardCanUse() {

        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

                return true;
            }

            LogUtils.logd( "No SDCard!");
        } catch (Exception e) {
            LogUtils.loge("SDCardCanUse :: npe-on-environment-getexternalstoragestate", e);
        }
        return false;
    }
}
