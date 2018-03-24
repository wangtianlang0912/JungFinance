package com.jung.finance.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.bean.BloggerModel;
import com.jung.finance.bean.Counter;
import com.jung.finance.ui.news.adapter.BloggerListAdapter;
import com.jung.finance.ui.news.contract.BloggerListContract;
import com.jung.finance.ui.news.model.BloggerListModel;
import com.jung.finance.ui.news.presenter.BloggerListPresenter;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * des:博客
 * Created by xsf
 * on 2016.09.17:30
 */
public class BloggerListFragment extends BaseFragment<BloggerListPresenter, BloggerListModel> implements BloggerListContract.View, OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    private BloggerListAdapter bloggerListAdapter;
    private List<BloggerModel.Media> datas = new ArrayList<>();

    private String mUid;
    private int mStartPage = 1;

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
            mUid = getArguments().getString(AppConstant.NEWS_ID);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        bloggerListAdapter = new BloggerListAdapter(getContext(), datas);
//        bloggerListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(bloggerListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (bloggerListAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.getBloggerListDataRequest(mUid, mStartPage);
        }
    }

    @Override
    public void onRefresh() {
        bloggerListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getBloggerListDataRequest(mUid, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        bloggerListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getBloggerListDataRequest(mUid, mStartPage);
    }

    @Override
    public void showLoading(String title) {
        if (bloggerListAdapter.getPageBean().isRefresh()) {
            if (bloggerListAdapter.getSize() <= 0) {
                irc.setRefreshing(true);
            }
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if (bloggerListAdapter.getPageBean().isRefresh()) {
            if (bloggerListAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
            }
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void returnListData(BloggerModel data) {
        List<BloggerModel.Media> list = data.getMedias();
        if (list != null) {
            if (bloggerListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                bloggerListAdapter.replaceAll(list);
            } else {
                bloggerListAdapter.addAll(list);
            }
        }
        bloggerListAdapter.notifyDataSetChanged();
        Counter counter = data.getCounter();
        if (counter != null) {

            mStartPage++;
            if (counter.getPageIndex() < counter.getPageSize()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);

            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);

            }
        }
    }
}
