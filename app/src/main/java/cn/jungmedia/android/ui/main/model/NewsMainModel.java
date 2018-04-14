package cn.jungmedia.android.ui.main.model;

import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.bean.NewsChannelTable;
import cn.jungmedia.android.db.NewsChannelTableManager;
import cn.jungmedia.android.ui.main.contract.NewsMainContract;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.ACache;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * des:
 * Created by xsf
 * on 2016.09.17:05
 */
public class NewsMainModel implements NewsMainContract.Model {
    @Override
    public Observable<List<NewsChannelTable>> lodeMineNewsChannels() {

        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTableList = (ArrayList<NewsChannelTable>) ACache.get(AppApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MINE);
               if(newsChannelTableList==null){
                   newsChannelTableList= (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsStatic();
                   ACache.get(AppApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,newsChannelTableList);
               }
                subscriber.onNext(newsChannelTableList);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<NewsChannelTable>>io_main());
    }
}
