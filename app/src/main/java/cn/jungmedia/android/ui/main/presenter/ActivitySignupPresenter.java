package cn.jungmedia.android.ui.main.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.main.bean.CrewBean;
import cn.jungmedia.android.ui.main.contract.ActivitySignupContract;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/14. 下午9:59
 *
 *
 */
public class ActivitySignupPresenter extends ActivitySignupContract.Presenter {
    @Override
    public void signup(int activeId, String phone, String name, int memberNum, final String company) {
        mRxManage.add(mModel.signup(activeId, phone, name, memberNum, company).subscribe(new RxSubscriber<BaseRespose<CrewBean>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose<CrewBean> baseRespose) {
                mView.returnSignup(baseRespose);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
