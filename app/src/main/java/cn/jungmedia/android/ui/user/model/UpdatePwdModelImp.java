package com.jung.android.ui.user.model;


import com.jung.android.api.Api;
import com.jung.android.api.HostType;
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
 * @date 2018/3/18. 上午12:17
 *
 *
 */
public class UpdatePwdModelImp implements UserContract.IUpdatePwdModel {

    @Override
    public Observable<BaseRespose> submit(String oldPwd, String newPwd) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .updatePwd(MyUtils.getToken(), oldPwd, newPwd, newPwd).map(new Func1<BaseRespose, BaseRespose>() {
                    @Override
                    public BaseRespose call(BaseRespose respose) {
                        return respose;
                    }
                }).compose(RxSchedulers.<BaseRespose>io_main());
    }
}
