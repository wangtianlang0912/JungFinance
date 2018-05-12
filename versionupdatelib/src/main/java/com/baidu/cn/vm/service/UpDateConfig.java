package com.baidu.cn.vm.service;

import android.content.Context;

import com.baidu.cn.vm.util.CommonUtil;

/**
 * Created by yujiangtao on 16/5/11.
 */
public class UpDateConfig {

    public static final int DOWNLOADING=0;
    public static final int DOWNLOADEND=1;
    public static final int DOWNLOADERROR=2;

    public static String APK_DIR=null;
    public static String APK_NAME="versionupdate.apk";

    /**
     * 设置默认的apk存储目录和apk名称
     * @param mcontext
     */
    public static void setAPKDirDefault(Context mcontext){
        //sd卡根目录
        APK_DIR = CommonUtil.getDir(mcontext,"");
        //应用名称
        APK_NAME = mcontext.getPackageManager()
                .getApplicationLabel(mcontext.getApplicationInfo()).toString();
    }

    /**
     * 设置apk存储目录和apk名称
     * @param relativedir  xx/xx
     * @param name  xx.apk
     */
    public static void setAPKPath(Context context,String relativedir,String name){
        APK_NAME = name;
        APK_DIR = CommonUtil.getDir(context,relativedir);
    }

}
