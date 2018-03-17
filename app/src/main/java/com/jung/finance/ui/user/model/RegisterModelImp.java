package com.jung.finance.ui.user.model;


import com.jung.finance.api.Api;
import com.jung.finance.api.HostType;
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
 * @date 2018/3/17. 下午6:58
 *
 *
 */
public class RegisterModelImp implements UserContract.IRegisterModel {
    @Override
    public Observable<String> getVerifyCode(String phone) {

        return Api.getDefault(HostType.Jung_FINANCE)
                .getVerifyCode(phone)
                .map(new Func1<BaseRespose<String>, String>() {
                    @Override
                    public String call(BaseRespose<String> respose) {
                        return respose.data;
                    }
                }).compose(RxSchedulers.<String>io_main());
    }

    @Override
    public Observable<String> register(String phone, String code, String pwd) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .register(phone, code, pwd).map(new Func1<BaseRespose<String>, String>() {
                    @Override
                    public String call(BaseRespose<String> respose) {
                        return respose.data;
                    }
                }).compose(RxSchedulers.<String>io_main());
    }
}
