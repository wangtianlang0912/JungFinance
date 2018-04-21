package cn.jungmedia.android.app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.jungmedia.android.R;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.bean.ActivityModel;
import cn.jungmedia.android.ui.blogger.fragment.BloggerFavFragment;
import cn.jungmedia.android.ui.blogger.fragment.BloggerFragment;
import cn.jungmedia.android.ui.blogger.fragment.FansListFragment;
import cn.jungmedia.android.ui.blogger.fragment.MediaDiffShowFragment;
import cn.jungmedia.android.ui.common.CommonActivity;
import cn.jungmedia.android.ui.common.CommonWebFragment;
import cn.jungmedia.android.ui.fav.ui.ActivityEditFragment;
import cn.jungmedia.android.ui.fav.ui.FastEditFragment;
import cn.jungmedia.android.ui.fav.ui.HqEditFragment;
import cn.jungmedia.android.ui.fav.ui.NewsEditFragment;
import cn.jungmedia.android.ui.main.fragment.ActivityInfoFragment;
import cn.jungmedia.android.ui.main.fragment.ActivitySignupFragment;
import cn.jungmedia.android.ui.news.fragment.ArticleDetailFragment2;
import cn.jungmedia.android.ui.news.fragment.CommentListFragment;
import cn.jungmedia.android.ui.score.ui.ScoreListFragment;
import cn.jungmedia.android.ui.setting.fragment.SettingFragment;
import cn.jungmedia.android.ui.user.fragment.AccountSafeFragment;
import cn.jungmedia.android.ui.user.fragment.BindMobileFragment;
import cn.jungmedia.android.ui.user.fragment.ForgetPwdFragment;
import cn.jungmedia.android.ui.user.fragment.LoginFragment;
import cn.jungmedia.android.ui.user.fragment.RegisterFragment;
import cn.jungmedia.android.ui.user.fragment.UpdatePwdFragment;
import cn.jungmedia.android.ui.user.fragment.UserInfoFragment;

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

    public static void intentToBloggerInfo(Context context, int uid, boolean status) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.FLAG_DATA, uid);
        bundle.putBoolean(AppConstant.FLAG_DATA2, status);
        intentToAct(context, "", BloggerFragment.class, bundle);
    }

    public static void intentToBloggerFav(Context context) {
        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.subscribe), BloggerFavFragment.class, bundle);

    }


    public static void intentToFans(Context context) {

        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.fans), FansListFragment.class, bundle);
    }

    public static void intentToScoreList(Context context) {

        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.score), ScoreListFragment.class, bundle);
    }

    /***
     * 普通文章详情
     * @param context
     * @param objectId
     */
    public static void intentToArticleDetail(Context context, int objectId) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.FLAG_DATA2, objectId);
        intentToAct(context, context.getString(R.string.article_detail), ArticleDetailFragment2.class, bundle);
    }

    /**
     * 活动详情
     *
     * @param context
     */
    public static void intentToActivityInfo(Context context, ActivityModel.Activity activity) {

        Bundle bundle = new Bundle();
        String activityUrl = String.format(ApiConstants.URL + "activity/i-%d.html", activity.getObjectId());
        bundle.putString(AppConstant.FLAG_DATA, activityUrl);
        bundle.putSerializable(AppConstant.FLAG_DATA2, activity);
        intentToAct(context, context.getString(R.string.activity), ActivityInfoFragment.class, bundle);

    }

    /***
     * 评论列表
     * @param context
     * @param articleId
     */
    public static void intentToCommentList(Context context, int articleId) {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.FLAG_DATA, articleId);
        intentToAct(context, context.getString(R.string.comment_list), CommentListFragment.class, bundle);

    }


    public static void intentToScoreRole(Context context) {
        String url = ApiConstants.getHost(HostType.Jung_FINANCE) + "me/points/guize";
        intentToCommonWeb(context, "积分规则", url);
    }


    public static void intentToMediaDiff(Context context) {

        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.refer), MediaDiffShowFragment.class, bundle);

    }

    public static void intentToNewsEdit(Context context) {

        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.info), NewsEditFragment.class, bundle);
    }

    public static void intentToHqEdit(Context context) {

        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.analyze), HqEditFragment.class, bundle);
    }

    public static void intentToFastEdit(Context context) {
        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.main_tab_fast), FastEditFragment.class, bundle);
    }

    public static void intentToActivityEdit(Context context) {

        Bundle bundle = new Bundle();
        intentToAct(context, context.getString(R.string.activity), ActivityEditFragment.class, bundle);
    }

    public static void intentToActivitySignup(Context context, int activeId) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.FLAG_DATA, activeId);
        intentToAct(context, context.getString(R.string.activity_signup), ActivitySignupFragment.class, bundle);
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
