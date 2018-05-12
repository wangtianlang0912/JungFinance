package com.baidu.cn.vm.version;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.baidu.cn.vm.util.CPResourceUtil;

/**
 *
 */
public class FinanceNotification implements NotificationBuilder, ProgressObserver {
    final int NOTIFYID = 10001;
    int notifyIcon;
    private Context mContext;
    private NotificationManager mManager;
    private Notification mNotification;

    public FinanceNotification(Context context,int notifyIcon) {
        this.mContext = context;
        this.notifyIcon = notifyIcon;
    }

    @Override
    public Intent createIntent(Context context) {
        Intent intent = new Intent();
        intent.putExtra("downloadding", true);
        return intent;
    }

    @Override
    public Notification createNotification(Context context) {
        Notification notification = new Notification();
        notification.icon = notifyIcon;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        int layoutId = CPResourceUtil.getLayoutId(context,"notification_layout");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),layoutId);
        notification.contentView = remoteViews;
        return notification;
    }

    public void notifyChanged(int progress) {
        try{
            if (mNotification == null) {
                mNotification = createNotification(mContext);
                mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

                int notifyIcon =CPResourceUtil.getId(mContext,"imageView");
                mNotification.contentView.setImageViewResource(notifyIcon,notifyIcon);
            }
            int rate =CPResourceUtil.getId(mContext,"rate");
            mNotification.contentView.setTextViewText(rate, "已下载" + progress + "%");
            int progressResId =CPResourceUtil.getId(mContext,"progress");
            mNotification.contentView.setProgressBar(progressResId, 100, progress, false);
            mManager.notify(NOTIFYID, mNotification);
            if (progress == 100) {
//            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, createIntent(mContext), 0);
//            mNotification.setLatestEventInfo(mContext, "下载完成", "已下载完成,可点击安装", pendingIntent);
                mManager.cancel(NOTIFYID);
            }else if(progress ==-1){
                mManager.cancel(NOTIFYID);
            }
        }catch (Exception e){}

    }

    @Override
    public void onChanged(int progress) {
        notifyChanged(progress);
    }
}
