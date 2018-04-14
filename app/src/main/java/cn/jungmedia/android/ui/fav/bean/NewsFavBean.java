package cn.jungmedia.android.ui.fav.bean;


import java.util.List;

import cn.jungmedia.android.bean.ArticleModel;
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
 * @date 2018/4/14. 下午4:16
 *
 *
 */
public class NewsFavBean {

    private List<Favorite> favorites;

    private Counter counter;

    public static class Favorite {
        private ArticleModel.Article article;

        private int entityId;

        private int objectId;

        public Favorite() {
        }

        public Favorite(int objectId) {
            this.objectId = objectId;
        }

        public void setArticle(ArticleModel.Article article) {
            this.article = article;
        }

        public ArticleModel.Article getArticle() {
            return this.article;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public int getEntityId() {
            return this.entityId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
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

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }
}
