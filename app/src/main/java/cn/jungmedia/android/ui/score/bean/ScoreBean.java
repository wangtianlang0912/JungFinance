package cn.jungmedia.android.ui.score.bean;


import java.util.List;

import cn.jungmedia.android.bean.Counter;
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
 * @date 2018/4/15. 下午10:25
 *
 *
 */
public class ScoreBean {

    private List<Score> scores;

    private Member member;

    private UserInfo.User user;

    private Counter counter;

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public List<Score> getScores() {
        return this.scores;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    public void setUser(UserInfo.User user) {
        this.user = user;
    }

    public UserInfo.User getUser() {
        return this.user;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return this.counter;
    }


    public class Member {
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

    public class Score {
        private int uid;

        private int parentId;

        private int indexId;

        private int status;

        private String remark;

        private int objectId;

        private int score;

        private int ctime;

        private String showTime;

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
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

    }
}
