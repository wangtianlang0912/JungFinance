package com.jung.finance.ui.news.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.ApiConstants;
import com.jung.finance.api.HostType;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.ui.news.contract.NewsListContract;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * des:新闻列表model
 * Created by xsf
 * on 2016.09.14:54
 */
public class NewsListModel implements NewsListContract.Model {
    /**
     * 获取新闻列表
     *
     * @param type
     * @param id
     * @param startPage
     * @return
     */
    @Override
    public Observable<List<ArticleModel.Article>> getNewsListData(final String type, final String id, final int startPage) {
        return Api.getDefault(HostType.Jung_FINANCE).getTopArtileList(Api.getCacheControl(), startPage)
                .flatMap(new Func1<Map<String, BaseRespose<ArticleModel>>, Observable<ArticleModel.Article>>() {
                    @Override
                    public Observable<ArticleModel.Article> call(Map<String, BaseRespose<ArticleModel>> map) {
                        return Observable.from(map.get(id).data.getArticles());
                    }
                })
                //转化图片
                .map(new Func1<ArticleModel.Article, ArticleModel.Article>() {
                    @Override
                    public ArticleModel.Article call(ArticleModel.Article newsSummary) {
                        String image = ApiConstants.getHost(HostType.Jung_FINANCE) + newsSummary.getImage();
                        newsSummary.setImage(image);
                        return newsSummary;
                    }
                })
                //转化时间
                .map(new Func1<ArticleModel.Article, ArticleModel.Article>() {
                    @Override
                    public ArticleModel.Article call(ArticleModel.Article newsSummary) {
                        String ptime = TimeUtil.formatTimeStampStr2Desc(newsSummary.getVtime()*1000);
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()//去重
                .toSortedList(new Func2<ArticleModel.Article, ArticleModel.Article, Integer>() {
                    @Override
                    public Integer call(ArticleModel.Article newsSummary, ArticleModel.Article newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<List<ArticleModel.Article>>io_main());
    }


    @Override
    public Observable<List<ArticleModel.Article>> getNewsListData2(final String type, final String id, final int startPage) {
        return Api.getDefault(HostType.Jung_FINANCE).getArtileList(id, startPage)
                .flatMap(new Func1<BaseRespose<ArticleModel>, Observable<ArticleModel.Article>>() {
                    @Override
                    public Observable<ArticleModel.Article> call(BaseRespose<ArticleModel> map) {
                        return Observable.from(map.data.getArticles());
                    }
                })
                //转化图片
                .map(new Func1<ArticleModel.Article, ArticleModel.Article>() {
                    @Override
                    public ArticleModel.Article call(ArticleModel.Article newsSummary) {
                        String image = ApiConstants.getHost(HostType.Jung_FINANCE) + newsSummary.getImage();
                        newsSummary.setImage(image);
                        return newsSummary;
                    }
                })
                //转化时间
                .map(new Func1<ArticleModel.Article, ArticleModel.Article>() {
                    @Override
                    public ArticleModel.Article call(ArticleModel.Article newsSummary) {
                        String ptime = TimeUtil.formatTimeStampStr2Desc(newsSummary.getVtime()*1000);
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
                .distinct()//去重
                .toSortedList(new Func2<ArticleModel.Article, ArticleModel.Article, Integer>() {
                    @Override
                    public Integer call(ArticleModel.Article newsSummary, ArticleModel.Article newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<List<ArticleModel.Article>>io_main());
    }
}
