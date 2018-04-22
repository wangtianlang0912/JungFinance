package cn.jungmedia.android.ui.blogger.presenter;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.blogger.bean.MediaInfoBean;
import cn.jungmedia.android.ui.blogger.contract.MediaUpdateContract;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/22. 下午10:00
 *
 *
 */
public class MediaUpdatePresenterImp extends MediaUpdateContract.Presenter {
    @Override
    public void submitMediaInfo(String mediaName, String realName, String wxId, String logoUrl, String wxUrl) {
        mRxManage.add(mModel.submitMediaInfo(mediaName, realName, wxId, logoUrl, wxUrl).subscribe(new RxSubscriber<BaseRespose<MediaInfoBean>>(mContext, false) {
            @Override
            protected void _onNext(BaseRespose<MediaInfoBean> data) {
                mView.returnData(data);
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
