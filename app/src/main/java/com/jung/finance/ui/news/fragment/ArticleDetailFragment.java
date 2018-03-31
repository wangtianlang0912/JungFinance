package com.jung.finance.ui.news.fragment;

import android.os.Bundle;

import com.jung.finance.app.AppConstant;
import com.jung.finance.ui.common.CommonWebFragment;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/31. 下午11:58
 *
 *
 */
public class ArticleDetailFragment extends CommonWebFragment {

    protected int articleId;

    @Override
    protected void loadData() {
        super.loadData();
        homeIntent = getActivity().getIntent();
        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
        articleId = bundle.getInt(AppConstant.FLAG_DATA2);

    }
}
