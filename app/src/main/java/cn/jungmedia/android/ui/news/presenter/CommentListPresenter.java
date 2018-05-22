package cn.jungmedia.android.ui.news.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.ui.news.contract.CommentListContract;
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
 * @date 2018/4/7. 下午11:01
 *
 *
 */
public class CommentListPresenter extends CommentListContract.Presenter {
    @Override
    public void createComment(int articleId, String value, int touid) {
        mRxManage.add(mModel.createComment(articleId, value, touid).subscribe(new RxSubscriber<BaseRespose<CommentCreateModel>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(BaseRespose<CommentCreateModel> result) {
                if (!MyUtils.verifyToken(result)) {
                    AppApplication.getInvalidCallback().onTokenInvalid();
                    return;
                } else {
                    mView.returnCreateComment(result.data);
                }
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
        mRxManage.add(mModel.getListData(articleId, p, touid).subscribe(new RxSubscriber<BaseRespose<CommentListModel>>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose<CommentListModel> result) {
                mView.returnCommentList(result.data);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
