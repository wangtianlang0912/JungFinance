package com.jung.android.ui.blogger.contract;


import com.jung.android.bean.ArticleModel;
import com.jung.android.ui.blogger.bean.BloggerBean;
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
 * @date 2018/3/24. 下午4:54
 *
 *
 */
public class BloggerContract {

    public interface Model extends BaseModel {
        //请求获取新闻
        Observable<ArticleModel> getBloggerArticleList(int uid, int startPage);

        Observable<BloggerBean> getBloggerInfo(int uid);

    }

    public interface View extends BaseView {

        void returnBloggerInfo(BloggerBean blogger);

        void returnListData(ArticleModel data);
    }

    public abstract static class Presenter extends BasePresenter<BloggerContract.View, BloggerContract.Model> {
        public abstract void getBloggerArticleList(final int uid, int startPage);

        public abstract void getBloggerInfo(int uid);
    }
}
