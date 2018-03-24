package com.jung.finance.ui.news.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.HostType;
import com.jung.finance.app.AppApplication;
import com.jung.finance.app.AppConstant;
import com.jung.finance.bean.ColumnModel;
import com.jung.finance.bean.NewsChannelTable;
import com.jung.finance.db.NewsChannelTableManager;
import com.jung.finance.ui.news.contract.NewsChannelContract;
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
    public Observable<List<NewsChannelTable>> lodeMoreNewsChannelsByCache() {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTableList =
                        (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MORE);
                if (newsChannelTableList == null) {


                }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }

    @Override
    public Observable<List<NewsChannelTable>> lodeMoreNewsChannelsByNet() {

        return Api.getDefault(HostType.Jung_FINANCE)
                .getColumnList(null, null)
                .map(new Func1<BaseRespose<ColumnModel>, List<NewsChannelTable>>() {
                    @Override
                    public List<NewsChannelTable> call(BaseRespose<ColumnModel> respose) {
                        List<NewsChannelTable> tableList = new ArrayList<NewsChannelTable>();
                        if (respose != null) {
                            for (int i = 0; i < respose.data.getColumns().size(); i++) {
                                ColumnModel.Column column = respose.data.getColumns().get(i);
                                NewsChannelTable entity = new NewsChannelTable(column.getTitle(), String.valueOf(column.getObjectId())
                                        , false, i, false);
                                tableList.add(entity);
                            }
                        }
                        return tableList;
                    }
                }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());


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
    public Observable<String> updateDb(final ArrayList<NewsChannelTable> mineChannelTableList, final ArrayList<NewsChannelTable> moreChannelTableList) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE, mineChannelTableList);
                ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MORE, moreChannelTableList);
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<String>io_main());
    }
}
