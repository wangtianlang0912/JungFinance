package cn.jungmedia.android.ui.setting.contract;


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
 * @date 2018/4/14. 下午2:20
 *
 *
 */
public class SettingContract {

    public interface Model extends BaseModel {

        public Observable<Boolean> logout();

    }

    public interface View extends BaseView {

        public void returnLogout(boolean result);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void logout();
    }

}
