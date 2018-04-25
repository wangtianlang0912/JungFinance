package cn.jungmedia.android.ui.main.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.ui.main.contract.SearchContract;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/25. 下午11:47
 *
 *
 */
public class SearchPresenter extends SearchContract.Presenter {
    @Override
    public void searchByKey(String key, int page) {
        mRxManage.add(mModel.searchByKey(key, page).subscribe(new RxSubscriber<ArticleModel>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ArticleModel articleModel) {
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
