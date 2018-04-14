package cn.jungmedia.android.ui.setting.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.setting.contract.SettingContract;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/14. 下午2:22
 *
 *
 */
public class SettingPresenter extends SettingContract.Presenter {
    @Override
    public void logout() {
        mRxManage.add(mModel.logout().subscribe(new RxSubscriber<Boolean>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(Boolean result) {
                mView.returnLogout(result);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
