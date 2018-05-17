package cn.jungmedia.android.ui.news.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.bean.ArticleDetail;
import cn.jungmedia.android.bean.ArticleRelevant;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.bean.VoteModel;
import cn.jungmedia.android.ui.news.contract.ArticleDetaiContract;


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
        mRxManage.add(mModel.getArticleReleateList(id).subscribe(new RxSubscriber<ArticleRelevant>(mContext, false) {
            @Override
            protected void _onNext(ArticleRelevant articleModel) {
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

        mRxManage.add(mModel.favActionArticle(objectId, status).subscribe(new RxSubscriber<BaseRespose<FavActionModel>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(BaseRespose<FavActionModel> result) {
                mView.stopLoading();
                mView.returnFavArticleState(result,true);
            }

            @Override
            protected void _onError(String message) {

                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void focusAction(int bloggerId, final boolean status) {
        mRxManage.add(mModel.focusAction(bloggerId, status).subscribe(new RxSubscriber<BaseRespose<FavActionModel>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(BaseRespose<FavActionModel> respose) {
                mView.stopLoading();
                mView.returnFocusBloggerState(respose,!status);
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
        mRxManage.add(mModel.getArticleFavState(articleId).subscribe(new RxSubscriber<BaseRespose<FavActionModel>>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose<FavActionModel> result) {
                mView.returnFavArticleState(result,false);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

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

    @Override
    public void support(int articleId) {
        mRxManage.add(mModel.support(articleId).subscribe(new RxSubscriber<BaseRespose<VoteModel>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(BaseRespose<VoteModel> result) {
                mView.returnVoteData(result);
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
    public void oppose(int articleId) {
        mRxManage.add(mModel.oppose(articleId).subscribe(new RxSubscriber<BaseRespose<VoteModel>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(BaseRespose<VoteModel> result) {
                mView.returnVoteData(result);
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
    public void share(int objectId) {
        mRxManage.add(mModel.share(objectId).subscribe(new RxSubscriber<BaseRespose>(mContext, false) {

            @Override
            protected void _onNext(BaseRespose result) {
                mView.returnShare(result);
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
            }
        }));
    }


}
