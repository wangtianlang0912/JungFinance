package cn.jungmedia.android.ui.fav.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.bean.ActivityFavModel;
import cn.jungmedia.android.ui.fav.bean.ActiveFavBean;
import cn.jungmedia.android.ui.fav.contract.ActivityEditContract;
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
 * @date 2018/4/10. 下午11:21
 *
 *
 */
public class ActivityEditModelImp implements ActivityEditContract.Model {
    @Override
    public Observable<ActiveFavBean> loadData(int startPage) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getActivityFavList(token, startPage)
                .map(new Func1<BaseRespose<ActiveFavBean>, ActiveFavBean>() {
                    @Override
                    public ActiveFavBean call(BaseRespose<ActiveFavBean> respose) {

                        ActiveFavBean activeFavBean = respose.data;

                        for (ActiveFavBean.Favorite favorite : activeFavBean.getFavorites()) {
                            String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + favorite.getActivity().getCoverImage();
                            favorite.getActivity().setCoverImage(cooverImage);
                        }
                        return activeFavBean;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ActiveFavBean>io_main());
    }

    @Override
    public Observable<Map<Integer, Boolean>> unFavAction(final int objectId) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<ActivityFavModel>> observable = Api.getDefault(HostType.Jung_FINANCE).unfavActivity(token, objectId);
        return observable.map(new Func1<BaseRespose<ActivityFavModel>, Map<Integer, Boolean>>() {
            @Override
            public Map<Integer, Boolean> call(BaseRespose<ActivityFavModel> baseRespose) {
                Map map = new HashMap<Integer, Boolean>();
                map.put(objectId, baseRespose.success());
                return map;
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<Map<Integer, Boolean>>io_main());
    }
}
