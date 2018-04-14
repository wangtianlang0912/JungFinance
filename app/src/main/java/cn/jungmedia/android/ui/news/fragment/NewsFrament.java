package com.jung.android.ui.news.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.banner.BannerAdapter;
import com.aspsine.irecyclerview.banner.BannerBean;
import com.aspsine.irecyclerview.banner.BannerGalleryView;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jung.android.app.AppConstant;
import com.jung.android.app.AppIntent;
import com.jung.android.bean.ArticleModel;
import com.jung.android.bean.BannerModel;
import com.jung.android.bean.Counter;
import com.jung.android.bean.LinkModel;
import com.jung.android.ui.news.adapter.NewListAdapter;
import com.jung.android.ui.news.contract.NewsListContract;
import com.jung.android.ui.news.model.NewsListModel;
import com.jung.android.ui.news.presenter.NewsListPresenter;
import com.jung.finance.R;
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
    private int mStartPage = 0;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    private boolean isVisible;

    ArticleModel.Article mArticleAd;

    private BannerGalleryView bannerView;
    private List<BannerBean> bannerList = new ArrayList<BannerBean>();
    BannerAdapter bannerAdapter = null;

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

        bannerView = new BannerGalleryView(getActivity());
        bannerView.getPagercontrol().setHighlightColor(Color.WHITE);
        bannerView.getPagercontrol().setBarColor(Color.LTGRAY);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                bannerView.getPagercontrol().getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bannerView.getPagercontrol().setLayoutParams(layoutParams);
        bannerView.setOnItemClicklistener(getOnItemClickListener());
        bannerAdapter = new BannerAdapter(getActivity(), bannerList, null);
        bannerAdapter.setDefaultLogoRes(R.drawable.logo_pic);
        bannerView.setAdapter(bannerAdapter);
        bannerView.notifyPagerControler();

        irc.addHeaderView(bannerView);


        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        newListAdapter = new NewListAdapter(getContext(), datas);
//        newListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(newListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (newListAdapter.getSize() <= 0) {
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
        if (mArticleAd != null && !newListAdapter.contains(mArticleAd)) {
            newListAdapter.addAt(3, mArticleAd);
        }
        newListAdapter.notifyDataSetChanged();
        Counter counter = newsSummaries.getCounter();
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
    public void returnBannerList(List<BannerModel.Banner> list) {
        if (list != null) {

            bannerList.clear();
            List<BannerBean> bannerBeans = new ArrayList<BannerBean>();
            for (BannerModel.Banner data : list) {
                BannerBean bannerBean = new BannerBean();
                bannerBean.setId(Integer.valueOf(data.getObjectId()));
                bannerBean.setImgUrl(data.getImage());
                bannerBean.setUrl(data.getUrl());
                bannerBean.setTitle(data.getTitle());
                bannerBean.setObj(data);
                bannerBeans.add(bannerBean);
            }
            bannerView.setNumPages(bannerBeans.size());
            bannerList.addAll(bannerBeans);
            bannerAdapter.notifyDataSetChanged();
            bannerView.notifyPagerControler();
//            bannerView.startAutoScroll();

            bannerView.setOnItemClicklistener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    BannerBean bannerBean = (BannerBean) adapterView.getItemAtPosition(i);
                    if (bannerBean != null) {
                        AppIntent.intentToCommonWeb(getActivity(), R.string.news, bannerBean.getUrl());
                    }
                }
            });
        }
    }

    @Override
    public void returnAdLink(LinkModel linkModel) {
        if (linkModel != null && linkModel.getLink() != null) {
            ArticleModel.Article article = new ArticleModel.Article();
            article.setImage(linkModel.getLink().getWapImage());
            article.setTitle(linkModel.getLink().getTitle());
            article.setSummary(linkModel.getLink().getUrl());
            article.setType(ArticleModel.ContentType.AD);

            if (!datas.isEmpty()) {
                datas.set(3, article);
            } else {
                mArticleAd = article;
            }
            newListAdapter.notifyDataSetChanged();
        }
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
        if (!irc.canLoadMore()) {
            return;
        }
        newListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getNewsListDataRequest(mNewsId, mStartPage);
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

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        };
    }
}
