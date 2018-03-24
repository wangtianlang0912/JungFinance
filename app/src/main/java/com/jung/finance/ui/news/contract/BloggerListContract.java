package com.jung.finance.ui.news.contract;


import com.jung.finance.bean.BloggerModel;
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
public class BloggerListContract {

    public interface Model extends BaseModel {
        //请求获取新闻
        Observable<BloggerModel> getListData(String uid, int startPage);
    }

    public interface View extends BaseView {
        void returnListData(BloggerModel data);
    }

    public abstract static class Presenter extends BasePresenter<BloggerListContract.View, BloggerListContract.Model> {
        public abstract void getBloggerListDataRequest(final String uid, int startPage);
    }
}
