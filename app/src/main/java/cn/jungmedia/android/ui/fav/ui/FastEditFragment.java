package cn.jungmedia.android.ui.fav.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.swipe.SwipeItemLayout;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonwidget.LoadingTip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.ui.fav.adapter.FastEditListAdapter;
import cn.jungmedia.android.ui.fav.bean.NewsFavBean;
import cn.jungmedia.android.ui.fav.contract.FastEditContract;
import cn.jungmedia.android.ui.fav.event.NewsStateEvent;
import cn.jungmedia.android.ui.fav.model.FastEditModelImp;
import cn.jungmedia.android.ui.fav.presenter.FastEditPresenter;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/10. 下午11:15
 *
 *
 */
public class FastEditFragment extends BaseFragment<FastEditPresenter, FastEditModelImp> implements FastEditContract.View, OnRefreshListener, OnLoadMoreListener, FastEditListAdapter.OnDeleteBtnClickListener {
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    private FastEditListAdapter listAdapter;
    private List<NewsFavBean.Favorite> datas = new ArrayList<>();

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
        listAdapter = new FastEditListAdapter(getContext(), datas, this);
//        listAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(listAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);

        emptyLayout.setVisibility(View.GONE);
        //数据为空才重新发起请求
        if (listAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.loadDataList(mStartPage);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onRefresh() {
        emptyLayout.setVisibility(View.GONE);
        listAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.loadDataList(mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (!irc.canLoadMore()) {
            return;
        }
        listAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.loadDataList(mStartPage);
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
    public void returnListData(NewsFavBean data) {

        irc.setRefreshing(false);
        List<NewsFavBean.Favorite> list = data.getFavorites();
        if (list != null) {
            if (listAdapter.getPageBean().isRefresh()) {
                listAdapter.replaceAll(list);
            } else {
                listAdapter.addAll(list);
            }
        }
        listAdapter.notifyDataSetChanged();
        Counter counter = data.getCounter();
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
                    listAdapter.remove(new NewsFavBean.Favorite(entry.getKey()));
                }
            }

            listAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsStateEvent(NewsStateEvent stateEvent) {

        if (listAdapter == null) {
            return;
        }
        if (stateEvent.isFav()) {

            if (!listAdapter.getPageBean().isRefresh()) {
                onRefresh();
            }
        } else {
            listAdapter.remove(new NewsFavBean.Favorite(stateEvent.getObjectId()));
            listAdapter.notifyDataSetChanged();
            if (listAdapter.getSize() > 0) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBtnClicked(NewsFavBean.Favorite favorite) {
        mPresenter.unFavAction(favorite.getObjectId());
    }

    @Override
    public void onDestroyView() {

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        super.onDestroyView();
    }
}
