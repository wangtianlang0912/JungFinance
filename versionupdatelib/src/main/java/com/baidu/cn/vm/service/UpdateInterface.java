package com.baidu.cn.vm.service;


import android.app.Activity;
import android.content.Context;

/**
 * Created by yujiangtao on 16/5/10.
 */
public interface UpdateInterface {
    /**
     * 启动线程下载apk到本地
     * @param url
     * @param pf
     */
    void download(String url, ProgressInterface pf);

    /**
     * 启动Service下载apk
     * @param context
     * @param url
     * @param pf
     */
    void download(Context context, String url, ProgressInterface pf);
    /**
     * 启动安装程序
     * @param act
     */
    void install(Activity act);

    /**
     * 取消下载
     */
    void cancelDownload();
}
