package com.jung.finance.ui.user.presenter;

import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import rx.Observable;

public interface UserContract {
    interface RegisterModel extends BaseModel {
        //请求验证码
        Observable<String> getVerifyCode(String phone);
    }

    interface RegisterView extends BaseView {
        void returnVerifyCode(String code);
    }

    abstract static class RegisterPresenter extends BasePresenter<RegisterView, RegisterModel> {
        public abstract void getVerifyCode(String phone);
    }
}
