package com.baidu.cn.vm.version;

/**
 *
 */
public class VersionInfo {
    private int versionCode;//版本号
    private String versionName;//版本名
    private String dirName;//文件目录
    private String fileName;//文件名称
    private String apkUrl;//apk下载地址
    private String describe;//更新描述
    private boolean isForce;//是否强制更新
    private boolean autoDownloadByWifi;//wifi下自动更新
    private boolean autoCheck;//自动检查更新

    public VersionInfo() {
    }

    public VersionInfo(int versionCode, String versionName, String dirName, String fileName, String apkUrl, String describe, boolean isForce, boolean autoDownloadByWifi, boolean autoCheck) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.dirName = dirName;
        this.fileName = fileName;
        this.apkUrl = apkUrl;
        this.describe = describe;
        this.isForce = isForce;
        this.autoDownloadByWifi = autoDownloadByWifi;
        this.autoCheck = autoCheck;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setIsForce(boolean isForce) {
        this.isForce = isForce;
    }

    public boolean isAutoDownloadByWifi() {
        return autoDownloadByWifi;
    }

    public void setAutoDownloadByWifi(boolean isAutoDownloadByWifi) {
        this.autoDownloadByWifi = isAutoDownloadByWifi;
    }

    public boolean isAutoCheck() {
        return autoCheck;
    }

    public void setAutoCheck(boolean autoCheck) {
        this.autoCheck = autoCheck;
    }
}
