package com.jung.android.ui.main.model;

import com.jung.android.api.HostType;
import com.jung.android.ui.main.contract.MineContract;
import com.jung.android.api.Api;
import com.jung.android.ui.user.bean.UserInfo;
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
 * @date 2018/3/18. 下午2:36
 *
 *
 */
public class MineModelImp implements MineContract.IMineModel {
    @Override
    public Observable<UserInfo> getUserInfo() {
        return Api.getDefault(HostType.Jung_FINANCE)
                .getUserInfo(MyUtils.getToken())
                .map(new Func1<BaseRespose<UserInfo>, UserInfo>() {
                    @Override
                    public UserInfo call(BaseRespose<UserInfo> respose) {
                        return respose.data;
                    }
                }).compose(RxSchedulers.<UserInfo>io_main());
    }

//    @Override
//    public Observable<ScoreInfo> getScoreInfo() {
//        return Api.getDefault(HostType.Jung_FINANCE)
//                .getScoreInfo(MyUtils.getToken())
//                .map(new Func1<BaseRespose<ScoreInfo>, ScoreInfo>() {
//                    @Override
//                    public ScoreInfo call(BaseRespose<ScoreInfo> respose) {
//                        return respose.data;
//                    }
//                }).compose(RxSchedulers.<ScoreInfo>io_main());
//    }
}
