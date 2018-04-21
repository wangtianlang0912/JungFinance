package cn.jungmedia.android.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/21. 下午2:36
 *
 *
 */
public class VoteModel {

    VoteItem article;

    public VoteItem getArticle() {
        return article;
    }

    public void setArticle(VoteItem article) {
        this.article = article;
    }

    public class VoteItem {

        int oppose;
        int objectId;
        int support;

        public int getOppose() {
            return oppose;
        }

        public void setOppose(int oppose) {
            this.oppose = oppose;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getSupport() {
            return support;
        }

        public void setSupport(int support) {
            this.support = support;
        }
    }

}
