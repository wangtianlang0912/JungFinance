package cn.jungmedia.android.ui.main.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import cn.jungmedia.android.ui.main.bean.CrewBean;
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
 * @date 2018/4/14. 下午9:17
 *
 *
 */
public class ActivitySignupContract {

    public interface Model extends BaseModel {

        Observable<BaseRespose<CrewBean>> signup(int activeId, String phone, String name, int memberNum, String company);

    }

    public interface View extends BaseView {

        void returnSignup(BaseRespose<CrewBean> baseRespose);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void signup(int activeId, String phone, String name, int memberNum, String company);
    }

}
