package cn.jungmedia.android.ui.blogger.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import cn.jungmedia.android.ui.blogger.bean.FansBean;
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
 * @date 2018/4/15. 下午10:06
 *
 *
 */
public class FansContract {

    public interface Model extends BaseModel {

        Observable<BaseRespose<FansBean>> getFansList(int startPage);

    }

    public interface View extends BaseView {


        void returnListData(FansBean data);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void getFansList(int startPage);

    }

}
