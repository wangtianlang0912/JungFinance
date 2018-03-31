package com.jung.finance.ui.main.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jung.finance.R;
import com.jung.finance.bean.ActivityModel;
import com.jung.finance.bean.Counter;
import com.jung.finance.ui.main.adapter.ActivityListAdapter;
import com.jung.finance.ui.main.contract.ActivityContract;
import com.jung.finance.ui.main.model.ActivityModelImp;
import com.jung.finance.ui.main.presenter.ActivityPresenterImp;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonwidget.LoadingTip;
import com.leon.common.commonwidget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/***
 * 活动
 *
 * @author niufei
 *
 *
 * @date 2018/3/10. 下午10:14
 *
 *
 */
public class ActivityMainFragment extends BaseFragment<ActivityPresenterImp, ActivityModelImp> implements ActivityContract.View, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    private ActivityListAdapter listAdapter;
    private List<ActivityModel.Activity> datas = new ArrayList<>();
    int mStartPage;

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        ntb.setTitleText(R.string.main_tab_activity);
        ntb.setBackVisibility(false);
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        listAdapter = new ActivityListAdapter(getContext(), datas);
//        listAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(listAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (listAdapter.getSize() <= 0) {
            mStartPage = 0;
            mPresenter.loadActivityList(mStartPage);
        }
    }

    @Override
    public void onRefresh() {
        listAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.loadActivityList(mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        listAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.loadActivityList(mStartPage);
    }

    @Override
    public void showLoading(String title) {
        if (listAdapter.getPageBean().isRefresh()) {
            if (listAdapter.getSize() <= 0) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void returnActivityModel(ActivityModel activityModel) {
        List<ActivityModel.Activity> list = activityModel.getActivities();
        if (list != null) {
            if (listAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                listAdapter.replaceAll(list);
            } else {
                listAdapter.addAll(list);
            }
        }
        listAdapter.notifyDataSetChanged();
        Counter counter = activityModel.getCounter();
        if (counter != null) {

            mStartPage++;
            if (counter.getPageIndex() < counter.getPageCount()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);

            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);

            }
        }
    }
}
