package cn.jungmedia.android.wxapi.model;


import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.wxapi.contract.WXContract;
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
 * @date 2018/4/12. 下午11:45
 *
 *
 */
public class WxModel implements WXContract.Model {
    @Override
    public Observable<UserInfo> userLogin(String openid, String nick, String logo) {
        return Api.getDefault(HostType.Jung_FINANCE).weixinLogin(openid, nick, logo)
                .map(new Func1<BaseRespose<UserInfo>, UserInfo>() {
                    @Override
                    public UserInfo call(BaseRespose<UserInfo> response) {
                        return response.data;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<UserInfo>io_main());
    }
}
