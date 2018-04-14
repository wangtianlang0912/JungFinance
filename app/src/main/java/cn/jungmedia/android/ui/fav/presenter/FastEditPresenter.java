package cn.jungmedia.android.ui.fav.presenter;

import com.leon.common.baserx.RxSubscriber;

import java.util.Map;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.fav.bean.NewsFavBean;
import cn.jungmedia.android.ui.fav.contract.FastEditContract;


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
public class FastEditPresenter extends FastEditContract.Presenter {
    @Override
    public void loadDataList(int startPage) {
        mRxManage.add(mModel.loadData(startPage).subscribe(new RxSubscriber<NewsFavBean>(mContext, false) {
            @Override
            protected void _onNext(NewsFavBean data) {
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
    public void unFavAction(int objectId) {
        mRxManage.add(mModel.unFavAction(objectId).subscribe(new RxSubscriber<Map<Integer,Boolean>>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(Map<Integer,Boolean> result) {
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
