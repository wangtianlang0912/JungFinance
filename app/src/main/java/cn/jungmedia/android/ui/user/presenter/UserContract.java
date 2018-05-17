package cn.jungmedia.android.ui.user.presenter;

import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import cn.jungmedia.android.ui.user.bean.UserInfo;
import rx.Observable;

public interface UserContract {
    interface IRegisterModel extends BaseModel {
        //请求验证码
        Observable<BaseRespose<String>> getVerifyCode(String phone);

        Observable<BaseRespose<UserInfo>> register(String phone, String code, String pwd);
    }

    interface IRegisterView extends BaseView {
        void returnVerifyCode(BaseRespose<String> result);

        void returnRegisterResponse(BaseRespose<UserInfo> response);
    }

    abstract class RegisterPresenter extends BasePresenter<IRegisterView, IRegisterModel> {
        public abstract void getVerifyCode(String phone);

        public abstract void register(String phone, String code, String pwd);
    }


    interface ILoginModel extends BaseModel {
        Observable<BaseRespose<String>> getVerifyCode(String phone);

        Observable<BaseRespose<UserInfo>> accountLogin(String phone, String pwd);

        Observable<BaseRespose<UserInfo>> mobileLogin(String phone, String code);
    }


    interface ILoginView extends BaseView {
        void returnVerifyCode(BaseRespose<String> result);

        void returnLoginResponse(BaseRespose<UserInfo> response);
    }

    abstract class LoginPresenter extends BasePresenter<ILoginView, ILoginModel> {
        public abstract void getVerifyCode(String phone);

        public abstract void accountLogin(String phone, String pwd);

        public abstract void mobileLogin(String phone, String code);
    }


    interface IForgetPwdModel extends BaseModel {
        Observable<BaseRespose<String>> getVerifyCode(String phone);

        Observable<BaseRespose<UserInfo>> submit(String phone, String code, String pwd);
    }


    interface IForgetPwdView extends BaseView {
        void returnVerifyCode(BaseRespose<String> result);

        void returnSubmitResponse(BaseRespose<UserInfo> response);
    }

    abstract class ForgetPwdPresenter extends BasePresenter<IForgetPwdView, IForgetPwdModel> {
        public abstract void getVerifyCode(String phone);

        public abstract void submit(String phone, String code, String pwd);
    }


    interface IBindMobileModel extends BaseModel {
        Observable<BaseRespose<String>> getVerifyCode(String phone);

        Observable<BaseRespose> submit(String phone, String code, String pwd);
    }


    interface IBindMobileView extends BaseView {
        void returnVerifyCode(BaseRespose<String> result);

        void returnSubmitResponse(BaseRespose response);
    }

    abstract class BindMobilePresenter extends BasePresenter<IBindMobileView, IBindMobileModel> {
        public abstract void getVerifyCode(String phone);

        public abstract void submit(String phone, String code, String pwd);
    }


    interface IUpdatePwdModel extends BaseModel {
        Observable<BaseRespose> submit(String oldPwd, String newPwd);
    }


    interface IUpdatePwdView extends BaseView {

        void returnSubmitResponse(BaseRespose response);
    }

    abstract class UpdatePwdPresenter extends BasePresenter<IUpdatePwdView, IUpdatePwdModel> {
        public abstract void submit(String oldPwd, String newPwd);
    }

    interface IUserInfoModel extends BaseModel {
        Observable<BaseRespose<UserInfo>> submit(String nick, String desp, String phone, String logo);

        Observable<String> uploadImage(String image);
    }


    interface IUserInfoView extends BaseView {

        void returnSubmitResponse(BaseRespose<UserInfo> response);

        void returnUploadImage(String url);
    }

    abstract class UserInfoPresenter extends BasePresenter<IUserInfoView, IUserInfoModel> {
        public abstract void submit(String nick, String desp, String phone, String logo);

        public abstract void uploadImage(String image);
    }
}
