package com.jung.finance.ui.news.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.ApiConstants;
import com.jung.finance.api.HostType;
import com.jung.finance.bean.CommentCreateModel;
import com.jung.finance.bean.CommentListModel;
import com.jung.finance.ui.news.contract.CommentListContract;
import com.jung.finance.utils.MyUtils;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

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
 * @date 2018/4/7. 下午11:01
 *
 *
 */
public class CommentListModelImp implements CommentListContract.Model {

    @Override
    public Observable<CommentCreateModel> createComment(int articleId, String body) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).createComment(token, articleId, body, null)
                .map(new Func1<BaseRespose<CommentCreateModel>, CommentCreateModel>() {
                    @Override
                    public CommentCreateModel call(BaseRespose<CommentCreateModel> baseRespose) {
                        return baseRespose.data;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<CommentCreateModel>io_main());
    }

    @Override
    public Observable<CommentListModel> getListData(int articleId, int p) {
        return Api.getDefault(HostType.Jung_FINANCE).getCommentList(articleId, 0, p, 20)
                .map(new Func1<BaseRespose<CommentListModel>, CommentListModel>() {
                    @Override
                    public CommentListModel call(BaseRespose<CommentListModel> baseRespose) {
                        CommentListModel listModel = baseRespose.data;
                        if (listModel != null && listModel.getComments() != null) {

                            for (CommentCreateModel.Comment comment : listModel.getComments()) {
                                String ptime = TimeUtil.formatTimeStampStr2Desc(comment.getCtime() * 1000);
                                comment.setcTimeStr(ptime);
                                String logo = comment.getUser().getLogo();
                                comment.getUser().setLogo(ApiConstants.URL + logo);
                            }
                        }
                        return listModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<CommentListModel>io_main());
    }
}
