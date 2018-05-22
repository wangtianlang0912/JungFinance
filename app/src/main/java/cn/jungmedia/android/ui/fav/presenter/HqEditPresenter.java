package cn.jungmedia.android.ui.fav.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import java.util.Map;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.ui.fav.bean.NewsFavBean;
import cn.jungmedia.android.ui.fav.contract.HqEditContract;
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
 * @date 2018/4/10. 下午11:25
 *
 *
 */
public class HqEditPresenter extends HqEditContract.Presenter {
    @Override
    public void loadDataList(int startPage) {
        mRxManage.add(mModel.loadData(startPage).subscribe(new RxSubscriber<BaseRespose<NewsFavBean>>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose<NewsFavBean> data) {
                if (!MyUtils.verifyToken(data)) {
                    AppApplication.getInvalidCallback().onTokenInvalid();
                    return;
                } else {
                    mView.returnListData(data.data);
                }
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
    public void unFavAction(int objectId) {
        mRxManage.add(mModel.unFavAction(objectId).subscribe(new RxSubscriber<Map<Integer, Boolean>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(Map<Integer, Boolean> result) {
                mView.stopLoading();
                mView.returnUnFavAction(result);
            }

            @Override
            protected void _onError(String message) {

                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }
}
