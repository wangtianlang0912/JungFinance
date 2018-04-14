package cn.jungmedia.android.ui.setting.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.setting.contract.SettingContract;
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
 * @date 2018/4/14. 下午2:24
 *
 *
 */
public class SettingModel implements SettingContract.Model {
    @Override
    public Observable<Boolean> logout() {

        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).logout(token)
                .map(new Func1<BaseRespose<String>, Boolean>() {
                    @Override
                    public Boolean call(BaseRespose<String> respose) {
                        return respose.success();
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<Boolean>io_main());
    }

}
