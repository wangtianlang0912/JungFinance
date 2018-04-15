package cn.jungmedia.android.ui.news.presenter;

import cn.jungmedia.android.bean.ArticleDetail;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.ui.news.contract.ArticleDetaiContract;
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
 * @date 2018/4/6. 下午11:00
 *
 *
 */
public class ArticleDetailPresenter extends ArticleDetaiContract.Presenter {
    @Override
    public void getArticleDetail(String id) {
        mRxManage.add(mModel.getArticleDetail(id).subscribe(new RxSubscriber<ArticleDetail>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(ArticleDetail detailModel) {
                mView.stopLoading();
                mView.returnArticleData(detailModel);
            }

            @Override
            protected void _onError(String message) {

                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getArticleRelateList(String id) {
        mRxManage.add(mModel.getArticleReleateList(id).subscribe(new RxSubscriber<ArticleModel>(mContext, false) {
            @Override
            protected void _onNext(ArticleModel articleModel) {
                mView.returnRelateList(articleModel);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void favActionArticle(int objectId, boolean status) {

        mRxManage.add(mModel.favActionArticle(objectId, status).subscribe(new RxSubscriber<FavActionModel.Favorite>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(FavActionModel.Favorite result) {
                mView.stopLoading();
                mView.returnFavArticleState(result);
            }

            @Override
            protected void _onError(String message) {

                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void focusAction(int bloggerId, boolean status) {
        mRxManage.add(mModel.focusAction(bloggerId, status).subscribe(new RxSubscriber<Boolean>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(Boolean result) {
                mView.stopLoading();
                mView.returnFocusBloggerState(result);
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getArticleFavState(int articleId) {
        mRxManage.add(mModel.getArticleFavState(articleId).subscribe(new RxSubscriber<FavActionModel.Favorite>(mContext, false) {
            @Override
            protected void _onNext(FavActionModel.Favorite result) {
                mView.returnFavArticleState(result);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

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
    public void getCommentList(int articleId) {
        mRxManage.add(mModel.getCommentList(articleId).subscribe(new RxSubscriber<CommentListModel>(mContext, false) {
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