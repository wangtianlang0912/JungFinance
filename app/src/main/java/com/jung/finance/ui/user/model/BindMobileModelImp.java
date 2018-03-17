package com.jung.finance.ui.user.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.HostType;
import com.jung.finance.ui.user.bean.UserInfo;
import com.jung.finance.ui.user.presenter.UserContract;
import com.jung.finance.utils.MyUtils;
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
    public Observable<UserInfo> submit(String phone, String code, String pwd) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .bindMobile(MyUtils.getToken(), phone, code, pwd).map(new Func1<BaseRespose<UserInfo>, UserInfo>() {
                    @Override
                    public UserInfo call(BaseRespose<UserInfo> respose) {
                        return respose.data;
                    }
                }).compose(RxSchedulers.<UserInfo>io_main());
    }

}
