package com.jung.finance.ui.news.presenter;

import com.jung.finance.bean.CommentCreateModel;
import com.jung.finance.bean.CommentListModel;
import com.jung.finance.ui.news.contract.CommentListContract;
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
 * @date 2018/4/7. 下午11:01
 *
 *
 */
public class CommentListPresenter extends CommentListContract.Presenter {
    @Override
    public void createComment(int articleId, String value) {
        mRxManage.add(mModel.createComment(articleId, value).subscribe(new RxSubscriber<CommentCreateModel>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(CommentCreateModel result) {
                mView.returnCreateComment(result);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getCommentList(int articleId, int p) {
        mRxManage.add(mModel.getListData(articleId, p).subscribe(new RxSubscriber<CommentListModel>(mContext, false) {
            @Override
            protected void _onNext(CommentListModel result) {
                mView.returnCommentList(result);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
