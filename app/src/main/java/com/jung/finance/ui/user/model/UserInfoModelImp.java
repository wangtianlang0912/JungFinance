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
 * @date 2018/3/18. 下午3:19
 *
 *
 */
public class UserInfoModelImp implements UserContract.IUserInfoModel {
    @Override
    public Observable<UserInfo> submit(String nick, String desp, String phone, String logo) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .updateUserInfo(MyUtils.getToken(), nick, phone, logo, desp).map(new Func1<BaseRespose<UserInfo>, UserInfo>() {
                    @Override
                    public UserInfo call(BaseRespose<UserInfo> respose) {
                        return respose.data;
                    }
                }).compose(RxSchedulers.<UserInfo>io_main());
    }
}
