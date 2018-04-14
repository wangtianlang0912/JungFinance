package com.jung.android.ui.main.model;


import com.jung.android.api.Api;
import com.jung.android.api.ApiConstants;
import com.jung.android.api.HostType;
import com.jung.android.bean.FastModel;
import com.jung.android.ui.news.contract.FastListContract;
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
 * @date 2018/3/28. 下午10:29
 *
 *
 */
public class FastModelImp implements FastListContract.Model {

    @Override
    public Observable<FastModel> getNewsListData(int startPage) {
        return Api.getDefault(HostType.Jung_FINANCE).getFastCommentList(Api.getCacheControl(), startPage, 20)
                .map(new Func1<BaseRespose<FastModel>, FastModel>() {
                    @Override
                    public FastModel call(BaseRespose<FastModel> baseRespose) {
                        FastModel FastModel = baseRespose.data;
                        for (FastModel.FastComment article : FastModel.getFastComments()) {
                            String coverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + article.getImage();
                            article.setImage(coverImage);

                            String ptime = TimeUtil.formatTimeStampStr2Desc(article.getVtime() * 1000);
                            article.setPtime(ptime);
                        }
                        return FastModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<FastModel>io_main());
    }
}
