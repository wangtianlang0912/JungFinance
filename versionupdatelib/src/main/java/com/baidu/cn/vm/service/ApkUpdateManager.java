package com.baidu.cn.vm.service;

import android.app.Activity;
import android.content.Context;

import com.baidu.cn.vm.util.FileUtil;

/**
 * Created by yujiangtao on 16/5/11.
 */
public class ApkUpdateManager {

    private static ApkUpdateManager manager = null;
    private UpdateIml updateUtil = null;
    private FileUtil fileUtil = null;
    private static Object obj = new Object();

    public static ApkUpdateManager getInstance() {
        if (manager == null) {
            synchronized (obj) {
                if (manager == null) {
                    manager = new ApkUpdateManager();
                }
            }
        }
        return manager;
    }


    /**
     * 设置apk默认的存储路径和名称
     * @param apkfiledir
     */
    public void setApkFilePath(Context context,String apkfiledir, String apkname) {
        UpDateConfig.setAPKPath(context,apkfiledir, apkname);
    }
    public void setApkFileDefault(Context context){
        UpDateConfig.setAPKDirDefault(context);
    }


    public ApkUpdateManager() {
        updateUtil = new UpdateIml();
        fileUtil = new FileUtil();
    }

    /**
     * 下载apk
     *
     * @param url
     * @param pf
     */
    public void download(String url, final ProgressInterface pf) {
        updateUtil.download(url, pf);
    }

    /**
     * 安装apk
     *
     * @param act
     */
    public void install(Activity act) {
        updateUtil.install(act);
    }

    /**
     * 删除旧的APK文件
     */
    public void deleteOldApkFile() {
        fileUtil.deleteOldApk();
    }

    /**
     * 检查本地apk是否比版本号为version的apk要新
     *
     * @param context
     * @param version
     * @return
     */
    public boolean checkLocalApkIsNew(Context context, int version) {
        return fileUtil.checkLocalApk(context, version);
    }

    public boolean checkLocalApkIsNew(Context context, String versionName) {
        return fileUtil.checkLocalApk(context, versionName);
    }

    public UpdateIml getUpdateUtil() {
        return updateUtil;
    }
}
