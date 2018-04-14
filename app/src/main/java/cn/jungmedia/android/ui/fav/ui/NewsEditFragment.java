package com.jung.android.ui.fav.ui;

import com.jung.android.ui.fav.presenter.NewsEditPresenter;
import com.jung.android.ui.fav.contract.NewsEditContract;
import com.jung.android.ui.fav.model.NewsEditModelImp;
import com.leon.common.base.BaseFragment;


/***
 *
 * @Copyright 2018
 *
 * @TODO 资讯
 *
 * @author niufei
 *
 *
 * @date 2018/4/10. 下午11:12
 *
 *
 */
public class NewsEditFragment extends BaseFragment<NewsEditPresenter, NewsEditModelImp> implements NewsEditContract.View {
    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void returnListData() {

    }

    @Override
    public void returnUnFavAction() {

    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    protected void initView() {

    }
}
