package cn.jungmedia.android.app;

import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * des:
 * Created by xsf
 * on 2016.09.10:44
 */
public class AppConstant {

    public static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";
    public static final String MENU_SHOW_HIDE = "MENU_SHOW_HIDE";

    /* 新闻*/
    public static final String NEWS_ID = "news_id";
    public static final String CHANNEL_POSITION = "channel_position";
    public static final String CHANNEL_MINE = "CHANNEL_MINE";
    public static final String CHANNEL_MORE = "CHANNEL_MORE";

    public static final String CHANNEL_SWAP = "CHANNEL_SWAP";
    public static final String NEWS_CHANNEL_CHANGED = "NEWS_CHANNEL_CHANGED";

    /* 视频*/
    public static final String VIDEO_TYPE = "VIDEO_TYPE";

    public static String NEWS_LIST_TO_TOP = "NEWS_LIST_TO_TOP";//列表返回顶部
    public static String ZONE_PUBLISH_ADD = "ZONE_PUBLISH_ADD";//发布说说

    public static String NEWS_POST_ID = "NEWS_POST_ID";//新闻详情id
    public static String NEWS_LINK = "NEWS_LINK";
    public static String NEWS_TITLE = "NEWS_TITLE";

    public static final String PHOTO_DETAIL_IMGSRC = "photo_detail_imgsrc";
    public static final String PHOTO_DETAIL = "photo_detail";
    public static final String PHOTO_TAB_CLICK = "PHOTO_TAB_CLICK";

    public static final String NEWS_IMG_RES = "news_img_res";
    public static final String TRANSITION_ANIMATION_NEWS_PHOTOS = "transition_animation_news_photos";

    public static final String FLAG_FRAGMENT = "frag-fragment";
    public static final String FLAG_NAME = "frag_name";
    public static final String FLAG_DATA = "frag_data";
    public static final String FLAG_DATA2 = "frag_data2";
    public static final String FLAG_DATA3 = "frag_data3";
    public static final String FLAG_BUNDLE = "frag_bundle";

    public static final String USERINFO_KEY = "userinfo";
    public static final String TOKEN_KEY = "token";

    public static final String APP_ID = "wx5164f014ada66b19"; //替换为申请到的app id
    public static final String APP_SECRET = "683f8e90479652077c509b74aa4b3bae";
    public static final String WX_CODE_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    public static final String WX_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
    public static IWXAPI wx_api; //全局的微信api对象
}
