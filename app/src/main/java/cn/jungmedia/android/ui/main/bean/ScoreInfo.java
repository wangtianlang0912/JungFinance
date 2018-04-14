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
 * @date 2018/3/18. 下午2:50
 *
 *
 */
public class ScoreInfo implements Serializable {

    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public class Member implements Serializable {
        private int uid;

        private int parentId;

        private int level;

        private int indexId;

        private int stime;

        private int objectId;

        private int scount;

        private int score;

        private int ltime;

        private int lcount;

        private int ctime;

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

        public void setLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return this.level;
        }

        public void setIndexId(int indexId) {
            this.indexId = indexId;
        }

        public int getIndexId() {
            return this.indexId;
        }

        public void setStime(int stime) {
            this.stime = stime;
        }

        public int getStime() {
            return this.stime;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

        public void setScount(int scount) {
            this.scount = scount;
        }

        public int getScount() {
            return this.scount;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return this.score;
        }

        public void setLtime(int ltime) {
            this.ltime = ltime;
        }

        public int getLtime() {
            return this.ltime;
        }

        public void setLcount(int lcount) {
            this.lcount = lcount;
        }

        public int getLcount() {
            return this.lcount;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

    }
}
