package com.jung.android.ui.main.presenter;

import com.jung.finance.R;
import com.jung.android.bean.ActivityFavModel;
import com.jung.android.ui.main.contract.ActivityDetailContract;
import com.leon.common.baserx.RxSubscriber;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/7. 下午4:31
 *
 *
 */
public class ActivityDetailPresenterImp extends ActivityDetailContract.Presenter {
    @Override
    public void attentActivity(int objectId) {

    }

    @Override
    public void getFavActivityState(int activityId) {
        mRxManage.add(mModel.getFavActivityState(activityId).subscribe(new RxSubscriber<ActivityFavModel.Favorite>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ActivityFavModel.Favorite result) {
                mView.returnFavActivityState(result);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void favActionActivity(int activityId, boolean hasFav) {

        mRxManage.add(mModel.favActionActivity(activityId,hasFav).subscribe(new RxSubscriber<ActivityFavModel.Favorite>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ActivityFavModel.Favorite result) {
                mView.returnFavActivityState(result);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
