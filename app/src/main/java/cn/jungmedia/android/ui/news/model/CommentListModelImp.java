package cn.jungmedia.android.ui.news.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.bean.CommentCreateModel;
import cn.jungmedia.android.bean.CommentListModel;
import cn.jungmedia.android.ui.news.contract.CommentListContract;
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
 * @date 2018/4/7. 下午11:01
 *
 *
 */
public class CommentListModelImp implements CommentListContract.Model {

    @Override
    public Observable<CommentCreateModel> createComment(int articleId, String body, int touid) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).createComment(token, articleId, body, touid == 0 ? null : String.valueOf(touid))
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
    public Observable<CommentListModel> getListData(int articleId, int p, int touid) {
        return Api.getDefault(HostType.Jung_FINANCE).getCommentList(articleId, p, 40, touid == 0 ? null : String.valueOf(touid))
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

                                if (comment.getTouid() > 0) {
                                    CommentCreateModel.Comment temp = new CommentCreateModel.Comment();
                                    temp.setObjectId(comment.getTouid());

                                    int index = listModel.getComments().indexOf(temp);
                                    if (index != -1) {
                                        CommentCreateModel.Comment parent = listModel.getComments().get(index);
                                        if (parent.getUser() != null) {
                                            comment.setParentTitle(parent.getUser().getNick());
                                        }
                                        comment.setParentContent(parent.getBody());
                                    }
                                }
                            }
                        }
                        return listModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<CommentListModel>io_main());
    }
}
