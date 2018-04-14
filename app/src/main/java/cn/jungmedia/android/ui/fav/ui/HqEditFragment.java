package cn.jungmedia.android.ui.fav.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.swipe.SwipeItemLayout;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.ui.fav.adapter.HqEditListAdapter;
import cn.jungmedia.android.ui.fav.bean.NewsFavBean;
import cn.jungmedia.android.ui.fav.contract.HqEditContract;
import cn.jungmedia.android.ui.fav.model.HqEditModelImp;
import cn.jungmedia.android.ui.fav.presenter.HqEditPresenter;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/10. 下午11:13
 *
 *
 */
public class HqEditFragment extends BaseFragment<HqEditPresenter, HqEditModelImp> implements HqEditContract.View, OnRefreshListener, OnLoadMoreListener, HqEditListAdapter.OnDeleteBtnClickListener {
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    private HqEditListAdapter listAdapter;
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
        listAdapter = new HqEditListAdapter(getContext(), datas, this);
//        listAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(listAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (listAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.loadDataList(mStartPage);
        }
    }

    @Override
    public void onRefresh() {
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
        List<NewsFavBean.Favorite> list = data.getFavorites();
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
            if (counter.getPageIndex() < counter.getPageCount()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                mStartPage++;
            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
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

    @Override
    public void onBtnClicked(NewsFavBean.Favorite favorite) {
        mPresenter.unFavAction(favorite.getObjectId());
    }
}
