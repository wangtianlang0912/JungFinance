package com.baidu.cn.vm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.baidu.cn.vm.util.FileUtil;

/**
 * Created by yujiangtao on 16/5/12.
 */
public class DownloadService extends Service {
    private DownloadUtil downloadUtil=null;
    public static final String PROGRESSACTION = "sina.com.cn.vm.service.progress";
    private boolean downloading = false;


    ProgressInterface pf = new ProgressInterface() {
        @Override
        public void end() {
            Intent intent = new Intent(PROGRESSACTION);
            intent.putExtra("STATE",UpDateConfig.DOWNLOADEND);
            intent.putExtra("PROGRESS", 100);
            sendBroadcast(intent);
            downloading= false;
//            Log.e("Progress_End","100");
            DownloadService.this.stopSelf();
        }

        @Override
        public void changgeProgress(int p) {
            Intent intent = new Intent(PROGRESSACTION);
            intent.putExtra("STATE",UpDateConfig.DOWNLOADING);
            intent.putExtra("PROGRESS",p);
            sendBroadcast(intent);
//            Log.e("Progress"," "+p);
        }

        @Override
        public void error() {
            Intent intent = new Intent(PROGRESSACTION);
            intent.putExtra("STATE",UpDateConfig.DOWNLOADERROR);
            sendBroadcast(intent);
            downloading= false;
//            Log.e("Progress_Error","error");
            DownloadService.this.stopSelf();
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!downloading&&intent!=null&&pf!=null) {
            downloading = true;
            //进程有可能被杀死，导致服务重启intent 值为null
            // http://blog.csdn.net/huutu/article/details/40357481
            String apkurl = intent.getStringExtra("URL");
            if(!TextUtils.isEmpty(apkurl)) {
                downloadUtil = new DownloadUtil(pf, apkurl);
                downloadUtil.to_download();
            }else{
                pf.error();
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileUtil.interceptFlag=true;
        downloading =false;
    }
}
