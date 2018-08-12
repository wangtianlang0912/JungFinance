package cn.jungmedia.android.ui.main.bean;


import java.io.Serializable;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/18. 下午3:03
 *
 *
 */
public class MediaInfo implements Serializable {

    private int uid;

    private int gznumTrack;

    private int status;

    private String qrImage;

    private int mtime;

    private int gznum;

    private int ctime;

    private int parentId;

    private int indexId;

    private String wechatNo;

    private int objectId;

    private String name;

    private String applicant;

    private int articleNum;

    private String coverImage;

    private String alias;

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return this.uid;
    }

    public void setGznumTrack(int gznumTrack) {
        this.gznumTrack = gznumTrack;
    }

    public int getGznumTrack() {
        return this.gznumTrack;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setQrImage(String qrImage) {
        this.qrImage = qrImage;
    }

    public String getQrImage() {
        return this.qrImage;
    }

    public void setMtime(int mtime) {
        this.mtime = mtime;
    }

    public int getMtime() {
        return this.mtime;
    }

    public void setGznum(int gznum) {
        this.gznum = gznum;
    }

    public int getGznum() {
        return this.gznum;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public int getCtime() {
        return this.ctime;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }

    public int getIndexId() {
        return this.indexId;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getWechatNo() {
        return this.wechatNo;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectId() {
        return this.objectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicant() {
        return this.applicant;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }

    public int getArticleNum() {
        return this.articleNum;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImage() {
        return this.coverImage;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}