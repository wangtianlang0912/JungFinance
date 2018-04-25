package cn.jungmedia.android.ui.main.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import cn.jungmedia.android.bean.ArticleModel;
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
 * @date 2018/4/25. 下午11:44
 *
 *
 */
public class SearchContract {

    public interface Model extends BaseModel {

        Observable<ArticleModel> searchByKey(String key, int page);

    }

    public interface View extends BaseView {

        void returnNewsListData(ArticleModel newsSummaries);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract void searchByKey(String key, int page);

    }
}
