package com.jung.finance.ui.news.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.ApiConstants;
import com.jung.finance.api.HostType;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.bean.BannerModel;
import com.jung.finance.bean.LinkModel;
import com.jung.finance.ui.news.contract.NewsListContract;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 */
public class NewsListModel implements NewsListContract.Model {
    /**
     * 获取新闻列表
     *
     * @param isTop
     * @param id
     * @param startPage
     * @return
     */
    @Override
    public Observable<ArticleModel> getNewsListData(boolean isTop, final String id, final int startPage) {
        return Api.getDefault(HostType.Jung_FINANCE).getArtileList(Api.getCacheControl(), isTop ? 1 : 0, id, startPage)
                .map(new Func1<BaseRespose<ArticleModel>, ArticleModel>() {
                    @Override
                    public ArticleModel call(BaseRespose<ArticleModel> baseRespose) {
                        ArticleModel articleModel = baseRespose.data;
                        for (ArticleModel.Article article : articleModel.getArticles()) {
                            String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + article.getImage();
                            article.setImage(cooverImage);

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
    public Observable<List<BannerModel.Banner>> getBannerList() {

        return Api.getDefault(HostType.Jung_FINANCE).getBannerList()
                .map(new Func1<BaseRespose<BannerModel>, List<BannerModel.Banner>>() {
                    @Override
                    public List<BannerModel.Banner> call(BaseRespose<BannerModel> respose) {
                        return respose.data.getBanners();
                    }
                }).compose(RxSchedulers.<List<BannerModel.Banner>>io_main());
    }

    @Override
    public Observable<LinkModel> getAdList(String site, String alias) {
        return Api.getDefault(HostType.Jung_FINANCE).getAdList(site, alias).map(new Func1<BaseRespose<LinkModel>, LinkModel>() {
            @Override
            public LinkModel call(BaseRespose<LinkModel> linkModelBaseRespose) {
                return linkModelBaseRespose.data;
            }
        }).compose(RxSchedulers.<LinkModel>io_main());
    }


}
