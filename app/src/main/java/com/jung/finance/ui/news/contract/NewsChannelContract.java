package com.jung.finance.ui.news.contract;

import com.jung.finance.bean.NewsChannelTable;
import com.jung.finance.bean.NewsChannelTableGroup;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * des:
 * Created by xsf
 * on 2016.09.11:53
 */
public interface NewsChannelContract {

    interface Model extends BaseModel {
        Observable<List<NewsChannelTable>> lodeMineNewsChannels();

        Observable<NewsChannelTableGroup> lodeMoreNewsChannelsByCache();

        Observable<NewsChannelTableGroup> lodeMoreNewsChannelsByNet();

        Observable<String> swapDb(ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition);

        Observable<String> updateDb(ArrayList<NewsChannelTable> mineChannelTableList, NewsChannelTableGroup tableGroup);
    }

    interface View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine);

        void returnMoreNewsChannels(NewsChannelTableGroup group);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void lodeChannelsRequest();

        public abstract void onItemSwap(ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, final int toPosition);

        public abstract void onItemAddOrRemove(ArrayList<NewsChannelTable> mineChannelTableList, NewsChannelTableGroup tableGroup);
    }
}
