package com.jung.android.ui.news.presenter;


import com.jung.android.bean.BloggerModel;
import com.jung.android.ui.news.contract.BloggerListContract;
import com.jung.finance.R;
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
 * @date 2018/3/24. 下午4:53
 *
 *
 */
public class BloggerListPresenter extends BloggerListContract.Presenter {
    @Override
    public void getBloggerListDataRequest(String uid, int startPage) {
        mRxManage.add(mModel.getListData(uid, startPage).subscribe(new RxSubscriber<BloggerModel>(mContext, false) {
            @Override
            protected void _onNext(BloggerModel data) {
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
