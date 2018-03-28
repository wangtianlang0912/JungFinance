package com.jung.finance.ui.news.contract;


import com.jung.finance.bean.FastModel;
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
 * @date 2018/3/28. 下午10:29
 *
 *
 */
public class FastListContract {

    public interface Model extends BaseModel {
        Observable<FastModel> getNewsListData(int startPage);

    }

    public interface View extends BaseView {
        void returnNewsListData(FastModel newsSummaries);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void getNewsListDataRequest(int startPage);
    }
}
