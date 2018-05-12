package com.baidu.cn.vm.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import java.io.File;

import com.baidu.cn.vm.util.FileUtil;

/**
 * 自动更新管理器
 * Created by yujiangtao on 16/5/10.
 */
public class UpdateIml implements UpdateInterface {
    private DownloadUtil downloadUtil=null;
    private ProgressInterface pf = null;
    private Context mcontext=null;
    private static boolean isDownLoading=false;

    BroadcastReceiver progressreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int  state = intent.getIntExtra("STATE",0);
            if(state == UpDateConfig.DOWNLOADERROR){
                pf.end();
                pf=null;
                isDownLoading=false;
                try {
                    context.unregisterReceiver(this);
                }catch (Exception e){
                }
                return;
            }

            isDownLoading = true;

            int p = intent.getIntExtra("PROGRESS",0);
            pf.changgeProgress(p);
            if(p==100){
                pf.end();
                pf=null;
                isDownLoading = false;
                try {
                    context.unregisterReceiver(this);
                }catch (Exception e){
                }
            }
        }
    };

    public void download(Context mcontext, String url, final ProgressInterface pf){
//        Log.e("Download","startDownload");
        this.pf = pf;
        this.mcontext = mcontext;
        if(isDownLoading)return;
        try {
            try {this.mcontext.unregisterReceiver(progressreceiver);
            }catch (Exception e){
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadService.PROGRESSACTION);
            mcontext.getApplicationContext().registerReceiver(progressreceiver, filter);
        }catch (Exception e){
        }
        Intent intent = new Intent(mcontext,DownloadService.class);
        intent.putExtra("URL",url);
        mcontext.startService(intent);
    }

    @Override
    public void download(String url, final ProgressInterface pf) {
        downloadUtil=new DownloadUtil(pf,url);
        downloadUtil.to_download();
    }

    @Override
    public void install(Activity act) {
        File apkfile = new File(UpDateConfig.APK_DIR,UpDateConfig.APK_NAME);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");// File.toString()会返回路径信息
        act.startActivity(i);
    }

    @Override
    public void cancelDownload() {
        FileUtil.interceptFlag=true;
        if(mcontext!=null) {
            Intent intent = new Intent(mcontext, DownloadService.class);
            mcontext.stopService(intent);
        }
    }

}
