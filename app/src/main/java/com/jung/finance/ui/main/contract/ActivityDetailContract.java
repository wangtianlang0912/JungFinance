package com.jung.finance.ui.main.contract;


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
 * @date 2018/4/7. 下午4:29
 *
 *
 */
public class ActivityDetailContract {


    public interface Model extends BaseModel {
        Observable<Boolean> attentActivity(int objectId);

        Observable<Boolean> getFavActivityState(int activityId);

        Observable<Boolean> favActionActivity(int activityId,boolean hasFav);
    }

    public interface View extends BaseView {
        void returnAttentActivity(boolean result);

        void returnFavActivityState(boolean result);

    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void attentActivity(int objectId);

        public abstract void getFavActivityState(int activityId);

        public abstract void favActionActivity(int activityId, boolean hasFav);

    }
}
