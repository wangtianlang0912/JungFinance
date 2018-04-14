package cn.jungmedia.android.ui.main.presenter;

import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.FastModel;
import cn.jungmedia.android.ui.news.contract.FastListContract;
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
 * @date 2018/3/28. 下午10:33
 *
 *
 */
public class FastListPresenterImp extends FastListContract.Presenter {
    @Override
    public void getNewsListDataRequest(int startPage) {
        mRxManage.add(mModel.getNewsListData(startPage).subscribe(new RxSubscriber<FastModel>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(FastModel articleModel) {
                mView.returnNewsListData(articleModel);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
