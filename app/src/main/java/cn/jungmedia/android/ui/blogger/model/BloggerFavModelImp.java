package cn.jungmedia.android.ui.blogger.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.Map;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.ui.blogger.bean.BloggerFavBean;
import cn.jungmedia.android.ui.blogger.contract.BloggerFavContract;
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
 * @date 2018/4/15. 下午8:33
 *
 *
 */
public class BloggerFavModelImp implements BloggerFavContract.Model {
    @Override
    public Observable<BaseRespose<BloggerFavBean>> getMediaFavList(int startPage) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getMediaFavList(token, startPage)

                .map(new Func1<BaseRespose<BloggerFavBean>, BaseRespose<BloggerFavBean>>() {
                    @Override
                    public BaseRespose<BloggerFavBean> call(BaseRespose<BloggerFavBean> respose) {

                        BloggerFavBean bloggerFavBean = respose.data;

                        for (BloggerFavBean.Favorite favorite : bloggerFavBean.getFavorites()) {

                            String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + favorite.getImage();
                            favorite.setImage(cooverImage);
                        }
                        return respose;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<BloggerFavBean>>io_main());
    }

    @Override
    public Observable<Map<Integer, Boolean>> unFavAction(final int uid) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = Api.getDefault(HostType.Jung_FINANCE).unFocusMedia(token, uid);
        return observable.map(new Func1<BaseRespose<FavActionModel>, Map<Integer, Boolean>>() {
            @Override
            public Map<Integer, Boolean> call(BaseRespose<FavActionModel> baseRespose) {
                Map map = new HashMap<Integer, Boolean>();
                map.put(uid, baseRespose.success());
                return map;
            }
        })
                //声明线程调度
                .compose(RxSchedulers.<Map<Integer, Boolean>>io_main());
    }

}

