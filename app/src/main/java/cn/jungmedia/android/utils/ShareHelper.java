package cn.jungmedia.android.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.leon.common.commonutils.ToastUitl;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.jungmedia.android.R;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/16. 下午11:46
 *
 *
 */
public class ShareHelper {

    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    public void share(Activity activity, String title, String content, String url) {
        share(activity, title, content, null, url, null);
    }

    public void share(final Activity activity, final String title, final String content, final String imageUrl, final String url, OnSharedListener sharedListener) {

        mShareListener = new CustomShareListener(activity, sharedListener);
        /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(activity).setDisplayList(

                SHARE_MEDIA.SINA,
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.MORE
//                ,
//                SHARE_MEDIA.QQ,
//                SHARE_MEDIA.QZONE
        )
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        UMWeb web = new UMWeb(url);
                        web.setTitle(title);
                        web.setDescription(content);
                        if (TextUtils.isEmpty(imageUrl)) {
                            web.setThumb(new UMImage(activity, R.drawable.logo_login));
                        } else {
                            web.setThumb(new UMImage(activity, imageUrl));
                        }

                        if (SHARE_MEDIA.WEIXIN.equals(share_media)
                                || SHARE_MEDIA.WEIXIN_CIRCLE.equals(share_media)
                                || SHARE_MEDIA.WEIXIN_FAVORITE.equals(share_media)) {

                            if (!isWeChatAppInstalled(activity)) {
                                ToastUitl.showShort("请先安装微信客户端");
                                return;
                            }
                        }

                        new ShareAction(activity).withMedia(web)
                                .setPlatform(share_media)
                                .setCallback(mShareListener)
                                .share();

                    }
                });

        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        mShareAction.open(config);
    }


    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isWeChatAppInstalled(Context context) {

        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<Activity> mActivity;
        private OnSharedListener onSharedListener;

        private CustomShareListener(Activity activity, OnSharedListener listener) {
            mActivity = new WeakReference(activity);
            this.onSharedListener = listener;
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }
            }
            if (onSharedListener != null) {
                onSharedListener.onSharedCompleted();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnSharedListener {

        void onSharedCompleted();
    }

}
