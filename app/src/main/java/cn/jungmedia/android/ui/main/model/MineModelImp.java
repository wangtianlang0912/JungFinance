package cn.jungmedia.android.ui.main.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.main.contract.MineContract;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.utils.MyUtils;
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
    public Observable<BaseRespose<UserInfo>> getUserInfo() {
        return Api.getDefault(HostType.Jung_FINANCE)
                .getUserInfo(MyUtils.getToken())
                .map(new Func1<BaseRespose<UserInfo>, BaseRespose<UserInfo>>() {
                    @Override
                    public BaseRespose<UserInfo> call(BaseRespose<UserInfo> respose) {
                        return respose;
                    }
                }).compose(RxSchedulers.<BaseRespose<UserInfo>>io_main());
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
