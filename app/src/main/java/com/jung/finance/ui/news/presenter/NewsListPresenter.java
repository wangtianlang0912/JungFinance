package com.jung.finance.ui.news.presenter;

import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.bean.ArticleModel;
import com.jung.finance.bean.BannerModel;
import com.jung.finance.bean.LinkModel;
import com.jung.finance.ui.news.contract.NewsListContract;
import com.leon.common.baserx.RxSubscriber;

import java.util.List;

import rx.functions.Action1;

/**
 * des:
 * Created by xsf
 * on 2016.09.14:53
 */
public class NewsListPresenter extends NewsListContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
        //监听返回顶部动作
        mRxManage.on(AppConstant.NEWS_LIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.scrolltoTop();
            }
        });
    }

    /**
     * 请求获取列表数据
     *
     * @param id
     * @param startPage
     */
    @Override
    public void getNewsListDataRequest(String id, int startPage) {
        mRxManage.add(mModel.getNewsListData("".equals(id), id, startPage).subscribe(new RxSubscriber<ArticleModel>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ArticleModel articleModel) {
                mView.returnNewsListData(articleModel);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getBannerList() {
        mRxManage.add(mModel.getBannerList().subscribe(new RxSubscriber<List<BannerModel.Banner>>(mContext, false) {

            @Override
            protected void _onNext(List<BannerModel.Banner> banners) {
                mView.returnBannerList(banners);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void getAdList(String site, String alias) {
        mRxManage.add(mModel.getAdList(site, alias).subscribe(new RxSubscriber<LinkModel>(mContext, false) {

            @Override
            protected void _onNext(LinkModel linkModel) {
                mView.returnAdLink(linkModel);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
