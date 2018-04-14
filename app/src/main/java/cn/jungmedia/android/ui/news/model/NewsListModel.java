package com.jung.android.ui.news.model;

import com.jung.android.api.Api;
import com.jung.android.api.ApiConstants;
import com.jung.android.api.HostType;
import com.jung.android.bean.ArticleModel;
import com.jung.android.bean.BannerModel;
import com.jung.android.bean.LinkModel;
import com.jung.android.ui.news.contract.NewsListContract;
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
    public Observable<List<BannerModel.Banner>> getBannerList() {

        return Api.getDefault(HostType.Jung_FINANCE).getBannerList()
                .map(new Func1<BaseRespose<BannerModel>, List<BannerModel.Banner>>() {
                    @Override
                    public List<BannerModel.Banner> call(BaseRespose<BannerModel> respose) {

                        for (BannerModel.Banner banner : respose.data.getBanners()) {
                            String coverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + banner.getImage();
                            banner.setImage(coverImage);
                        }
                        return respose.data.getBanners();
                    }
                }).compose(RxSchedulers.<List<BannerModel.Banner>>io_main());
    }

    @Override
    public Observable<LinkModel> getAdList(String site, String alias) {
        return Api.getDefault(HostType.Jung_FINANCE).getAdList(site, alias).map(new Func1<BaseRespose<LinkModel>, LinkModel>() {
            @Override
            public LinkModel call(BaseRespose<LinkModel> linkModelBaseRespose) {
                LinkModel linkModel = linkModelBaseRespose.data;
                LinkModel.Link link = linkModel.getLink();
                if (link != null) {
                    link.setWapImage(ApiConstants.getHost(HostType.Jung_FINANCE) + link.getWapImage());
                }
                return linkModel;
            }
        }).compose(RxSchedulers.<LinkModel>io_main());
    }


}
