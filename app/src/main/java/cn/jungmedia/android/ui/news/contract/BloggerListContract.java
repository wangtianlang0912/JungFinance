package cn.jungmedia.android.ui.news.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;
import com.leon.common.basebean.BaseRespose;

import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.bean.FavActionModel;
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
 * @date 2018/3/24. 下午4:54
 *
 *
 */
public class BloggerListContract {

    public interface Model extends BaseModel {
        //请求获取新闻
        Observable<BloggerModel> getListData(String uid, int startPage);

        Observable<BaseRespose<FavActionModel>> focusAction(int objectId, boolean status);

    }

    public interface View extends BaseView {
        void returnListData(BloggerModel data);

        void returnFocusBloggerState(BaseRespose<FavActionModel> result, boolean b);
    }

    public abstract static class Presenter extends BasePresenter<BloggerListContract.View, BloggerListContract.Model> {

        public abstract void getBloggerListDataRequest(int startPage);

        public abstract void focusAction(int bloggerId, boolean status);

    }
}
