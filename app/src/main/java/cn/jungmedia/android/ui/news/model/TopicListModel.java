package cn.jungmedia.android.ui.news.model;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.bean.TopicModel;
import cn.jungmedia.android.ui.news.contract.TopicListContract;
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
 * @date 2018/3/24. 下午4:57
 *
 *
 */
public class TopicListModel implements TopicListContract.Model {

    @Override
    public Observable<TopicModel> getListData(String uid, int startPage) {
        return Api.getDefault(HostType.Jung_FINANCE).getTopicList(Api.getCacheControl(), uid, startPage)
                .map(new Func1<BaseRespose<TopicModel>, TopicModel>() {
                    @Override
                    public TopicModel call(BaseRespose<TopicModel> respose) {

                        TopicModel topicListModel = respose.data;
                        for (TopicModel.Theme theme : topicListModel.getThemes()) {
                            String cooverImage = ApiConstants.getHost(HostType.Jung_FINANCE) + theme.getWapImage();
                            theme.setWapImage(cooverImage);

                            String ctime = TimeUtil.formatData(TimeUtil.dateFormatYMDHMS, theme.getCtime() * 1000);
                            theme.setPubTime(ctime);
                        }

                        return topicListModel;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<TopicModel>io_main());
    }
}
