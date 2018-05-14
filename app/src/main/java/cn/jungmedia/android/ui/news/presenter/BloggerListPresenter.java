package cn.jungmedia.android.ui.news.presenter;


import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.ui.news.contract.BloggerListContract;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.utils.MyUtils;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/24. 下午4:53
 *
 *
 */
public class BloggerListPresenter extends BloggerListContract.Presenter {
    @Override
    public void getBloggerListDataRequest(int startPage) {
        int uid = 0;
        UserInfo userInfo = MyUtils.getUserInfoFromPreference(AppApplication.getAppContext());
        if (userInfo != null) {
            uid = userInfo.getUser().getUid();
        }
        mRxManage.add(mModel.getListData(String.valueOf(uid), startPage).subscribe(new RxSubscriber<BloggerModel>(mContext, false) {
            @Override
            protected void _onNext(BloggerModel data) {
                mView.returnListData(data);
                mView.stopLoading();
            }

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void focusAction(int objectId,final boolean status) {
        mRxManage.add(mModel.focusAction(objectId, status).subscribe(new RxSubscriber<BaseRespose<FavActionModel>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(BaseRespose<FavActionModel> result) {
                mView.stopLoading();
                mView.returnFocusBloggerState(result, !status);
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }
}
