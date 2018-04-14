package com.jung.android.ui.user.model;

import com.jung.android.api.HostType;
import com.jung.android.api.Api;
import com.jung.android.ui.user.presenter.UserContract;
import com.jung.android.utils.MyUtils;
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
 * @date 2018/3/17. 下午11:57
 *
 *
 */
public class BindMobileModelImp implements UserContract.IBindMobileModel {
    @Override
    public Observable<BaseRespose<String>> getVerifyCode(String phone) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .sendSMSCodeByToken(MyUtils.getToken(), phone)
                .map(new Func1<BaseRespose<String>, BaseRespose<String>>() {
                    @Override
                    public BaseRespose<String> call(BaseRespose<String> respose) {
                        return respose;
                    }
                }).compose(RxSchedulers.<BaseRespose<String>>io_main());
    }

    @Override
    public Observable<BaseRespose> submit(String phone, String code, String pwd) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .bindMobile(MyUtils.getToken(), phone, code, pwd).map(new Func1<BaseRespose, BaseRespose>() {
                    @Override
                    public BaseRespose call(BaseRespose respose) {
                        return respose;
                    }
                }).compose(RxSchedulers.<BaseRespose>io_main());
    }

}
