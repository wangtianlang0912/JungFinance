package cn.jungmedia.android.ui.main.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.ActivityFavModel;
import cn.jungmedia.android.ui.main.contract.ActivityDetailContract;


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
                mView.returnFavActivityState(result, result != null);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void favActionActivity(int activityId, final boolean hasFav) {

        mRxManage.add(mModel.favActionActivity(activityId, hasFav).subscribe(new RxSubscriber<ActivityFavModel.Favorite>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ActivityFavModel.Favorite result) {
                mView.returnFavActivityState(result, !hasFav);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
