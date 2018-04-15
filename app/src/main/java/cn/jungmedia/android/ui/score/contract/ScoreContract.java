package cn.jungmedia.android.ui.score.contract;


import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

import cn.jungmedia.android.ui.score.bean.ScoreBean;
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
 * @date 2018/4/15. 下午10:06
 *
 *
 */
public class ScoreContract {

    public interface Model extends BaseModel {

        Observable<ScoreBean> getScoreInfo(int startPage);

    }

    public interface View extends BaseView {


        void returnListData(ScoreBean data);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void getScoreInfo(int startPage);

    }

}
