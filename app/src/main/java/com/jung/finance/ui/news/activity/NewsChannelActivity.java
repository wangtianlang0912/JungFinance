package com.jung.finance.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.bean.NewsChannelTable;
import com.jung.finance.bean.NewsChannelTableGroup;
import com.jung.finance.ui.news.adapter.ChannelAdapter;
import com.jung.finance.ui.news.contract.NewsChannelContract;
import com.jung.finance.ui.news.event.ChannelItemMoveEvent;
import com.jung.finance.ui.news.model.NewsChannelModel;
import com.jung.finance.ui.news.presenter.NewsChanelPresenter;
import com.jung.finance.widget.ItemDragHelperCallback;
import com.leon.common.base.BaseActivity;
import com.leon.common.commonwidget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * des:选择关注频道
 * Created by xsf
 * on 2016.09.11:51
 */
public class NewsChannelActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel> implements NewsChannelContract.View {

    @Bind(R.id.ntb)
    NormalTitleBar titleBar;
    @Bind(R.id.news_channel_mine_rv)
    RecyclerView newsChannelMineRv;
    @Bind(R.id.news_channel_news_rv)
    RecyclerView newsChannelNewsRv;
    @Bind(R.id.news_channel_market_rv)
    RecyclerView newsChannelMarketRv;
    @Bind(R.id.news_channel_college_rv)
    RecyclerView newsChannelCollegeRv;

    private ChannelAdapter channelAdapterMine;
    private ChannelAdapter channelAdapterNews;
    private ChannelAdapter channelAdapterCollege;
    private ChannelAdapter channelAdapterMarket;

    @Override
    public int getLayoutId() {
        return R.layout.act_news_channel;
    }

    /**
     * 入口
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, NewsChannelActivity.class);
        context.startActivity(intent);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRxManager.on(AppConstant.CHANNEL_SWAP, new Action1<ChannelItemMoveEvent>() {
            @Override
            public void call(ChannelItemMoveEvent channelItemMoveEvent) {
                if (channelItemMoveEvent != null) {
                    mPresenter.onItemSwap((ArrayList<NewsChannelTable>) channelAdapterMine.getAll(), channelItemMoveEvent.getFromPosition(), channelItemMoveEvent.getToPosition());
                }
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        titleBar.setTitleText(R.string.channel_manage);
        titleBar.setBackVisibility(false);
        titleBar.setRightImagSrc(R.drawable.logo_close_white);
        titleBar.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                overridePendingTransition(R.anim.anim_static, R.anim.top_exit);
            }
        });
        mPresenter.lodeChannelsRequest();
    }

    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine) {

        channelAdapterMine = new ChannelAdapter(mContext, R.layout.item_news_channel);
        newsChannelMineRv.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        newsChannelMineRv.setItemAnimator(new DefaultItemAnimator());
        newsChannelMineRv.setAdapter(channelAdapterMine);
        channelAdapterMine.replaceAll(newsChannelsMine);
        channelAdapterMine.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsChannelTable newsChannel = channelAdapterMine.get(position);

                if (newsChannel.getType() == channelAdapterCollege.getType()) {
                    channelAdapterCollege.add(newsChannel);
                } else if (newsChannel.getType() == channelAdapterNews.getType()) {
                    channelAdapterNews.add(newsChannel);
                } else if (newsChannel.getType() == channelAdapterMarket.getType()) {
                    channelAdapterMarket.add(newsChannel);
                }


                NewsChannelTableGroup tableGroup = new NewsChannelTableGroup();
                tableGroup.setMarkets(channelAdapterMarket.getAll());
                tableGroup.setNews(channelAdapterNews.getAll());
                tableGroup.setColleges(channelAdapterCollege.getAll());
                channelAdapterMine.removeAt(position);
                mPresenter.onItemAddOrRemove((ArrayList<NewsChannelTable>) channelAdapterMine.getAll(), tableGroup);

            }
        });

        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(channelAdapterMine);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(newsChannelMineRv);
        channelAdapterMine.setItemDragHelperCallback(itemDragHelperCallback);
    }

    @Override
    public void returnMoreNewsChannels(final NewsChannelTableGroup group) {

        if (group == null) {
            return;
        }
        // news
        List<NewsChannelTable> newsChannelTables = group.getNews();
        if (newsChannelTables != null && !newsChannelTables.isEmpty()) {
            channelAdapterNews = new ChannelAdapter(mContext, R.layout.item_news_channel);
            newsChannelNewsRv.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
            newsChannelNewsRv.setItemAnimator(new DefaultItemAnimator());
            newsChannelNewsRv.setAdapter(channelAdapterNews);
            channelAdapterNews.replaceAll(newsChannelTables);
            channelAdapterNews.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    NewsChannelTable newsChannel = channelAdapterNews.get(position);
                    channelAdapterMine.add(newsChannel);
                    channelAdapterNews.removeAt(position);
                    mPresenter.onItemAddOrRemove((ArrayList<NewsChannelTable>) channelAdapterMine.getAll(), group);
                }
            });
        }

        // college
        List<NewsChannelTable> collegesChannelTables = group.getColleges();
        if (collegesChannelTables != null && !collegesChannelTables.isEmpty()) {

            channelAdapterCollege = new ChannelAdapter(mContext, R.layout.item_news_channel);
            newsChannelCollegeRv.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
            newsChannelCollegeRv.setItemAnimator(new DefaultItemAnimator());
            newsChannelCollegeRv.setAdapter(channelAdapterCollege);
            channelAdapterCollege.replaceAll(collegesChannelTables);
            channelAdapterCollege.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    NewsChannelTable newsChannel = channelAdapterCollege.get(position);
                    channelAdapterMine.add(newsChannel);
                    channelAdapterCollege.removeAt(position);
                    mPresenter.onItemAddOrRemove((ArrayList<NewsChannelTable>) channelAdapterMine.getAll(), group);
                }
            });
        }

        // market
        List<NewsChannelTable> marketChannelTables = group.getMarkets();
        if (marketChannelTables != null && !marketChannelTables.isEmpty()) {

            channelAdapterMarket = new ChannelAdapter(mContext, R.layout.item_news_channel);
            newsChannelMarketRv.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
            newsChannelMarketRv.setItemAnimator(new DefaultItemAnimator());
            newsChannelMarketRv.setAdapter(channelAdapterMarket);
            channelAdapterMarket.replaceAll(marketChannelTables);
            channelAdapterMarket.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    NewsChannelTable newsChannel = channelAdapterCollege.get(position);
                    channelAdapterMine.add(newsChannel);
                    channelAdapterMarket.removeAt(position);
                    mPresenter.onItemAddOrRemove((ArrayList<NewsChannelTable>) channelAdapterMine.getAll(), group);
                }
            });
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
