package cn.jungmedia.android.ui.main.model;

import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.bean.ActivityModel;
import cn.jungmedia.android.ui.main.contract.ActivityContract;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

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
 * @date 2018/3/31. 下午5:46
 *
 *
 */
public class ActivityModelImp implements ActivityContract.Model {
    @Override
    public Observable<ActivityModel> lodeActivityList(int startPage) {
        return Api.getDefault(HostType.Jung_FINANCE).getActivityList(Api.getCacheControl(), startPage)
                .map(new Func1<BaseRespose<ActivityModel>, ActivityModel>() {
                    @Override
                    public ActivityModel call(BaseRespose<ActivityModel> baseRespose) {
                        ActivityModel activityModel = baseRespose.data;
                        for (ActivityModel.Activity activity : activityModel.getActivities()) {
                            String coverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + activity.getCoverImage();
                            activity.setCoverImage(coverImage);

                            String startTime = TimeUtil.formatData(TimeUtil.dateFormatYMDHMS, activity.getStime());
                            activity.setStartTime(startTime);

                            String endTime = TimeUtil.formatData(TimeUtil.dateFormatYMDHMS, activity.getEtime());
                            activity.setEndTime(endTime);
                        }
                        return activityModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ActivityModel>io_main());
    }
}
