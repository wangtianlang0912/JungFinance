package cn.jungmedia.android.ui.score.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.score.bean.ScoreBean;
import cn.jungmedia.android.ui.score.contract.ScoreContract;


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
        mRxManage.add(mModel.getScoreInfo(startPage).subscribe(new RxSubscriber<ScoreBean>(mContext, false) {
            @Override
            protected void _onNext(ScoreBean data) {
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
