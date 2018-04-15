package cn.jungmedia.android.ui.blogger.bean;


import java.util.List;

import cn.jungmedia.android.bean.BloggerModel;
import cn.jungmedia.android.bean.Counter;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/15. 下午8:30
 *
 *
 */
public class BloggerFavBean {

    private List<Favorite> favorites;

    private Counter counter;

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Favorite> getFavorites() {
        return this.favorites;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return this.counter;
    }

    public static class Favorite {

        private String summary;

        private int uid;

        private int parentId;

        private String title;

        private int indexId;

        private String alias;

        private int status;

        private int objectId;

        private int entityId;

        private String image;

        private BloggerModel.Media media;

        private int ctime;


        public Favorite() {
        }

        public Favorite(int objectId) {
            this.objectId = objectId;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIndexId() {
            return indexId;
        }

        public void setIndexId(int indexId) {
            this.indexId = indexId;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public BloggerModel.Media getMedia() {
            return media;
        }

        public void setMedia(BloggerModel.Media media) {
            this.media = media;
        }

        public int getCtime() {
            return ctime;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Favorite)) return false;

            Favorite favorite = (Favorite) o;

            return getObjectId() == favorite.getObjectId();

        }

        @Override
        public int hashCode() {
            return getObjectId();
        }
    }
}
