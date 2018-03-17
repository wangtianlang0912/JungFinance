package com.jung.finance.ui.user.presenter;

import com.jung.finance.ui.user.bean.UserInfo;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import rx.Observable;

public interface UserContract {
    interface IRegisterModel extends BaseModel {
        //请求验证码
        Observable<String> getVerifyCode(String phone);

        Observable<String> register(String phone, String code, String pwd);
    }

    interface IRegisterView extends BaseView {
        void returnVerifyCode(String code);

        void returnRegisterResponse(String response);
    }

    interface ILoginModel extends BaseModel {
        Observable<Boolean> getVerifyCode(String phone);

        Observable<UserInfo> accountLogin(String phone, String pwd);

        Observable<UserInfo> mobileLogin(String phone, String code);
    }


    interface ILoginView extends BaseView {
        void returnVerifyCode(boolean result);

        void returnLoginResponse(UserInfo response);
    }


    abstract static class RegisterPresenter extends BasePresenter<IRegisterView, IRegisterModel> {
        public abstract void getVerifyCode(String phone);

        public abstract void register(String phone, String code, String pwd);
    }

    abstract static class LoginPresenter extends BasePresenter<ILoginView, ILoginModel> {
        public abstract void getVerifyCode(String phone);

        public abstract void accountLogin(String phone, String pwd);

        public abstract void mobileLogin(String phone, String code);
    }

}
