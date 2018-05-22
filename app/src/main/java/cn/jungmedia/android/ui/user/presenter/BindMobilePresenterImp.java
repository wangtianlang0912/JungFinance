package cn.jungmedia.android.ui.user.presenter;


import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.utils.MyUtils;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/17. 下午11:58
 *
 *
 */
public class BindMobilePresenterImp extends UserContract.BindMobilePresenter {
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
                if (!MyUtils.verifyToken(data)) {
                    // error: "未找到授权凭证",
                    AppApplication.getInvalidCallback().onTokenInvalid();
                } else {
                    mView.returnVerifyCode(data);
                }
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void submit(String phone, String code, String pwd) {
        mRxManage.add(mModel.submit(phone, code, pwd).subscribe(new RxSubscriber<BaseRespose>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose data) {
                if (!MyUtils.verifyToken(data)) {
                    // error: "未找到授权凭证",
                    AppApplication.getInvalidCallback().onTokenInvalid();
                } else {
                    mView.returnSubmitResponse(data);
                }
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
