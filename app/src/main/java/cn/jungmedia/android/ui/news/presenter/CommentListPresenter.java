package cn.jungmedia.android.ui.news.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.ui.news.contract.CommentListContract;


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
    public void createComment(int articleId, String value, int touid) {
        mRxManage.add(mModel.createComment(articleId, value, touid).subscribe(new RxSubscriber<CommentCreateModel>(mContext, false) {

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
    public void getCommentList(int articleId, int p, int touid) {
        mRxManage.add(mModel.getListData(articleId, p, touid).subscribe(new RxSubscriber<CommentListModel>(mContext, false) {
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
