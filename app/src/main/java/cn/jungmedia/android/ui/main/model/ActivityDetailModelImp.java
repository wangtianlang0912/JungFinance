package com.jung.android.ui.main.model;


import com.jung.android.api.Api;
import com.jung.android.api.HostType;
import com.jung.android.bean.ActivityFavModel;
import com.jung.android.ui.main.contract.ActivityDetailContract;
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
    public Observable<ActivityFavModel.Favorite> getFavActivityState(int activityId) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getActivityFavState(token, activityId)
                .map(new Func1<BaseRespose<ActivityFavModel>, ActivityFavModel.Favorite>() {
                    @Override
                    public ActivityFavModel.Favorite call(BaseRespose<ActivityFavModel> baseRespose) {
                        ActivityFavModel activityModel = baseRespose.data;
                        return activityModel.getFavorite();
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ActivityFavModel.Favorite>io_main());
    }

    @Override
    public Observable<ActivityFavModel.Favorite> favActionActivity(int activityId, final boolean status) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<ActivityFavModel>> observable = null;
        if (status) {
            observable = Api.getDefault(HostType.Jung_FINANCE).unfavActivity(token, activityId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).favActivity(token, activityId);
        }
        return observable.map(new Func1<BaseRespose<ActivityFavModel>, ActivityFavModel.Favorite>() {
            @Override
            public ActivityFavModel.Favorite call(BaseRespose<ActivityFavModel> baseRespose) {

                return baseRespose.data.getFavorite();
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<ActivityFavModel.Favorite>io_main());
    }

}
