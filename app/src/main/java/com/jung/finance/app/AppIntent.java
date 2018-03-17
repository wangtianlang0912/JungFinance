package com.jung.finance.app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jung.finance.R;
import com.jung.finance.ui.common.CommonActivity;
import com.jung.finance.ui.setting.fragment.SettingFragment;
import com.jung.finance.ui.user.fragment.AccountSafeFragment;
import com.jung.finance.ui.user.fragment.BindMobileFragment;
import com.jung.finance.ui.user.fragment.ForgetPwdFragment;
import com.jung.finance.ui.user.fragment.LoginFragment;
import com.jung.finance.ui.user.fragment.RegisterFragment;
import com.jung.finance.ui.user.fragment.UpdateMobileFragment;
import com.jung.finance.ui.user.fragment.UserInfoFragment;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/15. 下午11:33
 *
 *
 */
public class AppIntent {

    public static void intentToAccountSafe(Context context) {

        intentToAct(context, R.string.account_safe, AccountSafeFragment.class);
    }

    public static void intentToSetting(Context context) {

        intentToAct(context, R.string.setting, SettingFragment.class);
    }

    public static void intentToUserInfo(Context context) {

        intentToAct(context, R.string.update_info, UserInfoFragment.class);
    }

    public static void intentToLogin(Context context) {

        intentToAct(context, R.string.login, LoginFragment.class);
    }

    public static void intentToRegister(Context context) {

        intentToAct(context, R.string.register, RegisterFragment.class);
    }

    public static void intentToForgetPwd(Context context) {

        intentToAct(context, R.string.forget_pwd, ForgetPwdFragment.class);
    }

    public static void intentToUpdatePwd(Context context) {

        intentToAct(context, R.string.update_pwd, UpdateMobileFragment.class);
    }

    public static void intentToBindMobile(Context context) {

        intentToAct(context, R.string.bind_phone, BindMobileFragment.class);
    }


    public static void intentToAct(Context context, int titleId, Class fragmentClass) {
        intentToAct(context, context.getString(titleId), fragmentClass, null);
    }

    public static void intentToAct(Context context, String title, Class fragmentClass, Bundle bundle) {

        Intent intent = new Intent();
        intent.setClass(context, CommonActivity.class);
        intent.putExtra(AppConstant.FLAG_FRAGMENT, fragmentClass.getName());
        intent.putExtra(AppConstant.FLAG_NAME, title);
        if (bundle != null) {
            intent.putExtra(AppConstant.FLAG_BUNDLE, bundle);
        }
        context.startActivity(intent);
    }
}
