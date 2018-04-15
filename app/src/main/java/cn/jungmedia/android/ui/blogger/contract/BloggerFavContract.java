package cn.jungmedia.android.ui.blogger.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import java.util.Map;

import cn.jungmedia.android.ui.blogger.bean.BloggerFavBean;
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
public class BloggerFavContract {

    public interface Model extends BaseModel {

        Observable<BloggerFavBean> getMediaFavList(int startPage);

        Observable<Map<Integer, Boolean>> unFavAction(int uid);

    }

    public interface View extends BaseView {


        void returnListData(BloggerFavBean data);

        void returnUnFavAction(Map<Integer, Boolean> map);

    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void getMediaFavList(int startPage);

        public abstract void unFavAction(int uid);
    }
}
