package com.leon.common.browser;

/**
 * Summary: js脚本所能执行的函数空间
 * Version 1.0
 * Date: 13-11-20
 * Time: 下午4:40
 * Copyright: Copyright (c) 2013
 */


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.leon.common.commonutils.LoadSysSoft;
import com.leon.common.ui.DuAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


//HostJsScope中需要被JS调用的函数，必须定义成public ，且必须包含WebView这个参数
public class HostJsScope {

    public static void getUrl(WebView webView, String param, String url) {
        if (url != null) {
          //  new LoadSysSoft().OpenBrowser(webView.getContext(), url);
        }
    }

    public static void getVideoUrl(WebView webView, String videoUrl) {
        if (videoUrl != null) {
            new LoadSysSoft().OpenVideo(webView.getContext(), videoUrl);
        }
    }

    public static void getDownLoadUrl(WebView webView, String url) {
        if (url != null) {
            new LoadSysSoft().OpenBrowser(webView.getContext(), url);
        }
    }

    public static void onFinish(WebView webView) {
        if (webView.getContext() instanceof Activity) {

            Activity activity = (Activity) webView.getContext();
            activity.finish();
        }
    }

    public static void OpenMarketApp(WebView webView, String appPackageName) {
        if (appPackageName != null) {
            new LoadSysSoft().OpenMarketApp(webView.getContext(), appPackageName);
        }
    }

    /**
     * 短暂气泡提醒
     *
     * @param webView 浏览器
     * @param message 提示信息
     */
    public static void toast(WebView webView, String message) {
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 可选择时间长短的气泡提醒
     *
     * @param webView    浏览器
     * @param message    提示信息
     * @param isShowLong 提醒时间方式
     */
    public static void toast(WebView webView, String message, int isShowLong) {
        Toast.makeText(webView.getContext(), message, isShowLong).show();
    }

    /**
     * 弹出记录的测试JS层到Java层代码执行损耗时间差
     *
     * @param webView   浏览器
     * @param timeStamp js层执行时的时间戳
     */
    public static void testLossTime(WebView webView, long timeStamp) {
        timeStamp = System.currentTimeMillis() - timeStamp;
        alert(webView, String.valueOf(timeStamp));
    }

    /**
     * 系统弹出提示框
     *
     * @param webView 浏览器
     * @param message 提示信息
     */
    public static void alert(WebView webView, String message) {

        final DuAlertDialog.Builder builder = new DuAlertDialog().createBuilder(webView.getContext());

        builder.setTitle("提示");
        builder.setMessage(message);


        builder.setCancelable(false);
        // 不需要绑定按键事件 屏蔽keycode等于84之类的按键
        builder.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
                return true;
            }
        });
        builder.show();
    }

    public static void alert(WebView webView, int msg) {
        alert(webView, String.valueOf(msg));
    }

    public static void alert(WebView webView, boolean msg) {
        alert(webView, String.valueOf(msg));
    }

    /**
     * 获取设备IMSI
     *
     * @param webView 浏览器
     * @return 设备IMSI
     */
    public static String getIMSI(WebView webView) {
        return ((TelephonyManager) webView.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 获取用户系统版本大小
     *
     * @param webView 浏览器
     * @return 安卓SDK版本
     */
    public static int getOsSdk(WebView webView) {
        return Build.VERSION.SDK_INT;
    }

    //---------------- 界面切换类 ------------------

    /**
     * 结束当前窗口
     *
     * @param view 浏览器
     */
    public static void goBack(WebView view) {
        if (view.getContext() instanceof Activity) {
            ((Activity) view.getContext()).finish();
        }
    }

    /**
     * 传入Json对象
     *
     * @param view 浏览器
     * @param jo   传入的JSON对象
     * @return 返回对象的第一个键值对
     */
    public static String passJson2Java(WebView view, JSONObject jo) {
        Iterator iterator = jo.keys();
        String res = null;
        if (iterator.hasNext()) {
            try {
                String keyW = (String) iterator.next();
                res = keyW + ": " + jo.getString(keyW);
            } catch (JSONException je) {

            }
        }
        return res;
    }

    /**
     * 将传入Json对象直接返回
     *
     * @param view 浏览器
     * @param jo   传入的JSON对象
     * @return 返回对象的第一个键值对
     */
    public static JSONObject retBackPassJson(WebView view, JSONObject jo) {
        return jo;
    }

    public static int overloadMethod(WebView view, int val) {
        return val;
    }

    public static String overloadMethod(WebView view, String val) {
        return val;
    }

    public  class RetJavaObj {
        public int intField;
        public String strField;
        public boolean boolField;
    }

    public  List<RetJavaObj> retJavaObject(WebView view) {
        RetJavaObj obj = new RetJavaObj();
        obj.intField = 1;
        obj.strField = "mine str";
        obj.boolField = true;
        List<RetJavaObj> rets = new ArrayList<RetJavaObj>();
        rets.add(obj);
        return rets;
    }

    public  void delayJsCallBack(WebView view, int ms, final String backMsg, final JsCallback jsCallback) {
        TaskExecutor.scheduleTaskOnUiThread(ms * 1000, new Runnable() {
            @Override
            public void run() {
                try {
                    jsCallback.apply(backMsg);
                } catch (JsCallback.JsCallbackException je) {
                    je.printStackTrace();
                }
            }
        });
    }

    public  long passLongType(WebView view, long i) {
        return i;
    }
}