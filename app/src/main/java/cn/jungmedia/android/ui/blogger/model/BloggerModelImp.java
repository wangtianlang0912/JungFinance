package cn.jungmedia.android.ui.blogger.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.bean.ArticleModel;
import cn.jungmedia.android.bean.FavActionModel;
import cn.jungmedia.android.ui.blogger.bean.BloggerBean;
import cn.jungmedia.android.ui.blogger.contract.BloggerContract;
import cn.jungmedia.android.ui.user.bean.UserInfo;
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
 * @date 2018/3/31. 下午10:43
 *
 *
 */
public class BloggerModelImp implements BloggerContract.Model {
    @Override
    public Observable<ArticleModel> getBloggerArticleList(int mediaId, int startPage) {
        UserInfo userInfo = MyUtils.getUserInfoFromPreference(AppApplication.getAppContext());
        int myUid = 0;
        if (userInfo != null) {
            myUid = userInfo.getUser().getUid();
        }
        return Api.getDefault(HostType.Jung_FINANCE).getBloggerArticleList(mediaId, myUid, startPage)
                .map(new Func1<BaseRespose<ArticleModel>, ArticleModel>() {
                    @Override
                    public ArticleModel call(BaseRespose<ArticleModel> respose) {

                        ArticleModel articleModel = respose.data;

                        for (ArticleModel.Article article : articleModel.getArticles()) {
                            String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + article.getImage();
                            article.setImage(cooverImage);
                        }
                        return articleModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ArticleModel>io_main());
    }

    @Override
    public Observable<BloggerBean> getBloggerInfo(int uid) {

        UserInfo userInfo = MyUtils.getUserInfoFromPreference(AppApplication.getAppContext());
        int myUid = 0;
        if (userInfo != null) {
            myUid = userInfo.getUser().getUid();
        }
        return Api.getDefault(HostType.Jung_FINANCE).getBloggerInfo(uid, myUid)
                .map(new Func1<BaseRespose<BloggerBean>, BloggerBean>() {
                    @Override
                    public BloggerBean call(BaseRespose<BloggerBean> respose) {

                        BloggerBean blogger = respose.data;
                        if (blogger != null && blogger.getMedia() != null) {
                            if (blogger.getMedia().getCoverImage() != null) {
                                String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + blogger.getMedia().getCoverImage();
                                blogger.getMedia().setCoverImage(cooverImage);
                            }
                        }
                        return blogger;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<BloggerBean>io_main());
    }

    @Override
    public Observable<BaseRespose<FavActionModel>> focusAction(int objectId, boolean status) {
        String token = MyUtils.getToken();
        Observable<BaseRespose<FavActionModel>> observable = null;
        if (status) {
            observable = Api.getDefault(HostType.Jung_FINANCE).unFocusMedia(token, objectId);
        } else {
            observable = Api.getDefault(HostType.Jung_FINANCE).focusMedia(token, objectId);
        }
        return observable
                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<FavActionModel>>io_main());
    }
}
