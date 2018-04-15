package cn.jungmedia.android.ui.blogger.presenter;

import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.ui.blogger.bean.BloggerBean;
import cn.jungmedia.android.ui.blogger.contract.BloggerContract;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/31. 下午10:44
 *
 *
 */
public class BloggerPresenterImp extends BloggerContract.Presenter {


    @Override
    public void getBloggerArticleList(int uid, int startPage) {
        mRxManage.add(mModel.getBloggerArticleList(uid, startPage).subscribe(new RxSubscriber<ArticleModel>(mContext, false) {
            @Override
            protected void _onNext(ArticleModel data) {
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
    public void getBloggerInfo(int uid) {
        mRxManage.add(mModel.getBloggerInfo(uid).subscribe(new RxSubscriber<BloggerBean>(mContext, false) {
            @Override
            protected void _onNext(BloggerBean data) {
                mView.returnBloggerInfo(data);
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void focusAction(int bloggerId, boolean status) {
        mRxManage.add(mModel.focusAction(bloggerId, status).subscribe(new RxSubscriber<Boolean>(mContext, false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("");
            }

            @Override
            protected void _onNext(Boolean result) {
                mView.stopLoading();
                mView.returnFocusBloggerState(result);
            }

            @Override
            protected void _onError(String message) {
                mView.stopLoading();
                mView.showErrorTip(message);
            }
        }));
    }
}
