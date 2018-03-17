package com.jung.finance.ui.user.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.HostType;
import com.jung.finance.ui.user.bean.UserInfo;
import com.jung.finance.ui.user.presenter.UserContract;
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
 * @date 2018/3/17. 下午7:27
 *
 *
 */
public class LoginModelImp implements UserContract.ILoginModel {
    @Override
    public Observable<Boolean> getVerifyCode(String phone) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .sendSMSCode(phone)
                .map(new Func1<BaseRespose<String>, Boolean>() {
                    @Override
                    public Boolean call(BaseRespose<String> respose) {
                        return respose.success();
                    }
                }).compose(RxSchedulers.<Boolean>io_main());
    }

    @Override
    public Observable<UserInfo> accountLogin(String phone, String pwd) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .login(phone,pwd)
                .map(new Func1<BaseRespose<UserInfo>, UserInfo>() {
                    @Override
                    public UserInfo call(BaseRespose<UserInfo> respose) {
                        return respose.data;
                    }
                }).compose(RxSchedulers.<UserInfo>io_main());
    }

    @Override
    public Observable<UserInfo> mobileLogin(String phone, String code) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .loginByMobile(phone,code)
                .map(new Func1<BaseRespose<UserInfo>, UserInfo>() {
                    @Override
                    public UserInfo call(BaseRespose<UserInfo> respose) {
                        return respose.data;
                    }
                }).compose(RxSchedulers.<UserInfo>io_main());

    }
}
