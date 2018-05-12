package com.baidu.cn.vm.version;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

/**
 *
 */
public interface NotificationBuilder {
    Intent createIntent(Context context);

    Notification createNotification(Context context);

    void notifyChanged(int progress);
}
