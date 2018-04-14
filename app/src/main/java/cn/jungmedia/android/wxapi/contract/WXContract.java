package com.jung.android.wxapi.contract;


import com.jung.android.ui.user.bean.UserInfo;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import rx.Observable;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/10. 下午11:16
 *
 *
 */
public class WXContract {

    public interface Model extends BaseModel {

        public Observable<UserInfo> userLogin(String openid, String nick, String logo);

    }

    public interface View extends BaseView {

        public void returnLoginData(UserInfo userInfo);

    }

    public static abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void userLogin(String openid, String nick, String logo);
    }
}
