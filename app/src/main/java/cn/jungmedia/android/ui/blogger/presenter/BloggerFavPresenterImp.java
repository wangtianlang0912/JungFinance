package cn.jungmedia.android.ui.blogger.presenter;


import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import java.util.Map;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.ui.blogger.bean.BloggerFavBean;
import cn.jungmedia.android.ui.blogger.contract.BloggerFavContract;
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
 * @date 2018/4/15. 下午8:34
 *
 *
 */
public class BloggerFavPresenterImp extends BloggerFavContract.Presenter {

    @Override
    public void getMediaFavList(int startPage) {
        mRxManage.add(mModel.getMediaFavList(startPage).subscribe(new RxSubscriber<BaseRespose<BloggerFavBean>>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose<BloggerFavBean> data) {
                if (!MyUtils.verifyToken(data)) {
                    // error: "未找到授权凭证",
                    AppApplication.getInvalidCallback().onTokenInvalid();
                } else {
                    mView.returnListData(data);
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
    public void unFavAction(int uid) {
        mRxManage.add(mModel.unFavAction(uid).subscribe(new RxSubscriber<Map<Integer, Boolean>>(mContext, false) {
            @Override
            protected void _onNext(Map<Integer, Boolean> map) {
                mView.returnUnFavAction(map);
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
