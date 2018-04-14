package cn.jungmedia.android.wxapi.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.wxapi.contract.WXContract;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/12. 下午11:46
 *
 *
 */
public class WxPresenter extends WXContract.Presenter {
    @Override
    public void userLogin(String openid, String nick, String logo) {
        mRxManage.add(mModel.userLogin(openid, nick, logo).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            protected void _onNext(UserInfo data) {
                mView.returnLoginData(data);
                mView.stopLoading();
            }

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
