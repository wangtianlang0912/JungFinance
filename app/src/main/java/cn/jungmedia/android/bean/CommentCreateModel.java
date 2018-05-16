package cn.jungmedia.android.bean;


import cn.jungmedia.android.ui.user.bean.UserInfo;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/7. 下午10:25
 *
 *
 */
public class CommentCreateModel {

    private Comment fcomment;

    private Comment comment;

    private Comment mcomment;

    public Comment getFcomment() {
        return fcomment;
    }

    public void setFcomment(Comment fcomment) {
        this.fcomment = fcomment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Comment getMcomment() {
        return mcomment;
    }

    public void setMcomment(Comment mcomment) {
        this.mcomment = mcomment;
    }

    public static class Comment {


        private UserInfo.User user;


        private String body;

        private String title;

        private int touid;

        private int mtime;

        private int rCount;

        private int articleId;

        private int uid;

        private int parentId;

        private int indexId;

        private int status;

        private String remark;

        private int objectId;

        private int score;

        private int ctime;

        private String cTimeStr;


        private String parentTitle;
        private String parentContent;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Comment)) return false;

            Comment comment = (Comment) o;

            return getObjectId() == comment.getObjectId();

        }

        @Override
        public int hashCode() {
            return getObjectId();
        }

        public String getParentTitle() {
            return parentTitle;
        }

        public void setParentTitle(String parentTitle) {
            this.parentTitle = parentTitle;
        }

        public String getParentContent() {
            return parentContent;
        }

        public void setParentContent(String parentContent) {
            this.parentContent = parentContent;
        }

        public UserInfo.User getUser() {
            return user;
        }

        public void setUser(UserInfo.User user) {
            this.user = user;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUid() {
            return this.uid;
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

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
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

        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return this.score;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTouid() {
            return touid;
        }

        public void setTouid(int touid) {
            this.touid = touid;
        }

        public int getMtime() {
            return mtime;
        }

        public void setMtime(int mtime) {
            this.mtime = mtime;
        }

        public int getrCount() {
            return rCount;
        }

        public void setrCount(int rCount) {
            this.rCount = rCount;
        }

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public String getcTimeStr() {
            return cTimeStr;
        }

        public void setcTimeStr(String cTimeStr) {
            this.cTimeStr = cTimeStr;
        }
    }
}
