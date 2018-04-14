package com.jung.android.ui.news.contract;

import com.jung.android.bean.ArticleModel;
import com.jung.android.bean.BannerModel;
import com.jung.android.bean.LinkModel;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * des:新闻列表contract
 * Created by xsf
 * on 2016.09.14:38
 */
public interface NewsListContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Observable<ArticleModel> getNewsListData(boolean isTop, final String id, int startPage);

        Observable<List<BannerModel.Banner>> getBannerList();

        Observable<LinkModel> getAdList(String site, String alias);
    }

    interface View extends BaseView {
        //返回获取的新闻
        void returnNewsListData(ArticleModel newsSummaries);

        void returnBannerList(List<BannerModel.Banner> list);

        void returnAdLink(LinkModel linkModel);

        //返回顶部
        void scrolltoTop();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取新闻请求
        public abstract void getNewsListDataRequest(final String id, int startPage);

        public abstract void getBannerList();

        public abstract void getAdList(String site, String alias);
    }
}
