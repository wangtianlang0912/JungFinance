package com.baidu.cn.vm.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yujiangtao on 16/5/13.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class RequestUtil {

    HttpUtil.RequestCallback callback=null;
    boolean cancelflag=false;
    private Map<Integer,Thread> threadMap=new HashMap<Integer, Thread>();

    private static RequestUtil instance=null;

    public static RequestUtil getInstance(){
        if(instance==null){
            synchronized (RequestUtil.class){
                if(instance == null){
                    instance = new RequestUtil();
                }
            }
        }
        return instance;
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(cancelflag)return true;
            switch (msg.what){
                case 0:
                    callback.onsuccess((String) msg.obj);
                    break;
                case 1:
                    callback.onfialed((Integer) msg.obj);
                    break;
                default:
                    break;
            }
            int id = msg.arg1;
            Thread thread = threadMap.get(id);
            if(thread!=null){
                thread = null;
            }
            threadMap.remove(id);
            return true;
        }
    });

    public void reqeust(final String url, HttpUtil.RequestCallback callback){
        this.callback = callback;
//        if(mthread!=null&&mthread.isAlive())return;
         Thread mthread = new Thread(new Runnable() {

            @Override
            public void run() {

                new HttpUtil().request_get(url, new HttpUtil.RequestCallback() {
                    @Override
                    public void onsuccess(String data) {
                        Message msg = new Message();
                        msg.obj = data;
                        msg.what = 0;
                        msg.arg1= (int) Thread.currentThread().getId();
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onfialed(int errorcode) {
                        Message msg = new Message();
                        msg.what=1;
                        msg.arg1= (int) Thread.currentThread().getId();
                        msg.obj= errorcode;
                        handler.sendMessage(msg);
                    }
                });
            }
        });
        mthread.start();
        threadMap.put((int) mthread.getId(),mthread);
    }

    public void cancelRequest(){
        for (Map.Entry entry:threadMap.entrySet()) {
            Thread thread = (Thread) entry.getValue();
            if(thread==null&&thread.isAlive()){
                thread.interrupt();
                thread=null;
            }
        }
        threadMap.clear();

    }
}
