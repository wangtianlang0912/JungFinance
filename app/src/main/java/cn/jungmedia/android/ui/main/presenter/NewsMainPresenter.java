package cn.jungmedia.android.ui.main.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import java.util.List;

import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.bean.NewsChannelTable;
import cn.jungmedia.android.ui.main.contract.NewsMainContract;
import rx.functions.Action1;

/**
 * des:
 * Created by xsf
 * on 2016.09.17:43
 */
public class NewsMainPresenter extends NewsMainContract.Presenter {

    @Override
    public void onStart() {
        super.onStart();
        //监听新闻频道变化刷新
        mRxManage.on(AppConstant.NEWS_CHANNEL_CHANGED, new Action1<List<NewsChannelTable>>() {

            @Override
            public void call(List<NewsChannelTable> newsChannelTables) {
                if (newsChannelTables != null) {
                    mView.returnMineNewsChannels(newsChannelTables);
                }
            }
        });
    }

    @Override
    public void lodeMineChannelsRequest() {
        mRxManage.add(mModel.lodeMineNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext, false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void deviceRegister() {
        mRxManage.add(mModel.deviceRegister().subscribe(new RxSubscriber<BaseRespose>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose baseRespose) {
                mView.returnDeviceRegister();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
