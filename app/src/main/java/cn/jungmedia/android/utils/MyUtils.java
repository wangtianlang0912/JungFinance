/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cn.jungmedia.android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.X509TrustManager;

import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.utils.encrypt.Base64;
import cn.jungmedia.android.utils.encrypt.MD5;

/**
 * @author 咖枯
 * @version 1.0 2016/5/31
 */
public class MyUtils {

    private static String mToken;

    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public static int getScreenWith() {
        return AppApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /***
     * 传空 清空数据
     *
     * @param context
     * @param userInfo
     */
    public static void saveUserInfo(Context context, UserInfo userInfo) {
        try {
            if (userInfo == null) {
                PerfrenceHelper.putString(context, AppConstant.USERINFO_KEY, "");
            } else {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(userInfo);
                String encodeStr = Base64.encodeBytes(baos.toByteArray());

                PerfrenceHelper.putString(context, AppConstant.USERINFO_KEY, encodeStr);
                baos.close();
                oos.close();
            }
        } catch (Exception e) {
            LogUtils.loge(e, "saveUserInfo");
        }
    }

    /***
     * @param context
     * @return
     */
    public static UserInfo getUserInfoFromPreference(Context context) {
        try {
            String userStr = PerfrenceHelper.getString(context, AppConstant.USERINFO_KEY, "");
            if (!TextUtils.isEmpty(userStr)) {
                byte[] base64User = Base64.decode(userStr);
                ByteArrayInputStream bais = new ByteArrayInputStream(base64User);
                ObjectInputStream ois = new ObjectInputStream(bais);
                UserInfo userInfo = (UserInfo) ois.readObject();

                bais.close();
                ois.close();
                return userInfo;
            }
        } catch (Exception e) {
            LogUtils.loge(e, "getUserInfoFromPreference");
        }
        return null;
    }

    /**
     * @param context
     * @param token
     */
    public static void saveToken(Context context, String token) {
        mToken = token;
        if (TextUtils.isEmpty(token)) {
            PerfrenceHelper.putString(context, AppConstant.TOKEN_KEY, "");
        } else {
            PerfrenceHelper.putString(context, AppConstant.TOKEN_KEY, token);
        }
    }


    public static String getToken() {

        if (TextUtils.isEmpty(mToken)) {
            mToken = PerfrenceHelper.getString(AppApplication.getAppContext(), AppConstant.TOKEN_KEY);
        }
        return mToken;
    }

    public static boolean isLogin() {

        return getToken() != null;
    }

    public static void clearUser() {
        mToken = null;
        PerfrenceHelper.clearKey(AppApplication.getAppContext(), AppConstant.TOKEN_KEY);
        PerfrenceHelper.clearKey(AppApplication.getAppContext(), AppConstant.USERINFO_KEY);
    }


    private static class DefaultTrustManager implements X509TrustManager {

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] cert, String oauthType)
                throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] cert, String oauthType)
                throws java.security.cert.CertificateException {
        }
    }


    /**
     * 获取版本名称
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取版本号
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = -1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }


    /**
     * 显示软键盘
     */
    public static void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
    }

    /**
     * 获取SD卡路径
     *
     * @return 如果sd卡不存在则返回null
     */
    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment
                .MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    /**
     * 安装文件
     *
     * @param data
     */
    public static void promptInstall(Context context, Uri data) {
        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "application/vnd.android.package-archive");
        // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
        promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(promptInstall);
    }

    public static void copy2clipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("clip", text);
        cm.setPrimaryClip(clip);
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 取得用户及设备标识
    // 标识设备 android=md5(imei+mac+manufacturer+model)；ios=openUDID（Unique Device
    // IDentifier）】

    /**
     * 由于在2013.12.19日新闻将行为日志的格式升级为3.0.1在2013.12.25日对行为日志设备id进行了调整，
     * 如果取md5之后是16位的不变，不低于32位的，则取中间的16位，对于目前的用户量完全满足而且可以节约存储
     */
    public static String getDeviceId(Context context) {
        String str = String.format("%s_%s_%s_%s", getIMEI(context),
                getMacAddress(context), android.os.Build.MANUFACTURER,
                android.os.Build.MODEL);

        /**
         * MD5的全称是 Message-Digest Algorithm 5 (信息摘要算法)
         * 通过手工就可以实现32位到16位之间的转换，只需要去掉32位密码格式的前八位以及最后八位，经过这样删减位数即可得到16位的加密格式。
         * 即：21232f297a57a5a743894a0e4a801fc3 -》7a57a5a743894a0e
         * 同样也会遇到40位的MD5，40位的计算公式 40位MD5 = 16 位MD5 + 《32位MD5后8位》 + 《32位MD5后16位》
         * 7a57a5a743894a0e4a801fc343894a0e4a801fc3
         */
        str = MD5.EncoderByMD5(str); // 取中间16位比较节省空间
        if (str.length() >= 32) {
            str = str.substring(8, 24);
        }
        return str;
    }

    /**
     * 获取手机串号
     *
     * @return
     */
    public static String getIMEI(Context context) {
        String deviceId = null;
        try {
            // 获取手机号、手机串号信息 当获取不到设备号时，系统会提供一个自动的deviceId
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
            e.getStackTrace();
            deviceId = "999999999999999";
        }
        return deviceId;
    }

    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
            return info.getMacAddress();
        } else {
            //from API level 9, 2.3
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
                return getMacAddressL9();
            }
        }
        return null;
    }

    //根据IP获取本地Mac
    @SuppressLint("NewApi")
    public static String getMacAddressL9() {
        String mac_s = "";
        try {
            byte[] mac;
            String ip_s = getLocalIpAddress();
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(ip_s));
            mac = ne.getHardwareAddress();
            mac_s = byte2hex(mac);
        } catch (Exception e) {
        }

        return mac_s;
    }

    //获取本地IP
    public static String getLocalIpAddress() {
        try {
            NetworkInterface intf = null;
            InetAddress inetAddress = null;
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
        }

        return null;
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs.append("0").append(stmp);
            } else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }


    public static boolean verifyToken(BaseRespose respose) {

        if (!respose.success()) {
            if ("11".equals(respose.code) || "未找到授权凭证".equals(respose.msg)) {
                // error: "未找到授权凭证",
                AppApplication.getInvalidCallback().onTokenInvalid();
                return false;
            }
        }

        return true;
    }
}
