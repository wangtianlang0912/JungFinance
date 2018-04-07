package com.jung.finance.ui.news.contract;


import com.jung.finance.bean.ArticleDetail;
import com.jung.finance.bean.ArticleModel;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

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

        Observable<ArticleModel> getArticleReleateList(String id);

        Observable<Boolean>  favActionArticle(int articleId, boolean status);

        Observable<Boolean> focusAction(int bloggerId,boolean status);

        Observable<Boolean> getArticleFavState(int articleId);
    }

    public interface View extends BaseView {
        void returnArticleData(ArticleDetail data);

        void returnRelateList(ArticleModel articleModel);

        void returnFavArticleState(boolean result);

        void returnFocusBloggerState(boolean result);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getArticleDetail(String id);

        //相关新闻
        public abstract void getArticleRelateList(String id);

        //收藏文章
        public abstract void favActionArticle(int articleId, boolean status);

        // 关注
        public abstract void focusAction(int bloggerId,boolean status);

        public abstract void getArticleFavState(int articleId);
    }
}
