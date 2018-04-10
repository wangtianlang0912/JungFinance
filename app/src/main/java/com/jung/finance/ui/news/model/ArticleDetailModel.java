package com.jung.finance.ui.news.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.ApiConstants;
import com.jung.finance.api.HostType;
import com.jung.finance.bean.ArticleDetail;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.bean.BloggerModel;
import com.jung.finance.bean.CommentCreateModel;
import com.jung.finance.bean.CommentListModel;
import com.jung.finance.bean.FavActionModel;
import com.jung.finance.ui.news.contract.ArticleDetaiContract;
import com.jung.finance.utils.MyUtils;
import com.jung.finance.utils.PatternUtil;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

import rx.Observable;
import rx.functions.Func1;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/6. 下午11:04
 *
 *
 */
public class ArticleDetailModel implements ArticleDetaiContract.Model {
    @Override
    public Observable<ArticleDetail> getArticleDetail(String id) {
        return Api.getDefault(HostType.Jung_FINANCE).getArticleDetail(id)
                .map(new Func1<BaseRespose<ArticleDetail>, ArticleDetail>() {
                    @Override
                    public ArticleDetail call(BaseRespose<ArticleDetail> respose) {

                        ArticleDetail articleDetail = respose.data;
                        if (articleDetail != null && articleDetail.getArticle() != null) {

                            ArticleModel.Article article = articleDetail.getArticle();
                            article.setImage(ApiConstants.URL + article.getImage());
                            if (article.getContent() != null) {
                                String content = PatternUtil.repairContent(article.getContent(), ApiConstants.URL);
                                article.setContent(content);
                            }
                            String ptime = TimeUtil.formatTimeStampStr2Desc(article.getVtime() * 1000);
                            article.setPtime(ptime);
                            BloggerModel.Media media = articleDetail.getArticle().getMedia();
                            if (media != null) {
                                media.setCoverImage(ApiConstants.URL + media.getCoverImage());
                            }
                        }
                        return articleDetail;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ArticleDetail>io_main());
    }

    @Override
    public Observable<ArticleModel> getArticleReleateList(String id) {
        return Api.getDefault(HostType.Jung_FINANCE).getRelateList(id)
                .map(new Func1<BaseRespose<ArticleModel>, ArticleModel>() {
                    @Override
                    public ArticleModel call(BaseRespose<ArticleModel> baseRespose) {
                        ArticleModel articleModel = baseRespose.data;
                        for (ArticleModel.Article article : articleModel.getArticles()) {
                            String coverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + article.getImage();
                            article.setImage(coverImage);

                            String ptime = TimeUtil.formatTimeStampStr2Desc(article.getVtime() * 1000);
                            article.setPtime(ptime);
                        }
                        return articleModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ArticleModel>io_main());
    }

    @Override
    public Observable<FavActionModel.Favorite> favActionArticle(int objectId, final boolean status) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = null;
        if (status) {
            observable = Api.getDefault(HostType.Jung_FINANCE).unfavArticle(token, objectId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).favArticle(token, objectId);
        }
        return observable.map(new Func1<BaseRespose<FavActionModel>, FavActionModel.Favorite>() {
            @Override
            public FavActionModel.Favorite call(BaseRespose<FavActionModel> baseRespose) {
                return baseRespose.data.getFavorite();
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<FavActionModel.Favorite>io_main());
    }

    @Override
    public Observable<Boolean> focusAction(int bloggerId, final boolean status) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = null;
        if (status) {
            observable = Api.getDefault(HostType.Jung_FINANCE).unFocusMedia(token, bloggerId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).focusMedia(token, bloggerId);
        }
        return observable.map(new Func1<BaseRespose<FavActionModel>, Boolean>() {
            @Override
            public Boolean call(BaseRespose<FavActionModel> baseRespose) {
                if (status) {
                    if (baseRespose.success()) {
                        return false; // 返回的是当前收藏的状态
                    }
                    return status;
                } else {
                    FavActionModel activityModel = baseRespose.data;
                    return activityModel != null && activityModel.getFavorite() != null; // 收藏成功返回fav对象
                }
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<Boolean>io_main());
    }

    @Override
    public Observable<FavActionModel.Favorite> getArticleFavState(int articleId) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getArticleFavState(token, articleId)
                .map(new Func1<BaseRespose<FavActionModel>, FavActionModel.Favorite>() {
                    @Override
                    public FavActionModel.Favorite call(BaseRespose<FavActionModel> baseRespose) {
                        FavActionModel activityModel = baseRespose.data;
                        return activityModel.getFavorite();
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<FavActionModel.Favorite>io_main());
    }

    @Override
    public Observable<CommentCreateModel> createComment(int articleId, String body) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).createComment(token, articleId, body, null)
                .map(new Func1<BaseRespose<CommentCreateModel>, CommentCreateModel>() {
                    @Override
                    public CommentCreateModel call(BaseRespose<CommentCreateModel> baseRespose) {
                        return baseRespose.data;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<CommentCreateModel>io_main());
    }

    @Override
    public Observable<CommentListModel> getCommentList(int articleId) {
        return Api.getDefault(HostType.Jung_FINANCE).getCommentList(articleId, 0, 0, 3)
                .map(new Func1<BaseRespose<CommentListModel>, CommentListModel>() {
                    @Override
                    public CommentListModel call(BaseRespose<CommentListModel> baseRespose) {
                        CommentListModel listModel = baseRespose.data;
                        if (listModel != null && listModel.getComments() != null) {

                            for (CommentCreateModel.Comment comment : listModel.getComments()) {
                                String ptime = TimeUtil.formatTimeStampStr2Desc(comment.getCtime() * 1000);
                                comment.setcTimeStr(ptime);
                                String logo = comment.getUser().getLogo();
                                comment.getUser().setLogo(ApiConstants.URL + logo);
                            }
                        }
                        return listModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<CommentListModel>io_main());
    }
}
