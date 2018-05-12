package cn.jungmedia.android.update;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author LIU ZHONG LEI
 * @Company SINA
 * @Copyright 2011-2012
 * @date 2012-5-31 上午9:51:20
 * @Version 1.0
 */
public class OnlineVersion implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5422464644267359236L;

    public String version;
    public String url;
    public String force;
    public String forceVersion;
    public String md5;
    public String description;
    @SerializedName("version_code")
    public int versionCode;

    public boolean forceUpdate;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getForceVersion() {
        return forceVersion;
    }

    public void setForceVersion(String forceVersion) {
        this.forceVersion = forceVersion;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public class VersionModel implements Serializable{
        public String status;
        public String msg;
        public String logid;
        OnlineVersion data;
    }

}
