package cn.jungmedia.android.ui.fav.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.ui.fav.bean.NewsFavBean;
import cn.jungmedia.android.ui.fav.contract.HqEditContract;
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
 * @date 2018/4/10. 下午11:23
 *
 *
 */
public class HqEditModelImp implements HqEditContract.Model {
    @Override
    public Observable<NewsFavBean> loadData(int startPage) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getArtileFavList(token, 2, startPage)
                .map(new Func1<BaseRespose<NewsFavBean>, NewsFavBean>() {
                    @Override
                    public NewsFavBean call(BaseRespose<NewsFavBean> respose) {

                        NewsFavBean newsFavBean = respose.data;

                        for (NewsFavBean.Favorite favorite : newsFavBean.getFavorites()) {
                            String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + favorite.getArticle().getImage();
                            favorite.getArticle().setImage(cooverImage);
                        }
                        return newsFavBean;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<NewsFavBean>io_main());
    }

    @Override
    public Observable<Map<Integer, Boolean>> unFavAction(final int objectId) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = Api.getDefault(HostType.Jung_FINANCE).unfavArticle(token, objectId);
        return observable.map(new Func1<BaseRespose<FavActionModel>, Map<Integer, Boolean>>() {
            @Override
            public Map<Integer, Boolean> call(BaseRespose<FavActionModel> baseRespose) {
                Map map = new HashMap<Integer, Boolean>();
                map.put(objectId, baseRespose.success());
                return map;
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<Map<Integer, Boolean>>io_main());
    }
}
