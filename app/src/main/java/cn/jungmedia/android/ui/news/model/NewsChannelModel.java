package com.jung.android.ui.news.model;

import com.jung.android.api.HostType;
import com.jung.android.app.AppConstant;
import com.jung.android.bean.NewsChannelTable;
import com.jung.android.db.NewsChannelTableManager;
import com.jung.android.api.Api;
import com.jung.android.app.AppApplication;
import com.jung.android.bean.ColumnModel;
import com.jung.android.bean.NewsChannelTableGroup;
import com.jung.android.ui.news.contract.NewsChannelContract;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.ACache;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * des:新闻频道
 * Created by xsf
 * on 2016.09.17:05
 */
public class NewsChannelModel implements NewsChannelContract.Model {
    @Override
    public Observable<List<NewsChannelTable>> lodeMineNewsChannels() {

        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTableList = (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MINE);
                if (newsChannelTableList == null) {
                    newsChannelTableList = (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsStatic();
                }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }

    @Override
    public Observable<NewsChannelTableGroup> lodeMoreNewsChannelsByCache() {
        return Observable.create(new Observable.OnSubscribe<NewsChannelTableGroup>() {
            @Override
            public void call(Subscriber<? super NewsChannelTableGroup> subscriber) {
                NewsChannelTableGroup group =
                        (NewsChannelTableGroup) ACache.get(AppApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MORE);
                if (group == null) {


                }
                subscriber.onNext(group);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<NewsChannelTableGroup>io_main());
    }

    @Override
    public Observable<NewsChannelTableGroup> lodeMoreNewsChannelsByNet() {

        return Api.getDefault(HostType.Jung_FINANCE)
                .getColumnCategoryList()
                .map(new Func1<BaseRespose<ColumnModel>, NewsChannelTableGroup>() {
                    @Override
                    public NewsChannelTableGroup call(BaseRespose<ColumnModel> respose) {
                        NewsChannelTableGroup group = new NewsChannelTableGroup();
                        if (respose != null) {
                            List<NewsChannelTable> collegeTableList = new ArrayList<NewsChannelTable>();
                            for (int i = 0; i < respose.data.getCollege().size(); i++) {
                                ColumnModel.Column column = respose.data.getCollege().get(i);
                                NewsChannelTable entity = new NewsChannelTable(column.getTitle(), String.valueOf(column.getObjectId()), column.getType()
                                        , false, i, false);
                                collegeTableList.add(entity);
                            }
                            group.setColleges(collegeTableList);
                            List<NewsChannelTable> marketTableList = new ArrayList<NewsChannelTable>();
                            for (int i = 0; i < respose.data.getMarket().size(); i++) {
                                ColumnModel.Column column = respose.data.getMarket().get(i);
                                NewsChannelTable entity = new NewsChannelTable(column.getTitle(), String.valueOf(column.getObjectId()), column.getType()
                                        , false, i, false);
                                marketTableList.add(entity);
                            }
                            group.setMarkets(marketTableList);
                            List<NewsChannelTable> newsTableList = new ArrayList<NewsChannelTable>();
                            for (int i = 0; i < respose.data.getNews().size(); i++) {
                                ColumnModel.Column column = respose.data.getNews().get(i);
                                NewsChannelTable entity = new NewsChannelTable(column.getTitle(), String.valueOf(column.getObjectId()), column.getType()
                                        , false, i, false);
                                newsTableList.add(entity);
                            }
                            group.setNews(newsTableList);
                        }
                        return group;
                    }
                }).compose(RxSchedulers.<NewsChannelTableGroup>io_main());


    }

    @Override
    public Observable<String> swapDb(final ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE, newsChannelTableList);
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<String>io_main());

    }

    @Override
    public Observable<String> updateDb(final ArrayList<NewsChannelTable> mineChannelTableList, final NewsChannelTableGroup tableGroup) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE, mineChannelTableList);
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MORE, tableGroup);
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<String>io_main());
    }
}
