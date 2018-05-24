package cn.jungmedia.android.ui.blogger.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.swipe.SwipeItemLayout;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.ui.blogger.adapter.BloggerFavAdapter;
import cn.jungmedia.android.ui.blogger.bean.BloggerFavBean;
import cn.jungmedia.android.ui.blogger.contract.BloggerFavContract;
import cn.jungmedia.android.ui.blogger.model.BloggerFavModelImp;
import cn.jungmedia.android.ui.blogger.presenter.BloggerFavPresenterImp;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/15. 下午8:23
 *
 *
 */
public class BloggerFavFragment extends BaseFragment<BloggerFavPresenterImp, BloggerFavModelImp> implements BloggerFavContract.View, OnRefreshListener, OnLoadMoreListener, BloggerFavAdapter.OnDeleteBtnClickListener {

    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    private BloggerFavAdapter listAdapter;
    private List<BloggerFavBean.Favorite> datas = new ArrayList<>();

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

        irc.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));

        datas.clear();
        listAdapter = new BloggerFavAdapter(getContext(), datas, this);
//        listAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(listAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);

        emptyLayout.setVisibility(View.GONE);
        //数据为空才重新发起请求
        if (listAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.getMediaFavList(mStartPage);
        }
    }

    @Override
    public void onRefresh() {
        emptyLayout.setVisibility(View.GONE);
        listAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getMediaFavList(mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (!irc.canLoadMore()) {
            return;
        }
        listAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getMediaFavList(mStartPage);
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
    public void returnListData(BaseRespose<BloggerFavBean> response) {
        irc.setRefreshing(false);
        if (response.data == null) {
            listAdapter.clear();
            listAdapter.notifyDataSetChanged();
            return;
        }
        List<BloggerFavBean.Favorite> list = response.data.getFavorites();
        if (list != null) {
            if (listAdapter.getPageBean().isRefresh()) {
                listAdapter.replaceAll(list);
            } else {
                listAdapter.addAll(list);
            }
        }
        listAdapter.notifyDataSetChanged();
        Counter counter = response.data.getCounter();
        if (counter != null) {
            if (counter.getPageIndex() < counter.getPageCount()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                mStartPage++;
            } else {
                if (listAdapter.getSize() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    emptyLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void returnUnFavAction(Map<Integer, Boolean> map) {

        if (map != null && !map.isEmpty()) {

            Set<Map.Entry<Integer, Boolean>> set = map.entrySet();
            for (Map.Entry<Integer, Boolean> entry : set) {
                if (entry.getValue()) {
                    listAdapter.remove(new BloggerFavBean.Favorite(entry.getKey()));
                }
            }

            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBtnClicked(BloggerFavBean.Favorite favorite) {
        mPresenter.unFavAction(favorite.getObjectId());
    }

}
