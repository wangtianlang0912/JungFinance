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
package com.jung.finance.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.jung.finance.app.AppApplication;
import com.jung.finance.app.AppConstant;
import com.jung.finance.ui.user.bean.UserInfo;
import com.jung.finance.utils.encrypt.Base64;
import com.leon.common.commonutils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

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

}
