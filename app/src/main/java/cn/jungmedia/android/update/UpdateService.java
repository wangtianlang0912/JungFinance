package cn.jungmedia.android.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.main.activity.MainActivity;

public class UpdateService extends Service {

    private Thread notifyingThread;
    private final static String SERVICE_NAME = "UPGRADE_SERVICE";

    private File updateDir = null;
    private File updateFile = null;

    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;

    private Intent updateIntent = null;
    private PendingIntent updatePendingIntent = null;

    public static final int DOWNLOAD_COMPLETE = 0;
    public static final int DOWNLOAD_FAIL = 1;

    private OnlineVersion versionInfo;

    private int notiCode = 10001;

    public IBinder onBind(Intent arg0) {
        return null;
    }

    @SuppressWarnings("deprecation")
    private String getDownLoadDir(Context context) {
        String downLoadRootDir = null;

        if (UpdateUtils.SDCardCanUse()) {
            File SDFile = Environment.getExternalStorageDirectory();
            downLoadRootDir = SDFile.getPath();
            if (!downLoadRootDir.endsWith(File.separator)) {
                downLoadRootDir = downLoadRootDir + File.separatorChar;
            }
            downLoadRootDir = downLoadRootDir + "app";
        } else {
            File f = context.getDir("apk", Context.MODE_PRIVATE | Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
            downLoadRootDir = f.getPath();
        }

        return downLoadRootDir;
    }

    public void onCreate() {
        updateDir = new File(this.getDownLoadDir(this));
        updateFile = new File(updateDir.getPath(), getPackageName() + ".apk");

        this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        this.updateNotification = new Notification();

        updateIntent = new Intent(this, MainActivity.class);
        updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

        updateNotification.icon = R.mipmap.ic_launcher;
        updateNotification.flags |= Notification.FLAG_AUTO_CANCEL;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        versionInfo = (OnlineVersion) intent.getSerializableExtra("OnlineVersion");

        if (versionInfo == null) {
            stopSelf();
        } else if (notifyingThread == null) {
            notifyingThread = new Thread(null, new updateRunnable(), SERVICE_NAME);
            notifyingThread.setDaemon(true);
            notifyingThread.start();
        }

        return Service.START_NOT_STICKY;
    }

    class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            message.what = DOWNLOAD_COMPLETE;
            try {
                if (!updateDir.exists()) {
                    updateDir.mkdirs();
                }
                if (!updateFile.exists()) {
                    updateFile.createNewFile();
                } else {
                    updateFile.delete();
                    updateFile.createNewFile();
                }

                long downloadSize = downloadUpdateFile(versionInfo.getUrl(), updateFile);
                if (downloadSize > 0) {
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                Log.e("upgrade error", "error : " + ex.getMessage());
                message.what = DOWNLOAD_FAIL;
                updateHandler.sendMessage(message);
            }
        }
    }

    private Handler updateHandler = new Handler() {
        @SuppressWarnings("deprecation")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    Uri uri = Uri.fromFile(updateFile);
                    Intent installIntent = new Intent(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                    updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, installIntent, 0);
                    updateNotification = new Notification.Builder(getApplicationContext())
                            .setContentTitle(getAppName())
                            .setContentText("下载完成,点击安装")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .build();
                    updateNotificationManager.notify(notiCode, updateNotification);
                    if (!UpdateUtils.isNotificationEnabled(UpdateService.this)) {
                        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(installIntent);
                    }

                    stopSelf();

                    break;

                case DOWNLOAD_FAIL:
                    updateNotification = new Notification.Builder(getApplicationContext())
                            .setContentTitle(getAppName())
                            .setContentText("下载失败")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .build();
                    updateNotificationManager.notify(notiCode, updateNotification);

                    stopSelf();

                    break;
            }
        }
    };


    private String getAppName() {
        try {
            PackageManager pm = getApplication().getPackageManager();
            PackageInfo pkg = pm.getPackageInfo(getApplication().getPackageName(), 0);
            return pkg.applicationInfo.loadLabel(getPackageManager()).toString();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return getPackageName();
    }

    private void commandFile(String filePath) {
        if (filePath != null && filePath.startsWith("/data")) {
            String[] command = {"chmod", "777", filePath};
            ProcessBuilder builder = new ProcessBuilder(command);
            try {
                builder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            Log.i("download", downloadUrl);

            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(60000);
            httpConnection.setReadTimeout(60000);

            httpConnection.connect();
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail! not found");
            }
            is = httpConnection.getInputStream();

            this.commandFile(saveFile.getPath());

            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;
            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                if ((int) (totalSize * 100 / updateTotalSize) - 1 > downloadCount) {
                    downloadCount += 1;
                    updateIntent = new Intent(this, MainActivity.class);
                    updateIntent.putExtra("downloadding", true);
                    updatePendingIntent = PendingIntent.getActivity(UpdateService.this, 0, updateIntent, 0);
                    updateNotification = new Notification.Builder(getApplicationContext())
                            .setContentTitle(getAppName())
                            .setContentText("已完成: " + (int) totalSize * 100 / updateTotalSize + "%")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .build();

                    updateNotificationManager.notify(notiCode, updateNotification);
                }
            }
        } catch (Exception ex) {
            throw new Exception("fail!" + ex.getMessage());
        } finally {
            if (fos != null) {
                fos.close();
            }

            if (is != null) {
                is.close();
            }

            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return totalSize;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
