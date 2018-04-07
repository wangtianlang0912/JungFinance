package com.jung.finance.app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jung.finance.R;
import com.jung.finance.api.ApiConstants;
import com.jung.finance.ui.blogger.fragment.BloggerArticleDetailFragment;
import com.jung.finance.ui.blogger.fragment.BloggerFragment;
import com.jung.finance.ui.common.CommonActivity;
import com.jung.finance.ui.common.CommonWebFragment;
import com.jung.finance.ui.main.fragment.ActivityInfoFragment;
import com.jung.finance.ui.news.fragment.ArticleDetailFragment;
import com.jung.finance.ui.news.fragment.CommentListFragment;
import com.jung.finance.ui.setting.fragment.SettingFragment;
import com.jung.finance.ui.user.fragment.AccountSafeFragment;
import com.jung.finance.ui.user.fragment.BindMobileFragment;
import com.jung.finance.ui.user.fragment.ForgetPwdFragment;
import com.jung.finance.ui.user.fragment.LoginFragment;
import com.jung.finance.ui.user.fragment.RegisterFragment;
import com.jung.finance.ui.user.fragment.UpdatePwdFragment;
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

        intentToAct(context, R.string.update_pwd, UpdatePwdFragment.class);
    }

    public static void intentToBindMobile(Context context) {

        intentToAct(context, R.string.bind_phone, BindMobileFragment.class);
    }

    public static void intentToBloggerInfo(Context context, int uid) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.FLAG_DATA, uid);
        intentToAct(context, "", BloggerFragment.class, bundle);
    }

    /***
     * 博主文章详情
     * @param context
     * @param objectId
     */
    public static void intentToBloggerArticleDetail(Context context, int objectId, int bloggerUid) {

        Bundle bundle = new Bundle();
        String articleUrl = String.format(ApiConstants.URL + "media/i-%d.html", objectId);
        bundle.putString(AppConstant.FLAG_DATA, articleUrl);
        bundle.putInt(AppConstant.FLAG_DATA2, objectId);
        bundle.putInt(AppConstant.FLAG_DATA3, bloggerUid);
        intentToAct(context, context.getString(R.string.article_detail), BloggerArticleDetailFragment.class, bundle);
    }


    /***
     * 普通文章详情
     * @param context
     * @param objectId
     */
    public static void intentToArticleDetail(Context context, int objectId) {

        Bundle bundle = new Bundle();
        String articleUrl = String.format(ApiConstants.URL + "news/i-%d.html", objectId);
        bundle.putString(AppConstant.FLAG_DATA, articleUrl);
        bundle.putInt(AppConstant.FLAG_DATA2, objectId);
        intentToAct(context, context.getString(R.string.article_detail), ArticleDetailFragment.class, bundle);
    }

    /**
     * 活动详情
     *
     * @param context
     * @param objectId
     */
    public static void intentToActivityInfo(Context context, int objectId) {

        Bundle bundle = new Bundle();
        String activityUrl = String.format(ApiConstants.URL + "activity/i-%d.html", objectId);
        bundle.putString(AppConstant.FLAG_DATA, activityUrl);
        bundle.putInt(AppConstant.FLAG_DATA2, objectId);
        intentToAct(context, context.getString(R.string.activity), ActivityInfoFragment.class, bundle);

    }

    /***
     * 评论列表
     * @param context
     * @param articleId
     */
    public static void intentToCommentList(Context context,int articleId) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.FLAG_DATA, articleId);
        intentToAct(context, context.getString(R.string.comment_list), CommentListFragment.class, bundle);

    }

    public static void intentToCommonWeb(Context context, int resTitle, String url) {
        intentToCommonWeb(context, context.getString(resTitle), url);
    }

    public static void intentToCommonWeb(Context context, String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.FLAG_DATA, url);
        intentToAct(context, title, CommonWebFragment.class, bundle);
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
