package com.jung.finance.api;

import com.jung.finance.bean.GirlData;
import com.jung.finance.bean.NewsDetail;
import com.jung.finance.bean.NewsSummary;
import com.jung.finance.bean.VideoData;
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


    @GET("app/user/register/phone/phonecode")
    Observable<BaseRespose<String>> getVerifyCode(@Query("phone") String phone);

    @POST("app/user/register/phone/register")
    Observable<BaseRespose<UserInfo>> register(@Query("phone") String phone, @Query("phonecode") String code, @Query("password") String password);

    //发送短信验证码[手机快速登录]
    @GET("app/user/login/phone/phonecode")
    Observable<BaseRespose<String>> sendSMSCode(@Query("phone") String phone);

    //手机快速登录
    @POST("app/user/login/phone/login")
    Observable<BaseRespose<UserInfo>> loginByMobile(@Query("phone") String phone, @Query("phonecode") String code);

    @POST("/app/user/login/login")
    Observable<BaseRespose<UserInfo>> login(@Query("account") String account, @Query("password") String password);


    //发送短信验证码[忘记密码]
    @GET("app/user/forgot/phone/phonecode")
    Observable<BaseRespose<String>> sendSMSCodeForForgetPwd(@Query("phone") String phone);

    @POST("app/user/forgot/phone/reset")
    Observable<BaseRespose<UserInfo>> reset(@Query("phone") String phone, @Query("phonecode") String code, @Query("password") String password);
}
