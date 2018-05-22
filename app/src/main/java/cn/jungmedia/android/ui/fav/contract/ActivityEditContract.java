package cn.jungmedia.android.ui.fav.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import java.util.Map;

import cn.jungmedia.android.ui.fav.bean.ActiveFavBean;
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
 * @date 2018/4/10. 下午11:16
 *
 *
 */
public class ActivityEditContract {

    public interface Model extends BaseModel {

        public Observable<BaseRespose<ActiveFavBean>> loadData(int startPage);

        public Observable<Map<Integer, Boolean>> unFavAction(int objectId);

    }

    public interface View extends BaseView {

        public void returnListData(BaseRespose<ActiveFavBean> respose);

        public void returnUnFavAction(Map<Integer, Boolean> result);

    }

    public static abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void loadDataList(int startPage);

        public abstract void unFavAction(int objectId);
    }
}
