package com.baidu.cn.vm.service;

import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.baidu.cn.vm.util.FileUtil;

/**
 * Created by yujiangtao on 16/5/12.
 */
public class DownloadUtil {
    private Thread downloadThread=null;
    private Handler handler=null;
    private int length=0;//apk总长度
    private int downloadCount=1;
    private String apkUrl=null;
    private ProgressInterface pf=null;

    public DownloadUtil(ProgressInterface pf,String apkUrl){
        this.pf=pf;
        this.apkUrl = apkUrl;
    }

    public void to_download(){
        downloadThread = new Thread(downApkRunnable);
        downloadThread.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case UpDateConfig.DOWNLOADING:
                        int progress= (Integer)msg.obj;
                        if(downloadCount==1){
                            pf.changgeProgress(1);
                        }
                        if(progress>=(length/99)*downloadCount){
                            if(pf!=null)
                            pf.changgeProgress(downloadCount);
                            downloadCount+=1;
                        }
                        break;
                    case UpDateConfig.DOWNLOADEND:
                        downloadCount=1;
                        if(pf!=null) {
                            pf.changgeProgress(100);
                            pf.end();
                        }
                        break;
                    case UpDateConfig.DOWNLOADERROR:
                        if(pf!=null) {
                            downloadCount=1;
                            pf.end();
                        }
                        break;
                }
            }
        };
    }
    /**
     * 下载的线程
     */
    private Runnable downApkRunnable = new Runnable() {
        @Override
        public void run() {
            URL url;
            try {
                url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                length = conn.getContentLength();
                InputStream ins = conn.getInputStream();
                FileUtil.interceptFlag=false;
                FileUtil.writeApkToFile(ins,handler);
            } catch (Exception e) {
                handler.sendEmptyMessage(UpDateConfig.DOWNLOADERROR);
            }
        }
    };
}
