package cn.jungmedia.android.ui.news.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.bean.ArticleDetail;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.bean.ArticleRelevant;
import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.bean.VoteModel;
import cn.jungmedia.android.ui.news.contract.ArticleDetaiContract;
import cn.jungmedia.android.ui.user.bean.UserInfo;
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
        int uid = 0;
        if (MyUtils.isLogin()) {
            UserInfo userInfo = MyUtils.getUserInfoFromPreference(AppApplication.getAppContext());
            if (userInfo != null && userInfo.getUser() != null) {
                uid = userInfo.getUser().getUid();
            }
        }
        return Api.getDefault(HostType.Jung_FINANCE).getArticleDetail(id, uid)
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
                            String ptime = TimeUtil.formatData(TimeUtil.dateFormatYMDHM, article.getVtime());
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
    public Observable<ArticleRelevant> getArticleReleateList(String id) {
        return Api.getDefault(HostType.Jung_FINANCE).getRelateList(id, 3)
                .map(new Func1<BaseRespose<ArticleRelevant>, ArticleRelevant>() {
                    @Override
                    public ArticleRelevant call(BaseRespose<ArticleRelevant> baseRespose) {
                        ArticleRelevant articleModel = baseRespose.data;
                        if (articleModel != null && articleModel.getArticles() != null) {
                            for (ArticleRelevant.Articles article : articleModel.getArticles()) {
                                ArticleRelevant.Detail detail = article.getDetail();
                                String coverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + detail.getImage();
                                detail.setImage(coverImage);

                                String ptime = TimeUtil.formatTimeStampStr2Desc(detail.getVtime() * 1000);
                                detail.setPtime(ptime);
                            }
                        }
                        return articleModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ArticleRelevant>io_main());
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
    public Observable<BaseRespose<CommentCreateModel>> createComment(int articleId, String body, int touid) {
        String token = MyUtils.getToken();
        Observable observable = Api.getDefault(HostType.Jung_FINANCE).createComment(token, articleId, body, touid == 0 ? null : String.valueOf(touid));
        return observable.map(new Func1<BaseRespose<CommentCreateModel>, BaseRespose<CommentCreateModel>>() {
            @Override
            public BaseRespose<CommentCreateModel> call(BaseRespose<CommentCreateModel> baseRespose) {
                return baseRespose;
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<CommentCreateModel>>io_main());
    }

    @Override
    public Observable<CommentListModel> getCommentList(int articleId) {
        return Api.getDefault(HostType.Jung_FINANCE).getCommentList(articleId, 0, 3, String.valueOf(0))
                .map(new Func1<BaseRespose<CommentListModel>, CommentListModel>() {
                    @Override
                    public CommentListModel call(BaseRespose<CommentListModel> baseRespose) {
                        CommentListModel listModel = baseRespose.data;
                        if (listModel != null && listModel.getComments() != null) {

                            for (CommentCreateModel.Comment comment : listModel.getComments()) {
                                String ptime = TimeUtil.formatData(TimeUtil.dateFormatYMDHMS, comment.getCtime());
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

    @Override
    public Observable<BaseRespose> share(int articleId) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).share(token, articleId)

                //声明线程调度
                .compose(RxSchedulers.<BaseRespose>io_main());
    }
}
