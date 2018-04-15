package cn.jungmedia.android.ui.blogger.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.blogger.bean.FansBean;
import cn.jungmedia.android.ui.blogger.contract.FansContract;


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
        mRxManage.add(mModel.getFansList(startPage).subscribe(new RxSubscriber<FansBean>(mContext, false) {
            @Override
            protected void _onNext(FansBean data) {
                mView.returnListData(data);
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
