package com.jung.android.ui.main.contract;

import com.jung.android.bean.ActivityModel;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import rx.Observable;

public interface ActivityContract {

    interface Model extends BaseModel {
        Observable<ActivityModel> lodeActivityList(int p);
    }

    interface View extends BaseView {
        void returnActivityModel(ActivityModel activityModel);
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void loadActivityList(int p);
    }
}
