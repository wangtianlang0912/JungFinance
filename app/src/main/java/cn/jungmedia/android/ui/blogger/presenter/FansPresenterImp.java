package cn.jungmedia.android.ui.blogger.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.ui.blogger.bean.FansBean;
import cn.jungmedia.android.ui.blogger.contract.FansContract;
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
 * @date 2018/4/15. 下午10:08
 *
 *
 */
public class FansPresenterImp extends FansContract.Presenter {
    @Override
    public void getFansList(int startPage) {
        mRxManage.add(mModel.getFansList(startPage).subscribe(new RxSubscriber<BaseRespose<FansBean>>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose<FansBean> data) {
                if (!MyUtils.verifyToken(data)) {
                    AppApplication.getInvalidCallback().onTokenInvalid();
                    return;
                } else {
                    mView.returnListData(data.data);
                }

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
