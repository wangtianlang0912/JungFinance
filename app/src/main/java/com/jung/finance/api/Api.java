package com.jung.finance.api;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.$Gson$Types;
import com.leon.common.baseapp.BaseApplication;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.LogUtils;
import com.leon.common.commonutils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * des:retorfit api
 * Created by xsf
 * on 2016.06.15:47
 */
public class Api {
    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 7676;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 7676;
    public Retrofit retrofit;
    public ApiService apiService;
    public OkHttpClient okHttpClient;
    private static SparseArray<Api> sRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);

    /*************************缓存设置*********************/
/*
   1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存*/

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";


    //构造方法私有
    private Api(int hostType) {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(build);
            }
        };

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls();
        gsonBuilder.registerTypeAdapter(BaseRespose.class, new BaseResponseJsonDeserializer());
        Gson gson = gsonBuilder.create();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.baseUrl(ApiConstants.getHost(hostType));
        retrofit = builder.build();
        apiService = retrofit.create(ApiService.class);
    }

    private class BaseResponseJsonDeserializer<T> implements JsonDeserializer<BaseRespose<T>> {
        @Override
        public BaseRespose<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls();

            BaseRespose<T> baseResponse = new BaseRespose<>();

            if (json.isJsonNull()) {
                baseResponse.code = "-1";
                baseResponse.msg = "response null";
                baseResponse.data = newInstance(typeOfT);
                return baseResponse;
            }
            if ("{}".equals(json.toString())) {
                baseResponse.data = newInstance(typeOfT);
                return baseResponse;
            }
            if (json.isJsonObject()) {
                JsonObject jsonObj = json.getAsJsonObject();

                if (jsonObj.has("error") || jsonObj.has("error-code")) {
                    if (jsonObj.has("error")) {
                        baseResponse.msg = jsonObj.get("error").getAsString();
                    }
                    if (jsonObj.has("error-code")) {
                        baseResponse.code = jsonObj.get("error-code").getAsString();
                    }

                    baseResponse.data = newInstance(typeOfT);
                    return baseResponse;
                }
                Type type = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                baseResponse.data = gsonBuilder.create().fromJson(json, type);

            } else {

                Type type = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                baseResponse.data = gsonBuilder.create().fromJson(json, type);
            }

            return baseResponse;
        }

        private T newInstance(Type typeOfT) {

            Type type = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            Class<?> rawTypeOfSrc = $Gson$Types.getRawType(type);
            try {
                return (T) rawTypeOfSrc.newInstance();
            } catch (Exception e) {
                LogUtils.loge(e, " baseResponse.data = (T) rawTypeOfSrc.newInstance()");
            }
            return null;
        }

    }


    /**
     * @param hostType NETEASE_NEWS_VIDEO：1 （新闻，视频），GANK_GIRL_PHOTO：2（图片新闻）;
     *                 EWS_DETAIL_HTML_PHOTO:3新闻详情html图片)
     */
    public static ApiService getDefault(int hostType) {
        Api retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new Api(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
        }
        return retrofitManager.apiService;
    }

    /**
     * OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        Api retrofitManager = sRetrofitManager.get(HostType.NETEASE_NEWS_VIDEO);
        if (retrofitManager == null) {
            retrofitManager = new Api(HostType.NETEASE_NEWS_VIDEO);
            sRetrofitManager.put(HostType.NETEASE_NEWS_VIDEO, retrofitManager);
        }
        return retrofitManager.okHttpClient;
    }


    /**
     * 根据网络状况获取缓存的策略
     */
    @NonNull
    public static String getCacheControl() {
        return NetWorkUtils.isNetConnected(BaseApplication.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}