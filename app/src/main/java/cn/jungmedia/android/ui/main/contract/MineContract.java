package cn.jungmedia.android.ui.main.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import cn.jungmedia.android.ui.user.bean.UserInfo;
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
 * @date 2018/3/18. 下午2:31
 *
 *
 */
public interface MineContract {

    interface IMineModel extends BaseModel {
        Observable<BaseRespose<UserInfo>> getUserInfo();
    }


    interface IMineView extends BaseView {

        void returnUserInfoResponse(UserInfo response);

    }

    abstract class MinePresenter extends BasePresenter<IMineView, IMineModel> {
        public abstract void getUserInfo();
    }
}
