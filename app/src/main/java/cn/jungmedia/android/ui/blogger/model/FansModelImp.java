package cn.jungmedia.android.ui.blogger.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.blogger.bean.FansBean;
import cn.jungmedia.android.ui.blogger.contract.FansContract;
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
 * @date 2018/4/15. 下午10:07
 *
 *
 */
public class FansModelImp implements FansContract.Model {
    @Override
    public Observable<FansBean> getFansList(int startPage) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getFansList(token, startPage)

                .map(new Func1<BaseRespose<FansBean>, FansBean>() {
                    @Override
                    public FansBean call(BaseRespose<FansBean> respose) {

                        FansBean bean = respose.data;
                        if (bean.getFavorites() != null) {
                            for (FansBean.Favorite favorite : bean.getFavorites()) {
                                if (favorite.getUser() != null) {
                                    String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + favorite.getUser().getLogo();
                                    favorite.getUser().setLogo(cooverImage);
                                }
                            }
                        }
                        return bean;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<FansBean>io_main());
    }
}
