package com.baidu.cn.vm.version;

import android.app.Activity;
import android.widget.Toast;

import com.baidu.cn.vm.service.ApkUpdateManager;
import com.baidu.cn.vm.service.ProgressInterface;
import com.baidu.cn.vm.service.UpdateInterface;
import com.baidu.cn.vm.util.CommonUtil;
import com.baidu.cn.vm.version.dialog.DialogInf;
import com.baidu.cn.vm.version.dialog.Style1Dialog;
import com.baidu.cn.vm.version.dialog.Style2Dialog;
import com.baidu.cn.vm.version.dialog.Style3Dialog;
import com.baidu.cn.vm.version.dialog.Style5Dialog;

/**
 *
 */
public class VersionManager {
    Activity mContext;
    VersionInfo mInfo;
    UpdateInterface mUpdateInterface;
    Updater mUpdater;
    VCCallback mVcCallback;
    int theme;
    android.content.DialogInterface.OnShowListener onShowListener;

    private VersionManager(Activity activity, VersionInfo info, int theme, android.content.DialogInterface.OnShowListener onShowListener, UpdateInterface updateInterface, Updater updater, VCCallback vcCallback) {
        this.mContext = activity;
        this.mInfo = info;
        this.mUpdateInterface = updateInterface;
        this.mUpdater = updater;
        this.mVcCallback = vcCallback;
        this.theme = theme;
        this.onShowListener = onShowListener;
    }

    public void update() {
        //
        try {
            if (mInfo == null) {
                throw new IllegalArgumentException("更新信息不能为空!请设置VersionInfo");
            }
            if (mContext == null || mContext.isFinishing()) {
                return;
            }
            ApkUpdateManager.getInstance().setApkFileDefault(mContext);
            if (mInfo.getDirName() != null && mInfo.getFileName() != null) {
                ApkUpdateManager.getInstance().setApkFilePath(mContext, mInfo.getDirName(), mInfo.getFileName());
            }
            //1本地检测
            boolean isVersion = ApkUpdateManager.getInstance().checkLocalApkIsNew(mContext, mInfo.getVersionName());
            //2是否强制更新
            if (mInfo.isForce()) {
                DialogInf dialogInf = new Style3Dialog(mContext, dialogInterface, onShowListener);
                dialogInf.createDialog(mInfo, theme).show();
            } else {//3不强制更新
                ////a.判断本地有已经下载最新包
                if (isVersion) {
                    DialogInf dialogInf = new Style2Dialog(mContext, dialogInterface, onShowListener);
                    dialogInf.createDialog(mInfo, theme).show();
                } else {
                    //手动检测更新
                    if (!mInfo.isAutoCheck()) {
                        DialogInf dialogInf = new Style1Dialog(mContext, dialogInterface, onShowListener);
                        dialogInf.createDialog(mInfo, theme).show();
                    } else {
                        ////b.本地没有包
                        ////c.wifi下下载，调用下载器
                        if (mInfo.isAutoDownloadByWifi() && CommonUtil.isWifi(mContext)) {
                            dialogInterface.download();
                        } else {
//                    ////d.非wifi下弹框提示（选择下载走c流程）
                            DialogInf dialogInf = new Style1Dialog(mContext, dialogInterface, onShowListener);
                            dialogInf.createDialog(mInfo, theme).show();
                        }
                    }
                }

            }
        } catch (Exception e) {

        }
    }

    private DialogInterface dialogInterface = new DialogInterface() {
        @Override
        public void download() {
            if (mUpdateInterface != null) {
                mUpdateInterface.download(mContext, mInfo.getApkUrl(), progressInterface);
            }
        }

        @Override
        public void install() {
            if (mUpdateInterface != null) {
                mUpdateInterface.install(mContext);
            }
        }

        @Override
        public void cancel(Type type) {
            if (type == Type.INSTALL_CANCEL) {
                DialogInf dialogInf = new Style5Dialog(mContext, dialogInterface, onShowListener);
                dialogInf.createDialog(mInfo, theme).show();
            }
        }
    };

