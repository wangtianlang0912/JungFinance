package cn.jungmedia.android.ui.user.presenter;


import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.user.bean.UserInfo;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/18. 下午3:18
 *
 *
 */
public class UserInfoPresenterImp extends UserContract.UserInfoPresenter {
    @Override
    public void submit(String nick, String desp, String phone, String logo) {
        mRxManage.add(mModel.submit(nick, desp, phone, logo).subscribe(new RxSubscriber<BaseRespose<UserInfo>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose<UserInfo> data) {
                mView.returnSubmitResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void uploadImage(String image) {
        mRxManage.add(mModel.uploadImage(image).subscribe(new RxSubscriber<String>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(String data) {
                mView.returnUploadImage(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
