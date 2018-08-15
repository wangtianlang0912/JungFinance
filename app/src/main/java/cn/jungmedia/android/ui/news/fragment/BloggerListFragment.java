package cn.jungmedia.android.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonwidget.LoadingTip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.ui.news.adapter.BloggerListAdapter;
import cn.jungmedia.android.ui.news.contract.BloggerListContract;
import cn.jungmedia.android.ui.news.event.BloggerStateEvent;
import cn.jungmedia.android.ui.news.model.BloggerListModel;
import cn.jungmedia.android.ui.news.presenter.BloggerListPresenter;
import cn.jungmedia.android.utils.MyUtils;

/**
 * des:博客
 * Created by xsf
 * on 2016.09.17:30
 */
public class BloggerListFragment extends BaseFragment<BloggerListPresenter, BloggerListModel> implements BloggerListContract.View, OnRefreshListener, OnLoadMoreListener, BloggerListAdapter.OnSubscribeBtnClickListener {
    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    private BloggerListAdapter bloggerListAdapter;
    private List<BloggerModel.Media> datas = new ArrayList<>();

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
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        bloggerListAdapter = new BloggerListAdapter(getContext(), datas, this);
//        bloggerListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(bloggerListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (bloggerListAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.getBloggerListDataRequest(mStartPage);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onRefresh() {
        bloggerListAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getBloggerListDataRequest(mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (!irc.canLoadMore()) {
            return;
        }
        bloggerListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getBloggerListDataRequest(mStartPage);
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


            if (counter.getPageIndex() < counter.getPageCount()) {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                mStartPage++;
            } else {
                irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
            }
        }
    }

    @Override
    public void returnFocusBloggerState(BaseRespose<FavActionModel> respose, boolean status) {
        if (respose.success()) {
            FavActionModel activityModel = respose.data;
            if (activityModel != null && activityModel.getFavorite() != null) {
                BloggerModel.Media media = new BloggerModel.Media();
                media.setObjectId(activityModel.getFavorite().getEntityId());
                int index = bloggerListAdapter.indexOf(media);
                if (index >= 0) {
                    if (status) {
                        BloggerModel.Favorite favorite = new BloggerModel.Favorite();
                        favorite.setUid(activityModel.getFavorite().getUid());
                        favorite.setObjectId(activityModel.getFavorite().getObjectId());
                        bloggerListAdapter.get(index).setFavorite(favorite);
                    } else {
                        bloggerListAdapter.get(index).setFavorite(null);
                    }
                }
                bloggerListAdapter.notifyDataSetChanged();
            }
        } else {
            showShortToast(respose.msg);
        }
    }

    @Override
    public void onSubscribeChanged(BloggerModel.Media blogger) {
        if (!MyUtils.isLogin()) {
            AppIntent.intentToLogin(getContext());
            return;
        }
        mPresenter.focusAction(blogger.getFavorite() != null ? blogger.getFavorite().getObjectId() : blogger.getObjectId()
                , blogger.getFavorite() != null);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBloggerStateEvent(BloggerStateEvent stateEvent) {

        BloggerModel.Media media = new BloggerModel.Media();
        media.setObjectId(stateEvent.getObjectId());
        int index = bloggerListAdapter.indexOf(media);
        if (index >= 0) {
            if (stateEvent.isSubcribed()) {
                BloggerModel.Favorite favorite = new BloggerModel.Favorite();
                favorite.setUid(stateEvent.getFavoriteUid());
                favorite.setObjectId(stateEvent.getFavoriteObjId());
                bloggerListAdapter.get(index).setFavorite(favorite);
            } else {
                bloggerListAdapter.get(index).setFavorite(null);
            }
        }
        bloggerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }
}
