package com.jung.finance.ui.news.model;

import com.jung.finance.api.Api;
import com.jung.finance.api.ApiConstants;
import com.jung.finance.api.HostType;
import com.jung.finance.bean.BloggerModel;
import com.jung.finance.ui.news.contract.BloggerListContract;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

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
 * @date 2018/3/24. 下午4:57
 *
 *
 */
public class BloggerListModel implements BloggerListContract.Model {

    @Override
    public Observable<BloggerModel> getListData(String uid, int startPage) {
        return Api.getDefault(HostType.Jung_FINANCE).getBloggerList(Api.getCacheControl(), uid, startPage)
                .map(new Func1<BaseRespose<BloggerModel>, BloggerModel>() {
                    @Override
                    public BloggerModel call(BaseRespose<BloggerModel> respose) {

                        BloggerModel bloggerModel = respose.data;
                        for (BloggerModel.Media media : bloggerModel.getMedias()) {
                            String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + media.getCoverImage();
                            media.setCoverImage(cooverImage);
                        }

                        return bloggerModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<BloggerModel>io_main());
    }
}
