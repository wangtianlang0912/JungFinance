package cn.jungmedia.android.ui.news.presenter;

import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.TopicModel;
import cn.jungmedia.android.ui.news.contract.TopicListContract;
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
 * @date 2018/3/24. 下午8:45
 *
 *
 */
public class TopicListPresenter extends TopicListContract.Presenter {
    @Override
    public void getListDataRequest(String uid, int startPage) {
        mRxManage.add(mModel.getListData(uid, startPage).subscribe(new RxSubscriber<TopicModel>(mContext, false) {
            @Override
            protected void _onNext(TopicModel data) {
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
}
