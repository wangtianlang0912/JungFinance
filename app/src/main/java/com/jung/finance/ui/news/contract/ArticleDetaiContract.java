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

    }

    public interface View extends BaseView {
        void returnArticleData(ArticleDetail data);

        void returnRelateList(ArticleModel articleModel);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getArticleDetail(String id);

        //相关新闻
        public abstract void getArticleRelateList(String id);

        //关注
        public abstract void focusChanged(int bloggerId, int status);
    }
}
