package com.jung.android.wxapi.model;


import com.jung.android.api.HostType;
import com.jung.android.api.Api;
import com.jung.android.ui.user.bean.UserInfo;
import com.jung.android.wxapi.contract.WXContract;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import rx.Observable;
import rx.functions.Func1;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/12. 下午11:45
 *
 *
 */
public class WxModel implements WXContract.Model {
    @Override
    public Observable<UserInfo> userLogin(String openid, String nick, String logo) {
        return Api.getDefault(HostType.Jung_FINANCE).weixinLogin(openid, nick, logo)
                .map(new Func1<BaseRespose<UserInfo>, UserInfo>() {
                    @Override
                    public UserInfo call(BaseRespose<UserInfo> response) {
                        return response.data;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<UserInfo>io_main());
    }
}
