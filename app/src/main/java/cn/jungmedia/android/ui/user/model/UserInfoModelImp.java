package cn.jungmedia.android.ui.user.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.ui.user.presenter.UserContract;
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
                        UserInfo userInfo = respose.data;
                        if (userInfo != null && userInfo.getUser() != null) {
                            userInfo.getUser().setLogo(ApiConstants.getHost(HostType.Jung_FINANCE) + userInfo.getUser().getLogo());
                        }
                        return respose.data;
                    }
                }).compose(RxSchedulers.<UserInfo>io_main());
    }
}
