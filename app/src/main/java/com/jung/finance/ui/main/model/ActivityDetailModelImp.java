package com.jung.finance.ui.main.model;


import com.jung.finance.api.Api;
import com.jung.finance.api.HostType;
import com.jung.finance.bean.FavActionModel;
import com.jung.finance.bean.ActivityFavGetModel;
import com.jung.finance.ui.main.contract.ActivityDetailContract;
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
 * @date 2018/4/7. 下午4:32
 *
 *
 */
public class ActivityDetailModelImp implements ActivityDetailContract.Model {
    @Override
    public Observable<Boolean> attentActivity(int objectId) {
        return null;
    }

    @Override
    public Observable<Boolean> getFavActivityState(int activityId) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getActivityFavState(token, activityId)
                .map(new Func1<BaseRespose<ActivityFavGetModel>, Boolean>() {
                    @Override
                    public Boolean call(BaseRespose<ActivityFavGetModel> baseRespose) {
                        ActivityFavGetModel activityModel = baseRespose.data;
                        return activityModel != null && activityModel.getFavorite() != null;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<Boolean>io_main());
    }

    @Override
    public Observable<Boolean> favActionActivity(int activityId, final boolean status) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = null;
        if (status) {
            observable = Api.getDefault(HostType.Jung_FINANCE).unfavActivity(token, activityId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).favActivity(token, activityId);
        }
        return observable.map(new Func1<BaseRespose<FavActionModel>, Boolean>() {
            @Override
            public Boolean call(BaseRespose<FavActionModel> baseRespose) {
                if (status) {
                    if (baseRespose.success()) {
                        return false; // 返回的是当前收藏的状态
                    }
                    return status;
                } else {
                    FavActionModel activityModel = baseRespose.data;
                    return activityModel != null && activityModel.getFavorite() != null; // 收藏成功返回fav对象
                }
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<Boolean>io_main());
    }

}
