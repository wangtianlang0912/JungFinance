package com.baidu.cn.vm.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.cn.vm.service.UpDateConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yujiangtao on 16/5/10.
 */
public class FileUtil {

    /**
     * 检查本地apk是否存在及其是否比当前版本新
     * @param context
     * @param currentversion
     * @return true or false
     */
    public boolean checkLocalApk(Context context, int currentversion) {
        File apkfile = new File(UpDateConfig.APK_DIR,UpDateConfig.APK_NAME);
        if(!apkfile.exists())return false;
        int localapkversion = CommonUtil.getApkVersion(context,apkfile.getAbsolutePath());
        if(currentversion>localapkversion){
            deleteOldApk();
            return false;
        }
        return true;
    }
    public boolean checkLocalApk(Context context, String versionName) {
        File apkfile = new File(UpDateConfig.APK_DIR,UpDateConfig.APK_NAME);
        if(!apkfile.exists())return false;
        String localVersionName = CommonUtil.getApkVersionName(context,apkfile.getAbsolutePath());
        int compare = localVersionName.compareTo(versionName);
        if(compare<0){
            deleteOldApk();
            return false;
        }
        return true;
    }
    //中断标志
    public static boolean interceptFlag = false;

    /***
     * 写APK到文件
     *
     * @param inputStream
     * @param handler
     * @return
     */
    public static boolean writeApkToFile(InputStream inputStream,
                                         Handler handler) throws IOException {
        File file = new File(UpDateConfig.APK_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        String tempFileName = UpDateConfig.APK_NAME.replace(".apk",".temp");
        File tempfile = new File(file, tempFileName);
        FileOutputStream outStream = new FileOutputStream(tempfile);
        int count = 0;
        byte buf[] = new byte[1024];
        do {
            int numread = inputStream.read(buf);
            count += numread;
            Message msg = handler.obtainMessage(UpDateConfig.DOWNLOADING);
            msg.obj = count;
            handler.sendMessage(msg);
            if (numread <= 0) {
                // 下载完成通知安装
                tempfile.renameTo(new File(file,tempFileName.replace(".temp",".apk")));
                handler.sendEmptyMessage(UpDateConfig.DOWNLOADEND);
                break;
            }else
            outStream.write(buf, 0, numread);

        } while (!interceptFlag);// 点击取消停止下载
        outStream.close();
        inputStream.close();
        return !interceptFlag;

    }

    /**
     * 删除旧的apk文件
     */
    public void deleteOldApk() {
        try {
            File apkfile = new File(UpDateConfig.APK_DIR, UpDateConfig.APK_NAME);
            if (apkfile.exists()) apkfile.delete();
        }catch (Exception e){
        }
    }
}
