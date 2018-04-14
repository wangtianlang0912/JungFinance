package cn.jungmedia.android.ui.news.presenter;

import cn.jungmedia.android.bean.NewsDetail;
import cn.jungmedia.android.ui.news.contract.NewsDetailContract;
import cn.jungmedia.android.R;
import com.leon.common.baserx.RxSubscriber;
import com.leon.common.commonutils.ToastUitl;

/**
 * des:新闻详情
 * Created by xsf
 * on 2016.09.17:08
 */
public class NewsDetailPresenter extends NewsDetailContract.Presenter {
    @Override
    public void getOneNewsDataRequest(String postId) {
        mRxManage.add(mModel.getOneNewsData(postId).subscribe(new RxSubscriber<NewsDetail>(mContext) {
            @Override
            protected void _onNext(NewsDetail newsDetail) {
            mView.returnOneNewsData(newsDetail);
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showToastWithImg(message, R.drawable.ic_wrong);
            }
        }));
    }
}
