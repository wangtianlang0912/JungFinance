package cn.jungmedia.android.ui.score.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;
import com.leon.common.commonutils.TimeUtil;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.score.bean.ScoreBean;
import cn.jungmedia.android.ui.score.contract.ScoreContract;
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
 * @date 2018/4/15. 下午10:29
 *
 *
 */
public class ScoreModelImp implements ScoreContract.Model {
    @Override
    public Observable<ScoreBean> getScoreInfo(int startPage) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).getScoreInfo(token, startPage, "desc")

                .map(new Func1<BaseRespose<ScoreBean>, ScoreBean>() {
                    @Override
                    public ScoreBean call(BaseRespose<ScoreBean> respose) {

                        ScoreBean bean = respose.data;
                        if (bean != null && bean.getUser() != null) {
                            bean.getUser().setLogo(ApiConstants.getHost(HostType.Jung_FINANCE) + bean.getUser().getLogo());
                        }

                        for (ScoreBean.Score score : bean.getScores()) {
                            String showTime = TimeUtil.formatData(TimeUtil.dateFormatYMDHMS, score.getCtime());
                            score.setShowTime(showTime);
                        }

                        return bean;
                    }
                })
                //声明线程调度
                .compose(RxSchedulers.<ScoreBean>io_main());
    }
}
