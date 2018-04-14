package com.jung.android.ui.main.presenter;

import com.jung.finance.R;
import com.jung.android.bean.FastModel;
import com.jung.android.ui.news.contract.FastListContract;
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
 * @date 2018/3/28. 下午10:33
 *
 *
 */
public class FastListPresenterImp extends FastListContract.Presenter {
    @Override
    public void getNewsListDataRequest(int startPage) {
        mRxManage.add(mModel.getNewsListData(startPage).subscribe(new RxSubscriber<FastModel>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(FastModel articleModel) {
                mView.returnNewsListData(articleModel);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
