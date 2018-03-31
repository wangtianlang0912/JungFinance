package com.jung.finance.ui.blogger.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.bean.BloggerModel;
import com.jung.finance.bean.Counter;
import com.jung.finance.ui.blogger.bean.BloggerBean;
import com.jung.finance.ui.blogger.contract.BloggerContract;
import com.jung.finance.ui.blogger.model.BloggerModelImp;
import com.jung.finance.ui.blogger.presenter.BloggerPresenterImp;
import com.jung.finance.ui.news.adapter.NewListAdapter;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/31. 下午10:28
 *
 *
 */
public class BloggerFragment extends BaseFragment<BloggerPresenterImp, BloggerModelImp> implements BloggerContract.View, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.logo_view)
    ImageView logoView;
    @Bind(R.id.title_view)
    TextView titleView;
    @Bind(R.id.summary_view)
    TextView summaryView;
    @Bind(R.id.subscribe_num)
    TextView subscribeNum;
    @Bind(R.id.fans_num)
    TextView fansNum;
    @Bind(R.id.param_layout)
    LinearLayout paramLayout;
    @Bind(R.id.subscribe_btn)
    TextView subscribeBtn;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;

    NewListAdapter newListAdapter;
    int mStartPage;
    int mUid;
    private List<ArticleModel.Article> datas = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_blogger;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        Bundle bundle = getActivity().getIntent().getBundleExtra(AppConstant.FLAG_BUNDLE);
        if (bundle != null) {
            mUid = bundle.getInt(AppConstant.FLAG_DATA);
            mPresenter.getBloggerInfo(mUid);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        newListAdapter = new NewListAdapter(getContext(), datas);
//        bloggerListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(newListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (newListAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.getBloggerArticleList(mUid, mStartPage);
        }
    }

    @Override
    public void onRefresh() {
        newListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getBloggerArticleList(mUid, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (!irc.canLoadMore()) {
            return;
        }
        newListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getBloggerArticleList(mUid, mStartPage);
    }

    @Override
    public void showLoading(String title) {
        if (newListAdapter.getPageBean().isRefresh()) {
            if (newListAdapter.getSize() <= 0) {
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
        if (newListAdapter.getPageBean().isRefresh()) {
            if (newListAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
            }
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void returnListData(ArticleModel data) {
        List<ArticleModel.Article> list = data.getArticles();
        if (list != null) {
            if (newListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                newListAdapter.replaceAll(list);
            } else {
                newListAdapter.addAll(list);
            }
        }
        newListAdapter.notifyDataSetChanged();
        Counter counter = data.getCounter();
        if (counter != null) {
            if (counter.getPageIndex() < counter.getPageCount()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                mStartPage++;
            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
            }
        }
    }

    @Override
    public void returnBloggerInfo(BloggerBean bloggerBean) {
        if (bloggerBean == null || bloggerBean.getMedia() == null) {
            return;
        }
        BloggerModel.Media blogger = bloggerBean.getMedia();
        ImageLoaderUtils.displayRound(getActivity(), logoView, blogger.getCoverImage());
        titleView.setText(blogger.getName());
        summaryView.setText(blogger.getRemark());
        subscribeNum.setText(blogger.getRole().getmCount() + "");
        fansNum.setText(blogger.getGznum() + "");
        subscribeBtn.setText(blogger.getRole().getRole() == 1 ? "+订阅" : "已订阅");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.logo_view, R.id.subscribe_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logo_view:
                break;
            case R.id.subscribe_btn:
                break;
        }
    }
}
