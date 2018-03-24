package com.jung.finance.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.bean.BannerModel;
import com.jung.finance.bean.Counter;
import com.jung.finance.bean.LinkModel;
import com.jung.finance.ui.news.adapter.NewListAdapter;
import com.jung.finance.ui.news.contract.NewsListContract;
import com.jung.finance.ui.news.model.NewsListModel;
import com.jung.finance.ui.news.presenter.NewsListPresenter;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * des:新闻fragment
 * Created by xsf
 * on 2016.09.17:30
 */
public class NewsFrament extends BaseFragment<NewsListPresenter, NewsListModel> implements NewsListContract.View, OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    private NewListAdapter newListAdapter;
    private List<ArticleModel.Article> datas = new ArrayList<>();

    private String mNewsId;
    private int mStartPage=0;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private boolean isVisible;

    @Override
    protected int getLayoutResource() {
        return R.layout.framents_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mNewsId = getArguments().getString(AppConstant.NEWS_ID);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        newListAdapter = new NewListAdapter(getContext(), datas);
        newListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(newListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if(newListAdapter.getSize()<=0) {
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsId, mStartPage);
        }
    }


    @Override
    public void returnNewsListData(ArticleModel newsSummaries) {
        List<ArticleModel.Article> list = newsSummaries.getArticles();
        if (list != null) {
            if (newListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                newListAdapter.replaceAll(list);
            } else {
                newListAdapter.addAll(list);
            }
        }
        newListAdapter.notifyDataSetChanged();
        Counter counter = newsSummaries.getCounter();
        if (counter != null) {

            mStartPage++;
            if (counter.getPageIndex() < counter.getPageSize()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);

            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);

            }
        }
    }

    @Override
    public void returnBannerList(List<BannerModel.Banner> list) {

    }

    @Override
    public void returnAdLink(LinkModel linkModel) {

    }

    /**
     * 返回顶部
     */
    @Override
    public void scrolltoTop() {
        irc.smoothScrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        newListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getNewsListDataRequest(mNewsId, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        newListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getNewsListDataRequest(mNewsId, mStartPage);
    }

    @Override
    public void showLoading(String title) {
        if( newListAdapter.getPageBean().isRefresh()) {
            if(newListAdapter.getSize()<=0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( newListAdapter.getPageBean().isRefresh()) {
            if(newListAdapter.getSize()<=0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
            }
            irc.setRefreshing(false);
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

}
