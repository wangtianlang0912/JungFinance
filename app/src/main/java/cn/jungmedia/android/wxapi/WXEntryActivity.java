package com.jung.android.wxapi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jung.android.app.AppConstant;
import com.jung.android.ui.user.bean.UserInfo;
import com.jung.android.utils.MyUtils;
import com.jung.android.wxapi.bean.WXUserInfo;
import com.jung.android.wxapi.contract.WXContract;
import com.jung.android.wxapi.model.WxModel;
import com.jung.android.wxapi.presenter.WxPresenter;
import com.leon.common.base.BaseActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/12. 下午11:00
 *
 *
 */
public class WXEntryActivity extends BaseActivity<WxPresenter, WxModel> implements IWXAPIEventHandler, WXContract.View {

    private Handler mHandler;
    private OkHttpClient mOkHttpClient = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        mHandler = new Handler(getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        showErrorTip("微信授权请求失败");
                        finish();
                        break;
                }
            }
        };

        AppConstant.wx_api.handleIntent(getIntent(), this);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

    }

    //微信请求相应
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.i("WXTest", "onResp OK");

                if (resp instanceof SendAuth.Resp) {
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    //获取微信传回的code
                    String code = newResp.code;
                    Log.i("WXTest", "onResp code = " + code);

                    final String url = String.format(AppConstant.WX_CODE_ACCESSTOKEN, AppConstant.APP_ID, AppConstant.APP_SECRET, code);
                    new Thread() {
                        @Override
                        public void run() {

                            String response = requestGetBySyn(mOkHttpClient, url);
                            if (!TextUtils.isEmpty(response)) {

                                JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
                                if (jsonObject == null) {
                                    mHandler.sendEmptyMessage(1);
                                    return;
                                }
                                String accessToken = jsonObject.get("access_token").getAsString();
                                String openid = jsonObject.get("openid").getAsString();

                                if (TextUtils.isEmpty(accessToken) || TextUtils.isEmpty(openid)) {
                                    mHandler.sendEmptyMessage(1);
                                    return;
                                }
                                String userUrl = String.format(AppConstant.WX_GET_USERINFO, accessToken, openid);
                                String userResp = requestGetBySyn(mOkHttpClient, userUrl);
                                WXUserInfo userInfo = new Gson().fromJson(userResp, WXUserInfo.class);
                                if (userInfo == null) {
                                    mHandler.sendEmptyMessage(1);
                                    return;
                                }
                                mPresenter.userLogin(userInfo.getOpenid(), userInfo.getNickname(), userInfo.getHeadimgurl());
                            }

                        }
                    }.start();

                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.i("WXTest", "onResp ERR_USER_CANCEL ");
                //发送取消
                showErrorTip("请求已取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.i("WXTest", "onResp ERR_AUTH_DENIED");
                //发送被拒绝
                showErrorTip("请求被拒绝");
                finish();
                break;
            default:
                Log.i("WXTest", "onResp default errCode " + resp.errCode);
                //发送返回
                showErrorTip("未知错误");
                finish();
                break;
        }


    }


    private String requestGetBySyn(OkHttpClient okHttpClient, String requestUrl) {
        try {
            //创建一个请求
            Request request = new Request.Builder().url(requestUrl).build();
            //创建一个Call
            final Call call = okHttpClient.newCall(request);
            //执行请求
            final Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            Log.e("WXTest", e.toString());
        }
        return null;
    }

    @Override
    public void showLoading(String title) {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showShortToast(msg);
    }

    @Override
    public void returnLoginData(UserInfo response) {
        if (response != null && response.getToken() != null) {
            MyUtils.saveUserInfo(this, response);
            MyUtils.saveToken(this, response.getToken());
        }
        finish();
    }
}