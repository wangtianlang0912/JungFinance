package cn.jungmedia.android.ui.blogger.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import cn.jungmedia.android.ui.blogger.bean.MediaInfoBean;
import rx.Observable;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/22. 下午9:57
 *
 *
 */
public class MediaUpdateContract {

    public interface Model extends BaseModel {

        Observable<BaseRespose<MediaInfoBean>> submitMediaInfo(String mediaName, String alias, String realName, String wxId, String logoUrl, String wxUrl);

    }

    public interface View extends BaseView {


        void returnData(BaseRespose<MediaInfoBean> respose);

    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void submitMediaInfo(String mediaName, String alias, String realName, String wxId, String logoUrl, String wxUrl);

    }
}
