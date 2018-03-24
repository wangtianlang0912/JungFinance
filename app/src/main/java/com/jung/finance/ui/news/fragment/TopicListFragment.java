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
import com.jung.finance.bean.Counter;
import com.jung.finance.bean.TopicModel;
import com.jung.finance.ui.news.adapter.TopicListAdapter;
import com.jung.finance.ui.news.contract.TopicListContract;
import com.jung.finance.ui.news.model.TopicListModel;
import com.jung.finance.ui.news.presenter.TopicListPresenter;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * des:专栏
 * Created by xsf
 * on 2016.09.17:30
 */
public class TopicListFragment extends BaseFragment<TopicListPresenter, TopicListModel> implements TopicListContract.View, OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    private TopicListAdapter listAdapter;
    private List<TopicModel.Theme> datas = new ArrayList<>();

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
        listAdapter = new TopicListAdapter(getContext(), datas);
        listAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(listAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (listAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.getListDataRequest(mUid, mStartPage);
        }
    }

    @Override
    public void onRefresh() {
        listAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getListDataRequest(mUid, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        listAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getListDataRequest(mUid, mStartPage);
    }

    @Override
    public void showLoading(String title) {
        if (listAdapter.getPageBean().isRefresh()) {
            if (listAdapter.getSize() <= 0) {
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
        if (listAdapter.getPageBean().isRefresh()) {
            if (listAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
            }
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void returnListData(TopicModel data) {
        List<TopicModel.Theme> list = data.getThemes();
        if (list != null) {
            if (listAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                listAdapter.replaceAll(list);
            } else {
                listAdapter.addAll(list);
            }
        }
        listAdapter.notifyDataSetChanged();
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
