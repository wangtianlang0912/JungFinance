package cn.jungmedia.android.ui.main.model;

import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSchedulers;

import cn.jungmedia.android.api.Api;
import cn.jungmedia.android.api.HostType;
import cn.jungmedia.android.ui.main.bean.CrewBean;
import cn.jungmedia.android.ui.main.contract.ActivitySignupContract;
import cn.jungmedia.android.utils.MyUtils;
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
 * @date 2018/4/14. 下午9:52
 *
 *
 */
public class ActivitySignupModelImp implements ActivitySignupContract.Model {
    @Override
    public Observable<BaseRespose<CrewBean>> signup(int activeId, String phone, String name, int memberNum, String company) {
        String token = MyUtils.getToken();
        return Api.getDefault(HostType.Jung_FINANCE).joinActivity(token, String.valueOf(activeId), phone, name, memberNum, company)
                //声明线程调度
                .compose(RxSchedulers.<BaseRespose<CrewBean>>io_main());
    }
}
