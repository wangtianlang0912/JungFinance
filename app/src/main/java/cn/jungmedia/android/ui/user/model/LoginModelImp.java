package cn.jungmedia.android.ui.user.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.ui.user.presenter.UserContract;
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
 * @date 2018/3/17. 下午7:27
 *
 *
 */
public class LoginModelImp implements UserContract.ILoginModel {
    @Override
    public Observable<BaseRespose<String>> getVerifyCode(String phone) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .sendSMSCode(phone)
                .map(new Func1<BaseRespose<String>, BaseRespose<String>>() {
                    @Override
                    public BaseRespose<String> call(BaseRespose<String> respose) {
                        return respose;
                    }
                }).compose(RxSchedulers.<BaseRespose<String>>io_main());
    }

    @Override
    public Observable<BaseRespose<UserInfo>> accountLogin(String phone, String pwd) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .login(phone,pwd)
              .compose(RxSchedulers.<BaseRespose<UserInfo>>io_main());
    }

    @Override
    public Observable<BaseRespose<UserInfo>> mobileLogin(String phone, String code) {
        return Api.getDefault(HostType.Jung_FINANCE)
                .loginByMobile(phone,code)
                .compose(RxSchedulers.<BaseRespose<UserInfo>>io_main());

    }
}
