package com.baidu.cn.vm.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by yujiangtao on 16/5/10.
 */
public class CommonUtil {

    /**
     * 获取apk文件根目录
     *
     * @param relativepath xx/xx
     * @return
     */
    public static String getDir(Context mcontext, String relativepath) {
        if (mcontext == null) return null;
        String dir = null;
        if (android.os.Environment.MEDIA_MOUNTED.equals
                (android.os.Environment.getExternalStorageState())) {
            dir = Environment.getExternalStorageDirectory().getPath();
        } else {
            dir = mcontext.getFilesDir().getPath();
        }
        if (dir.endsWith(File.separator)) return dir + relativepath;
        return dir + File.separator + relativepath;

    }

    public static boolean isWifi(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mWifi.isConnected();
    }

    /**
     * 获取本地apk的版本信息
     *
     * @param mcontext
     * @param apkpath
     * @return
     */
    public static int getApkVersion(Context mcontext, String apkpath) {
        PackageManager pm = mcontext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkpath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return info.versionCode;
        }
        return -1;
    }

    /**
     * 获取版本名称
     *
     * @param mcontext
     * @param apkpath
     * @return
     */
    public static String getApkVersionName(Context mcontext, String apkpath) {
        PackageManager pm = mcontext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkpath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return info.versionName;
        }
        return "";
    }

    /**
     * 判断通知栏是否启用
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return false;
        }
        try {
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

            ApplicationInfo appInfo = context.getApplicationInfo();

            String pkg = context.getApplicationContext().getPackageName();

            int uid = appInfo.uid;

            Class appOpsClass = Class.forName(AppOpsManager.class.getName());/* Context.APP_OPS_MANAGER */

            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            Integer value = (Integer) opPostNotificationValue.get(Integer.class);
            Integer res = (Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg);
            if ((res == null)) {
                res = 0;
            }
            return res == AppOpsManager.MODE_ALLOWED;

        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 请求数据
     *
     * @param url
     * @param callback
     */
    public static void request(String url, HttpUtil.RequestCallback callback) {
        RequestUtil.getInstance().reqeust(url, callback);
    }

    /**
     * 检测当前应用版本是否需要更新
     *
     * @param context
     * @param versionCode 最新版本号
     * @return 需要更新返回true否则是false
     */
    public static boolean checkVersion(Context context, int versionCode) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            int currVersionCode = info.versionCode;
            return currVersionCode < versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    public static boolean isForeground(Context context, String activityName) {
        if (context == null) {
            return false;
        }
        try {
            String packageName = context.getPackageName();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = mActivityManager.getRunningTasks(1);
            if (tasks != null && !tasks.isEmpty()) {
                ActivityManager.RunningTaskInfo taskInfo = tasks.get(0);
                if (taskInfo != null) {
                    ComponentName topActivity = taskInfo.topActivity;
                    if (topActivity.getPackageName().equals(packageName) &&
                            topActivity.getClassName().equals(activityName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }

        return false;
    }

}
