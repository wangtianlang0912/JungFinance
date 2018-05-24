package cn.jungmedia.android.ui.score.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.commonwidget.LoadingTip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.ui.score.adapter.ScoreAdapter;
import cn.jungmedia.android.ui.score.bean.ScoreBean;
import cn.jungmedia.android.ui.score.contract.ScoreContract;
import cn.jungmedia.android.ui.score.model.ScoreModelImp;
import cn.jungmedia.android.ui.score.presenter.ScorePresenterImp;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/15. 下午10:23
 *
 *
 */
public class ScoreListFragment extends BaseFragment<ScorePresenterImp, ScoreModelImp> implements ScoreContract.View, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.irc)
    IRecyclerView irc;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;
    ImageView logoView;
    TextView roleView;
    TextView nickText;
    TextView scoreText;
    private ScoreAdapter listAdapter;
    private List<ScoreBean.Score> datas = new ArrayList<>();

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

        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.score_header, null);
        irc.addHeaderView(headerView);
        logoView = (ImageView) headerView.findViewById(R.id.logo_view);
        nickText = (TextView) headerView.findViewById(R.id.nick_text);
        scoreText = (TextView) headerView.findViewById(R.id.score_text);
        roleView = (TextView) headerView.findViewById(R.id.role_view);

        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        datas.clear();
        listAdapter = new ScoreAdapter(getContext(), datas);
//        bloggerListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(listAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //数据为空才重新发起请求
        if (listAdapter.getSize() <= 0) {
            mStartPage = 1;
            mPresenter.getScoreInfo(mStartPage);
        }
    }

    @Override
    public void onRefresh() {
        listAdapter.getPageBean().setRefresh(true);
        mStartPage = 1;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getScoreInfo(mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        if (!irc.canLoadMore()) {
            return;
        }
        listAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getScoreInfo(mStartPage);
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
    public void returnListData(ScoreBean data) {

        if (data.getUser() != null) {

            ImageLoaderUtils.displayRound(getActivity(), logoView, data.getUser().getLogo());
            nickText.setText(data.getUser().getNick());
        }
        scoreText.setText("共" + data.getMember().getScore() + "积分");
        roleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppIntent.intentToScoreRole(getActivity());
            }
        });
        irc.setRefreshing(false);
        List<ScoreBean.Score> list = data.getScores();
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
                }
            }
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
