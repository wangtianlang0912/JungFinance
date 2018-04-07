package com.jung.finance.ui.news.contract;


import com.jung.finance.bean.CommentCreateModel;
import com.jung.finance.bean.CommentListModel;
import com.leon.common.base.BaseModel;
import com.leon.common.base.BasePresenter;
import com.leon.common.base.BaseView;

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
 * @date 2018/4/7. 下午10:58
 *
 *
 */
public class CommentListContract {

    public interface Model extends BaseModel {

        Observable<CommentListModel> getListData(int articleId, int startPage);

        Observable<CommentCreateModel> createComment(int articleId, String body);
    }

    public interface View extends BaseView {
        void returnCreateComment(CommentCreateModel model);

        void returnCommentList(CommentListModel model);
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        // 创建评论
        public abstract void createComment(int articleId, String value);

        //获取列表
        public abstract void getCommentList(int articleId, int p);
    }

}