    private ProgressInterface progressInterface = new ProgressInterface() {

        @Override
        public void end() {
            //自动安装条件：强制更新  开启app自动触发设置内检测功能  非wifi下自动检测
            if (!mInfo.isForce() && mInfo.isAutoCheck() && (CommonUtil.isWifi(mContext) && mInfo.isAutoDownloadByWifi())) {
                return;
            }
            mUpdateInterface.install(mContext);
//            if (mInfo.isForce()) {
//                mUpdateInterface.install(mContext);
//            } else {
//                //区分wifi，wifi下自动下载，非wifi情况
////                if(!mInfo.isAutoCheck() || !CommonUtil.isWifi(mContext) || !mInfo.isAutoDownloadByWifi()){
////                    DialogInf dialogInf = new Style2Dialog(dialogInterface);
////                    dialogInf.createDialog(mContext, mInfo).show();
////                }
//                if(!mInfo.isForce()&&mInfo.isAutoCheck()){//自动检测更新：区分wifi，wifi下自动下载，非wifi情况
//                    if (!mInfo.isForce()&&mInfo.isAutoCheck()&&CommonUtil.isWifi(mContext)&&mInfo.isAutoDownloadByWifi()) {
//                        return;
//                    }
//                    mUpdateInterface.install(mContext);
//                }else{//false 手动触发设置内检测功能
//                    mUpdateInterface.install(mContext);
//                }
//            }
        }

        @Override
        public void changgeProgress(int p) {
            if (!mInfo.isAutoCheck() || !CommonUtil.isWifi(mContext) || mInfo.isForce() || !mInfo.isAutoDownloadByWifi()) {
                if (mUpdater != null) {
                    mUpdater.update(p);
                }
            }
//            if (!CommonUtil.isWifi(mContext)) {
//                if (mUpdater != null) {
//                    mUpdater.update(p);
//                }
//            }  else if (mInfo.isForce()) {
//                if (mUpdater != null) {
//                    mUpdater.update(p);
//                }
//            } else if(!mInfo.isAutoDownloadByWifi()){
//                if (mUpdater != null) {
//                    mUpdater.update(p);
//                }
//            }
        }

        @Override
        public void error() {
            if (mUpdater != null) {
                mUpdater.update(-1);
            }
            if (mContext != null) {
                Toast.makeText(mContext, "下载失败,请重试", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public VersionInfo getVersionInfo() {
        return mInfo;
    }

    public void setVersionInfo(VersionInfo info) {
        this.mInfo = info;
    }

    public UpdateInterface getUpdateInterface() {
        return mUpdateInterface;
    }

    public void setmUpdateInterface(UpdateInterface updateInterface) {
        this.mUpdateInterface = updateInterface;
    }

    public static class Builder {
        Activity activity;
        int versionCode;//版本号
        String versionName;//版本名
        String dirName;//文件目录
        String fileName;//文件名称
        String apkUrl;//apk下载地址
        String describe;//更新描述
        boolean isForce;//是否强制更新
        boolean autoDownloadByWifi;//wifi下是否自动下载更新
        boolean autoCheck;//是否自动触发检测方法（非手动触发）
        int notifyIcon;
        int dialogTheme;
        UpdateInterface updateInterface;//下载器
        Updater updater;//更新器
        VCCallback vcCallback;
        android.content.DialogInterface.OnShowListener onShowListener;

        public Builder(Activity activity) {
            this.activity = activity;
            this.updateInterface = ApkUpdateManager.getInstance().getUpdateUtil();
//            this.updater = new UpdaterIml();
        }

        public VersionManager build() {
            return new VersionManager(activity, new VersionInfo(versionCode, versionName, dirName, fileName, apkUrl, describe, isForce, autoDownloadByWifi, autoCheck), dialogTheme, onShowListener, updateInterface, updater, vcCallback);
        }

        public Activity getActivity() {
            return activity;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getDirName() {
            return dirName;
        }

        public void setDirName(String dirName) {
            this.dirName = dirName;
        }

        public UpdateInterface getUpdateInterface() {
            return updateInterface;
        }

        public void setUpdateInterface(UpdateInterface updateInterface) {
            this.updateInterface = updateInterface;
        }

        public boolean isForce() {
            return isForce;
        }

        public void setIsForce(boolean isForce) {
            this.isForce = isForce;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Updater getUpdater() {
            return updater;
        }

        public void setUpdater(Updater updater) {
            this.updater = updater;
        }

        public int getIcon() {
            return notifyIcon;
        }

        public void setIcon(int icon) {
            this.notifyIcon = icon;
        }

        public VCCallback getVcCallback() {
            return vcCallback;
        }

        public void setVcCallback(VCCallback vcCallback) {
            this.vcCallback = vcCallback;
        }

        public boolean isAutoDownloadByWifi() {
            return autoDownloadByWifi;
        }

        public void setAutoDownloadByWifi(boolean autoDownloadByWifi) {
            this.autoDownloadByWifi = autoDownloadByWifi;
        }

        public boolean isAutoCheck() {
            return autoCheck;
        }

        public void setAutoCheck(boolean autoCheck) {
            this.autoCheck = autoCheck;
        }

        public int getDialogTheme() {
            return dialogTheme;
        }

        public void setDialogTheme(int dialogTheme) {
            this.dialogTheme = dialogTheme;
        }

        public android.content.DialogInterface.OnShowListener getOnShowListener() {
            return onShowListener;
        }

        public void setOnShowListener(android.content.DialogInterface.OnShowListener onShowListener) {
            this.onShowListener = onShowListener;
        }
    }
}
