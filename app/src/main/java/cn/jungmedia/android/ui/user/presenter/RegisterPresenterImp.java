package cn.jungmedia.android.ui.user.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.user.bean.UserInfo;

public class RegisterPresenterImp extends UserContract.RegisterPresenter {

    @Override
    public void getVerifyCode(String phone) {
        mRxManage.add(mModel.getVerifyCode(phone).subscribe(new RxSubscriber<BaseRespose<String>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose<String> data) {
                mView.returnVerifyCode(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void register(String phone, String code, String pwd) {

        mRxManage.add(mModel.register(phone, code, pwd).subscribe(new RxSubscriber<BaseRespose<UserInfo>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose<UserInfo> data) {
                mView.returnRegisterResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
