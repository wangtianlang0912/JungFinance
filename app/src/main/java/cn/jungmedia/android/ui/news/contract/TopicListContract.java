package com.jung.android.ui.news.contract;


import com.jung.android.bean.TopicModel;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import rx.Observable;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/24. 下午4:54
 *
 *
 */
public class TopicListContract {

    public interface Model extends BaseModel {
        //请求获取新闻
        Observable<TopicModel> getListData(String uid, int startPage);
    }

    public interface View extends BaseView {
        void returnListData(TopicModel data);
    }

    public abstract static class Presenter extends BasePresenter<TopicListContract.View, TopicListContract.Model> {
        public abstract void getListDataRequest(final String uid, int startPage);
    }
}
