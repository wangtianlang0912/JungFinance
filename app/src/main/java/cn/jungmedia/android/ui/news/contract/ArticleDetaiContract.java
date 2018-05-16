package cn.jungmedia.android.ui.news.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import cn.jungmedia.android.bean.ArticleDetail;
import cn.jungmedia.android.bean.ArticleRelevant;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.bean.VoteModel;
import rx.Observable;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/6. 下午10:59
 *
 *
 */
public class ArticleDetaiContract {


    public interface Model extends BaseModel {
        //请求获取新闻
        Observable<ArticleDetail> getArticleDetail(String id);

        Observable<ArticleRelevant> getArticleReleateList(String id);

        Observable<BaseRespose<FavActionModel>> favActionArticle(int articleId, boolean status);

        Observable<BaseRespose<FavActionModel>> focusAction(int bloggerId, boolean status);

        Observable<BaseRespose<FavActionModel>> getArticleFavState(int articleId);

        Observable<CommentCreateModel> createComment(int articleId, String body, int touid);

        Observable<CommentListModel> getCommentList(int articleId);

        Observable<BaseRespose<VoteModel>> support(int articleId);

        Observable<BaseRespose<VoteModel>> oppose(int articleId);

        Observable<BaseRespose> share(int articleId);

    }

    public interface View extends BaseView {
        void returnArticleData(ArticleDetail data);

        void returnRelateList(ArticleRelevant articleModel);

        void returnFavArticleState(BaseRespose<FavActionModel> result);

        void returnFocusBloggerState(BaseRespose<FavActionModel> result, boolean b);

        void returnCreateComment(CommentCreateModel model);

        void returnCommentList(CommentListModel model);

        void returnVoteData(BaseRespose<VoteModel> response);

        void returnShare(BaseRespose response);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void getArticleDetail(String id);

        //相关新闻
        public abstract void getArticleRelateList(String id);

        //收藏文章
        public abstract void favActionArticle(int articleId, boolean status);

        // 关注
        public abstract void focusAction(int bloggerId, boolean status);

        public abstract void getArticleFavState(int articleId);

        // 创建评论
        public abstract void createComment(int articleId, String value, int touid);

        //获取3条评论展示在文章详情中
        public abstract void getCommentList(int articleId);

        public abstract void support(int articleId);

        public abstract void oppose(int articleId);

        public abstract void share(int objectId);
    }
}
