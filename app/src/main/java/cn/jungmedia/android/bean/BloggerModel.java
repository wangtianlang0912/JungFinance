package cn.jungmedia.android.bean;


import java.util.List;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/20. 上午12:08
 *
 *
 */
public class BloggerModel {

    private List<Media> medias;

    private Counter counter;

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public List<Media> getMedias() {
        return this.medias;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return this.counter;
    }


    public class Media {
        private int uid;

        private Article article;

        private String remark;

        private int objectId;

        private String name;

        private int gznum;

        private Blogger user;

        private int articleNum;

        private String coverImage;

        private Role role;

        private int status;

        public Role getRole() {
            if (role == null) {
                role = new Role();
            }
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUid() {
            return this.uid;
        }

        public void setArticle(Article article) {
            this.article = article;
        }

        public Article getArticle() {
            return this.article;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return this.remark;
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

        public void setGznum(int gznum) {
            this.gznum = gznum;
        }

        public int getGznum() {
            return this.gznum;
        }

        public void setBlogger(Blogger user) {
            this.user = user;
        }

        public Blogger getBlogger() {
            return this.user;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public class Role {

        int mCount;
        int role;

        public int getmCount() {
            return mCount;
        }

        public void setmCount(int mCount) {
            this.mCount = mCount;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }
    }

    public class Blogger {
        private int uid;

        private int mCount;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUid() {
            return this.uid;
        }

        public void setMCount(int mCount) {
            this.mCount = mCount;
        }

        public int getMCount() {
            return this.mCount;
        }

    }


    public class Article {
        private int total;

        private int pageSize;

        private int pageCount;

        private int pageIndex;

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return this.total;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageSize() {
            return this.pageSize;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageCount() {
            return this.pageCount;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageIndex() {
            return this.pageIndex;
        }

    }
}
