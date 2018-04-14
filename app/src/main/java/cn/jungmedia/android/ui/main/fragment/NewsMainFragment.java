package com.jung.android.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jung.android.app.AppConstant;
import com.jung.android.bean.NewsChannelTable;
import com.jung.android.ui.news.activity.NewsChannelActivity;
import com.jung.finance.R;
import com.jung.android.ui.main.contract.NewsMainContract;
import com.jung.android.ui.main.model.NewsMainModel;
import com.jung.android.ui.main.presenter.NewsMainPresenter;
import com.jung.android.ui.news.fragment.BloggerListFragment;
import com.jung.android.ui.news.fragment.NewsFrament;
import com.jung.android.ui.news.fragment.TopicListFragment;
import com.jung.android.utils.MyUtils;
import com.leon.common.base.BaseFragment;
import com.leon.common.base.BaseFragmentAdapter;
import com.leon.common.commonutils.ToastUitl;
import com.leon.common.commonwidget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * des:新闻首页首页
 * Created by xsf
 * on 2016.09.16:45
 */
public class NewsMainFragment extends BaseFragment<NewsMainPresenter, NewsMainModel> implements NewsMainContract.View {

    //    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.ntb)
    NormalTitleBar ntb;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.add_channel_iv)
    ImageView addChannelIv;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.app_bar_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.lodeMineChannelsRequest();
        ntb.setTvLeftVisiable(false);
        ntb.setTitleText(getString(R.string.app_name));
        ntb.setRightImagSrc(R.drawable.icon_search);
        ntb.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUitl.show("Search", Toast.LENGTH_SHORT);
            }
        });

    }

    @OnClick(R.id.add_channel_iv)
    public void clickAdd() {
        NewsChannelActivity.startAction(getContext());
        getActivity().overridePendingTransition(R.anim.bottom_enter, R.anim.anim_static);

    }

    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelsMine) {
        if (newsChannelsMine != null) {
            List<String> channelNames = new ArrayList<>();
            List<Fragment> mNewsFragmentList = new ArrayList<>();
            for (int i = 0; i < newsChannelsMine.size(); i++) {
                channelNames.add(newsChannelsMine.get(i).getNewsChannelName());
                mNewsFragmentList.add(createListFragments(newsChannelsMine.get(i)));
            }
            if (fragmentAdapter == null) {
                fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
            } else {
                //刷新fragment
                fragmentAdapter.setFragments(getChildFragmentManager(), mNewsFragmentList, channelNames);
            }
            viewPager.setAdapter(fragmentAdapter);
            tabs.setupWithViewPager(viewPager);
            MyUtils.dynamicSetTabLayoutMode(tabs);
            setPageChangeListener();
        }
    }

    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private BaseFragment createListFragments(NewsChannelTable newsChannel) {

        if ("top".equals(newsChannel.getNewsChannelId())) {
            NewsFrament fragment = new NewsFrament();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.NEWS_ID, "");
            bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
            fragment.setArguments(bundle);
            return fragment;

        } else if ("media".equals(newsChannel.getNewsChannelId())) {
            BloggerListFragment listFragment = new BloggerListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.NEWS_ID, "");
            bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
            listFragment.setArguments(bundle);
            return listFragment;
        } else if ("theme".equals(newsChannel.getNewsChannelId())) {

            TopicListFragment listFragment = new TopicListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.NEWS_ID, "");
            bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
            listFragment.setArguments(bundle);
            return listFragment;
        } else {
            NewsFrament fragment = new NewsFrament();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.NEWS_ID, newsChannel.getNewsChannelId());
            bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
