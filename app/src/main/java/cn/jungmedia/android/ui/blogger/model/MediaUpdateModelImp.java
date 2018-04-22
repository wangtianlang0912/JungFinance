package cn.jungmedia.android.ui.blogger.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.ui.blogger.bean.MediaInfoBean;
import cn.jungmedia.android.ui.blogger.contract.MediaUpdateContract;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.utils.MyUtils;
import rx.Observable;
import rx.functions.Func1;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/22. 下午9:59
 *
 *
 */
public class MediaUpdateModelImp implements MediaUpdateContract.Model {

    @Override
    public Observable<BaseRespose<MediaInfoBean>> submitMediaInfo(String mediaName, String realName, String wxId, String logoUrl, String wxUrl) {
        String token = MyUtils.getToken();
        UserInfo userInfo = MyUtils.getUserInfoFromPreference(AppApplication.getAppContext());
        int mediaId = 0;
        if (userInfo != null && userInfo.getUser() != null && userInfo.getUser().getMedia() != null) {
            mediaId = userInfo.getUser().getMedia().getObjectId();
        }
        Observable<BaseRespose<MediaInfoBean>> observable;
        if (mediaId == 0) {
            observable = Api.getDefault(HostType.Jung_FINANCE).createMediaInfo(token, mediaName, logoUrl, realName, wxUrl, 0, wxId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).setMediaInfo(token, mediaId, mediaName, logoUrl, realName, wxUrl, 0, wxId);
        }

        return observable.map(new Func1<BaseRespose<MediaInfoBean>, BaseRespose<MediaInfoBean>>() {
            @Override
            public BaseRespose<MediaInfoBean> call(BaseRespose<MediaInfoBean> respose) {

                MediaInfoBean bean = respose.data;
                String coverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + bean.getMedia().getCoverImage();
                bean.getMedia().setCoverImage(coverImage);

                String wxImage = ApiConstants.getHost(HostType.Jung_FINANCE) + bean.getMedia().getQrImage();
                bean.getMedia().setQrImage(wxImage);
                return respose;
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<MediaInfoBean>>io_main());
    }

}
