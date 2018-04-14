package com.jung.android.ui.fav.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/10. 下午11:20
 *
 *
 */
public class NewsEditContract {

    public interface Model extends BaseModel {

        public void loadData(int startPage);

        public void unFavAction(int objectId);


    }

    public interface View extends BaseView {

        public void returnListData();

        public void returnUnFavAction();
    }

    public static abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void loadDataList(int startPage);

        public abstract void unFavAction(int objectId);
    }
}
