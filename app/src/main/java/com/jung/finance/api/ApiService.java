package com.jung.finance.api;

import com.jung.finance.bean.ActivityFavGetModel;
import com.jung.finance.bean.ActivityModel;
import com.jung.finance.bean.ArticleDetail;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.bean.BannerModel;
import com.jung.finance.bean.BloggerModel;
import com.jung.finance.bean.ColumnModel;
import com.jung.finance.bean.CommentCreateModel;
import com.jung.finance.bean.CommentListModel;
import com.jung.finance.bean.FastModel;
import com.jung.finance.bean.FavActionModel;
import com.jung.finance.bean.GirlData;
import com.jung.finance.bean.LinkModel;
import com.jung.finance.bean.NewsDetail;
import com.jung.finance.bean.NewsSummary;
import com.jung.finance.bean.TopicModel;
import com.jung.finance.bean.VideoData;
import com.jung.finance.ui.blogger.bean.BloggerBean;
import com.jung.finance.ui.main.bean.ScoreInfo;
import com.jung.finance.ui.user.bean.UserInfo;
import com.leon.common.basebean.BaseRespose;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * des:ApiService
 * Created by xsf
 * on 2016.06.15:47
 */
public interface ApiService {

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewDetail(
            @Header("Cache-Control") String cacheControl,
            @Path("postId") String postId);

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Header("Cache-Control") String cacheControl,
            @Url String photoPath);
    //@Url，它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视
    // baseUrl 需要符合标准，为空、""、或不合法将会报错

    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoData>>> getVideoList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("startPage") int startPage);

    //注册
    @GET("app/user/register/phone/phonecode")
    Observable<BaseRespose<String>> getVerifyCode(@Query("phone") String phone);

    //注册
    @POST("app/user/register/phone/register")
    Observable<BaseRespose<UserInfo>> register(@Query("phone") String phone, @Query("phonecode") String code, @Query("password") String password);

    //发送短信验证码[手机快速登录]
    @GET("app/user/login/phone/phonecode")
    Observable<BaseRespose<String>> sendSMSCode(@Query("phone") String phone);

    //手机快速登录
    @POST("app/user/login/phone/login")
    Observable<BaseRespose<UserInfo>> loginByMobile(@Query("phone") String phone, @Query("phonecode") String code);

    //账户登录
    @POST("/app/user/login/login")
    Observable<BaseRespose<UserInfo>> login(@Query("account") String account, @Query("password") String password);

    //发送短信验证码[忘记密码]
    @GET("app/user/forgot/phone/phonecode")
    Observable<BaseRespose<String>> sendSMSCodeForForgetPwd(@Query("phone") String phone);

    //重置密码
    @POST("app/user/forgot/phone/reset")
    Observable<BaseRespose<UserInfo>> reset(@Query("phone") String phone, @Query("phonecode") String code, @Query("password") String password);

    //注销
    @POST("app/auth/logout")
    Observable<BaseRespose<String>> logout(@Query("token") String token);

    //获取当前登录用户ID
    @GET("app/auth/me")
    Observable<BaseRespose<UserInfo>> me(@Query("token") String token);

    //修改手机号
    @POST("app/me/phone/set")
    Observable<BaseRespose<UserInfo>> updateMobile(@Query("token") String token, @Query("phone") String phone, @Query("phonecode") String code, @Query("password") String password);

    //绑定手机号
    @POST("app/me/phone/bind")
    Observable<BaseRespose> bindMobile(@Query("token") String token, @Query("phone") String phone, @Query("phonecode") String code, @Query("password") String password);

    //发送短信验证码 (绑定手机号,修改手机号)
    @GET("app/me/phone/phonecode")
    Observable<BaseRespose<String>> sendSMSCodeByToken(@Query("token") String token, @Query("phone") String phone);

    //设置用户登录密码
    @POST("app/me/password/set")
    Observable<BaseRespose> updatePwd(@Query("token") String token, @Query("password") String password, @Query("newpassword") String newpassword, @Query("repassword") String repassword);

    //获取用户信息
    @GET("app/me/get")
    Observable<BaseRespose<UserInfo>> getUserInfo(@Query("token") String token);

    @GET("app/me/score/get")
    Observable<BaseRespose<ScoreInfo>> getScoreInfo(@Query("token") String token);

    //修改用户信息
    @POST("app/me/set")
    Observable<BaseRespose<UserInfo>> updateUserInfo(@Query("token") String token, @Query("nick") String nick, @Query("phone") String phone, @Query("logo") String logo, @Query("remark") String desp);


    // 分类
    @GET("app/column/select")
    Observable<BaseRespose<ColumnModel>> getColumnCategoryList();

    //首页文章/区块链/珠宝app/article/query?columnId=21&p=1
    @GET("app/article/query")
    Observable<BaseRespose<ArticleModel>> getArtileList(@Header("Cache-Control") String cacheControl, @Query("isHead") int isHead, @Query("columnId") String columnId, @Query("p") int page);

    // 获取文章详情
    @GET("app/media/article/get")
    Observable<BaseRespose<ArticleDetail>> getArticleDetail(@Query("id") String id);

    // 获取推荐文章
    @GET("app/article/relevant")
    Observable<BaseRespose<ArticleModel>> getRelateList(String id);

    //首页banner
    @GET("app/banner/query")
    Observable<BaseRespose<BannerModel>> getBannerList();

    //首页下载地址
    @GET("app/link/get")
    //site = home&alias=top
    Observable<BaseRespose<LinkModel>> getAdList(@Query("site") String columnId, @Query("alias") String type);

    //名家uid=2977
    @GET("app/media/query")
