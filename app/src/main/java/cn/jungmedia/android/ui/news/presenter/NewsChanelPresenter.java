package com.jung.android.ui.news.presenter;

import com.jung.android.app.AppConstant;
import com.jung.android.bean.NewsChannelTable;
import com.jung.android.bean.NewsChannelTableGroup;
import com.jung.android.ui.news.contract.NewsChannelContract;
import com.leon.common.baserx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * des:新闻频道
 * Created by xsf
 * on 2016.09.17:43
 */
public class NewsChanelPresenter extends NewsChannelContract.Presenter {
    @Override
    public void lodeChannelsRequest() {
        mRxManage.add(mModel.lodeMineNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext, false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
        mRxManage.add(mModel.lodeMoreNewsChannelsByCache().subscribe(new RxSubscriber<NewsChannelTableGroup>(mContext, false) {
            @Override
            protected void _onNext(NewsChannelTableGroup tableGroup) {
                if (tableGroup != null) {
                    mView.returnMoreNewsChannels(tableGroup);
                } else {
                    mRxManage.add(mModel.lodeMoreNewsChannelsByNet().subscribe(new RxSubscriber<NewsChannelTableGroup>(mContext, false) {
                        @Override
                        protected void _onNext(NewsChannelTableGroup tableGroups) {
                            if (tableGroups != null) {
                                mView.returnMoreNewsChannels(tableGroups);
                            }
                        }

                        @Override
                        protected void _onError(String message) {

                        }
                    }));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void onItemSwap(final ArrayList<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition) {
        mRxManage.add(mModel.swapDb(newsChannelTableList, fromPosition, toPosition).subscribe(new RxSubscriber<String>(mContext, false) {
            @Override
            protected void _onNext(String s) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED, newsChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
        ;
    }

    @Override
    public void onItemAddOrRemove(final ArrayList<NewsChannelTable> mineChannelTableList, NewsChannelTableGroup tableGroup) {
        mRxManage.add(mModel.updateDb(mineChannelTableList, tableGroup).subscribe(new RxSubscriber<String>(mContext, false) {
            @Override
            protected void _onNext(String s) {
                mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED, mineChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
