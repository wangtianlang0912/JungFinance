package cn.jungmedia.android.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.ui.common.CommonActivity;
import cn.jungmedia.android.ui.main.contract.SearchContract;
import cn.jungmedia.android.ui.main.model.SearchListModel;
import cn.jungmedia.android.ui.main.presenter.SearchPresenter;
import cn.jungmedia.android.ui.news.adapter.NewListAdapter;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/25. 下午11:41
 *
 *
 */
public class SearchFragment extends BaseFragment<SearchPresenter, SearchListModel> implements SearchContract.View, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.list_layout)
    LinearLayout listLayout;
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.search_view)
    SearchView searchView;
    private NewListAdapter newListAdapter;
    private List<ArticleModel.Article> datas = new ArrayList<>();

    private int mStartPage = 0;
    private String mKeyword = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.frag_search;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {

        CommonActivity activity = (CommonActivity) getActivity();
        activity.getNtb().setVisibility(View.GONE);

        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        newListAdapter = new NewListAdapter(getContext(), datas);
//        newListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(newListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        listLayout.setVisibility(View.GONE);

        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String key) {

                mKeyword = key;
                datas.clear();
                newListAdapter.notifyDataSetChanged();
                mStartPage = 0;
                mPresenter.searchByKey(key, mStartPage);
            }
        });
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                getActivity().finish();
            }
        });
        searchView.setOnClickClear(new bCallBack() {
            @Override
            public void BackAciton() {

                datas.clear();
                newListAdapter.notifyDataSetChanged();

                searchView.setHistoryViewVisible(true);
                listLayout.setVisibility(View.GONE);
            }
        });
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

            if (counter.getPageIndex() < counter.getPageCount()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                mStartPage++;
            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);

            }
        }

        if (newListAdapter.getItemCount() > 0) {
            searchView.setHistoryViewVisible(false);
            listLayout.setVisibility(View.VISIBLE);
        } else {
            searchView.setHistoryViewVisible(true);
            listLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRefresh() {
        newListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.searchByKey(mKeyword, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (!irc.canLoadMore()) {
            return;
        }
        newListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.searchByKey(mKeyword, mStartPage);
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
}