//    app/media/query?p=1&uid=2977
    Observable<BaseRespose<BloggerModel>> getBloggerList(@Header("Cache-Control") String cacheControl, @Query("uid") String uid, @Query("p") int page);

    //专题uid=2977
    @GET("app/theme/query")
    Observable<BaseRespose<TopicModel>> getTopicList(@Header("Cache-Control") String cacheControl, @Query("uid") String uid, @Query("p") int page);


    @GET("app/fast/query")
    Observable<BaseRespose<FastModel>> getFastCommentList(@Header("Cache-Control") String cacheControl, @Query("p") int page, @Query("size") int pageSize);

    @GET("app/activity/query")
    Observable<BaseRespose<ActivityModel>> getActivityList(@Header("Cache-Control") String cacheControl, @Query("p") int page);

    // 获取自媒体
    @GET("app/media/get")
    Observable<BaseRespose<BloggerBean>> getBloggerInfo(@Query("id") int id, @Query("uid") int uid);

    // 获取自媒体列表
    @GET("app/media/article/query")
    Observable<BaseRespose<ArticleModel>> getBloggerArticleList(@Query("mediaId") int id, @Query("uid") int uid, @Query("p") int page);

    // 收藏文章
    @POST("app/me/article/fav/create")
    Observable<BaseRespose<FavActionModel>> favArticle(@Query("token") String token, @Query("id") int articleId);

    // 获取收藏
    @GET("app/me/article/fav/get")
    Observable<BaseRespose<FavActionModel>> getArticleFavState(@Query("token") String token, @Query("id") int articleId);


    // 取消收藏文章
    @POST("app/me/article/fav/remove")
    Observable<BaseRespose<FavActionModel>> unfavArticle(@Query("token") String token, @Query("id") int articleId);

    // 参加活动
    @POST("app/me/activity/join")
    Observable<BaseRespose<Boolean>> joinActivity(@Query("token") String token, @Query("id") String activeId,
                                                  @Query("name") String name, @Query("phone") String phone,
                                                  @Query("number") int number, @Query("company") String company);

    // 收藏活动
    @POST("app/me/activity/fav/create")
    Observable<BaseRespose<FavActionModel>> favActivity(@Query("token") String token, @Query("id") int activeId);

    // 取消收藏活动
    @POST("app/me/activity/fav/remove")
    Observable<BaseRespose<FavActionModel>> unfavActivity(@Query("token") String token, @Query("id") int activeId);

    // 获取收藏状态
    @GET("app/me/activity/fav/get")
    Observable<BaseRespose<ActivityFavGetModel>> getActivityFavState(@Query("token") String token, @Query("id") int activeId);

    // 获取我收藏的活动
    @GET("app/me/activity/fav/query")
    Observable<BaseRespose<ActivityModel>> getActivityFavList(@Query("token") String token, @Query("id") String activeId,
                                                              @Query("type") String type, @Query("orderBy") String orderBy,
                                                              @Query("p") int p, @Query("size") int size);


    // 收藏媒体
    @POST("app/me/media/fav/create")
    Observable<BaseRespose<FavActionModel>> focusMedia(@Query("token") String token, @Query("id") int mediaId);

    // 移除收藏项目
    @POST("app/me/media/fav/remove")
    Observable<BaseRespose<FavActionModel>> unFocusMedia(@Query("token") String token, @Query("id") int mediaId);

    // 创建评论
    @POST("app/comment/create")
    Observable<BaseRespose<CommentCreateModel>> createComment(@Query("token") String token, @Query("id") int id,
                                                              @Query("body") String body, @Query("touid") String touid);

    // 评论列表
    @GET("app/comment/query")
    Observable<BaseRespose<CommentListModel>> getCommentList(@Query("id") int id, @Query("touid") int touid,
                                                             @Query("p") int page, @Query("size") int size);


}
