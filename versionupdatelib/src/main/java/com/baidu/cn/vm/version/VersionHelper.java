package com.baidu.cn.vm.version;

import com.baidu.cn.vm.util.CommonUtil;
import com.baidu.cn.vm.util.HttpUtil;
import com.baidu.cn.vm.util.RequestUtil;
import com.baidu.cn.vm.version.dialog.Style4Dialog;

/**
 *
 */
public class VersionHelper {
    public static void update(String url, final BuilderImpl builder) {
        //发送网络请求
        RequestUtil.getInstance().reqeust(url, new HttpUtil.RequestCallback() {
            @Override
            public void onsuccess(String data) {
                //生成构造器
                if (builder != null) {
                    VersionManager.Builder builder1 = builder.builder(data);
                    if (builder1 != null) {
                        VCCallback callback = builder1.getVcCallback();
                        if (!CommonUtil.checkVersion(builder1.getActivity(), builder1.getVersionCode())) {
                            if (callback != null) {
                                callback.hasUpdate(false);
                            }
                            builder1 = null;
                            return;
                        }
                        if (callback != null) {
                            callback.hasUpdate(true);
                        }
                        if (builder1.getUpdater() == null) {
                            UpdaterIml updater = new UpdaterIml();
                            if (CommonUtil.isNotificationEnabled(builder1.getActivity())) {
                                updater.registerObserver(new FinanceNotification(builder1.getActivity(), builder1.getIcon()));
                            } else {
                                updater.registerObserver(new Style4Dialog(builder1.getActivity(), builder1.isForce(), builder1.getDialogTheme(), builder1.getOnShowListener()));
                            }
                            builder1.setUpdater(updater);
                        }
                        builder1.build().update();
                    }
                }
            }

            @Override
            public void onfialed(int errorcode) {

            }
        });
    }
}
