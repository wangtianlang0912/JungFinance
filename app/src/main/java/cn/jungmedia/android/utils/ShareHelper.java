package cn.jungmedia.android.utils;


import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

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

                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA
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
