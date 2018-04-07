package com.jung.finance.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/7. 下午5:23
 *
 *
 */
public class ActivityFavGetModel {

    private Favorite favorite;

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public Favorite getFavorite() {
        return this.favorite;
    }

    public class Favorite {
        private int entityId;

        private Activity activity;

        private int objectId;

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public int getEntityId() {
            return this.entityId;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public Activity getActivity() {
            return this.activity;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

    }

    public class Activity {
        private int stime;

        private int status;

        private int mtime;

        private int gznum;

        private int type;

        private int ctime;

        private String cost;

        private String url;

        private String content;

        private int parentId;

        private int indexId;

        private String address;

        private int etime;

        private int objectId;

        private String name;

        private int ptime;

        private String coverImage;

        public void setStime(int stime) {
            this.stime = stime;
        }

        public int getStime() {
            return this.stime;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
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

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getCost() {
            return this.cost;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
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

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return this.address;
        }

        public void setEtime(int etime) {
            this.etime = etime;
        }

        public int getEtime() {
            return this.etime;
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

        public void setPtime(int ptime) {
            this.ptime = ptime;
        }

        public int getPtime() {
            return this.ptime;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getCoverImage() {
            return this.coverImage;
        }

    }
}
