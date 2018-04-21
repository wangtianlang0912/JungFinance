package cn.jungmedia.android.ui.news.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.bean.ArticleDetail;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.bean.VoteModel;
import cn.jungmedia.android.ui.news.contract.ArticleDetaiContract;
import cn.jungmedia.android.utils.MyUtils;
import cn.jungmedia.android.utils.PatternUtil;
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
    public Observable<BaseRespose<FavActionModel>> favActionArticle(int objectId, final boolean status) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = null;
        if (status) {
            observable = Api.getDefault(HostType.Jung_FINANCE).unfavArticle(token, objectId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).favArticle(token, objectId);
        }
        //声明线程调度
        return observable.compose(RxSchedulers.<BaseRespose<FavActionModel>>io_main());
    }

    @Override
    public Observable<BaseRespose<FavActionModel>> focusAction(int bloggerId, final boolean status) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = null;
        if (status) {
            observable = Api.getDefault(HostType.Jung_FINANCE).unFocusMedia(token, bloggerId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).focusMedia(token, bloggerId);
        }
        return observable
                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<FavActionModel>>io_main());
    }

    @Override
    public Observable<BaseRespose<FavActionModel>> getArticleFavState(int articleId) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getArticleFavState(token, articleId)
                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<FavActionModel>>io_main());
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

    @Override
    public Observable<BaseRespose<VoteModel>> support(int articleId) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).support(token, articleId)

                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<VoteModel>>io_main());
    }

    @Override
    public Observable<BaseRespose<VoteModel>> oppose(int articleId) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).oppose(token, articleId)

                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<VoteModel>>io_main());
    }
}
