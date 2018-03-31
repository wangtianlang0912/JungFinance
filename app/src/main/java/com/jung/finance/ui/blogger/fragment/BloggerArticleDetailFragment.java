package com.jung.finance.ui.blogger.fragment;

import android.os.Bundle;

import com.jung.finance.app.AppConstant;
import com.jung.finance.ui.news.fragment.ArticleDetailFragment;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/1. 上午12:03
 *
 *
 */
public class BloggerArticleDetailFragment extends ArticleDetailFragment {

    private int bloggerUid;

    @Override
    protected void loadData() {
        super.loadData();

        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
        bloggerUid = bundle.getInt(AppConstant.FLAG_DATA3);
    }
}
