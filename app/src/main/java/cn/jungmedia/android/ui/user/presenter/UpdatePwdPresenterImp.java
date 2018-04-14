package cn.jungmedia.android.ui.user.presenter;


import cn.jungmedia.android.R;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/18. 上午12:17
 *
 *
 */
public class UpdatePwdPresenterImp extends UserContract.UpdatePwdPresenter {
    @Override
    public void submit(String oldPwd, String newPwd) {
        mRxManage.add(mModel.submit(oldPwd, newPwd).subscribe(new RxSubscriber<BaseRespose>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose data) {
                mView.returnSubmitResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }


}
