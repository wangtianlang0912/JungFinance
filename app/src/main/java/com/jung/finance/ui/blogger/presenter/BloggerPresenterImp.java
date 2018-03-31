package com.jung.finance.ui.blogger.presenter;

import com.jung.finance.R;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.ui.blogger.bean.BloggerBean;
import com.jung.finance.ui.blogger.contract.BloggerContract;
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
 * @date 2018/3/31. 下午10:44
 *
 *
 */
public class BloggerPresenterImp extends BloggerContract.Presenter {


    @Override
    public void getBloggerArticleList(int uid, int startPage) {
        mRxManage.add(mModel.getBloggerArticleList(uid, startPage).subscribe(new RxSubscriber<ArticleModel>(mContext, false) {
            @Override
            protected void _onNext(ArticleModel data) {
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

    @Override
    public void getBloggerInfo(int uid) {
        mRxManage.add(mModel.getBloggerInfo(uid).subscribe(new RxSubscriber<BloggerBean>(mContext, false) {
            @Override
            protected void _onNext(BloggerBean data) {
                mView.returnBloggerInfo(data);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
