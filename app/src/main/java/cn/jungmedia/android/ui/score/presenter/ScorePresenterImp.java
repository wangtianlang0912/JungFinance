package cn.jungmedia.android.ui.score.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.ui.score.bean.ScoreBean;
import cn.jungmedia.android.ui.score.contract.ScoreContract;
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
 * @date 2018/4/15. 下午10:29
 *
 *
 */
public class ScorePresenterImp extends ScoreContract.Presenter {
    @Override
    public void getScoreInfo(int startPage) {
        mRxManage.add(mModel.getScoreInfo(startPage).subscribe(new RxSubscriber<BaseRespose<ScoreBean>>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose<ScoreBean> data) {

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
