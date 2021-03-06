package cn.jungmedia.android.bean;


import java.io.Serializable;
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
 * @date 2018/3/31. 下午5:38
 *
 *
 */
public class ActivityModel {


    List<Activity> activities;

    Counter counter;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public enum Status {

        IDLE, SIGNUP, START, FINISH;

    }

    public class Activity implements Serializable {
        /**
         * 初始
         */
        public final static int STATUS_NONE = 0;

        /**
         * 报名
         */
        public final static int STATUS_SIGN = 1;

        /**
         * 开始
         */
        public final static int STATUS_START = 2;

        /**
         * 结束
         */
        public final static int STATUS_END = 3;


        private String startTime;

        private String endTime;


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

        public int getMtime() {
            return mtime;
        }

        public void setMtime(int mtime) {
            this.mtime = mtime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getIndexId() {
            return indexId;
        }

        public void setIndexId(int indexId) {
            this.indexId = indexId;
        }

        public void setStime(int stime) {
            this.stime = stime;
        }

        public int getStime() {
            return this.stime;
        }

        public void setEtime(int etime) {
            this.etime = etime;
        }

        public int getEtime() {
            return this.etime;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return this.address;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Status getStatus() {
            switch (this.status) {

                case STATUS_NONE:

                    return Status.IDLE;

                case STATUS_SIGN:

                    return Status.SIGNUP;

                case STATUS_START:

                    return Status.START;
                case STATUS_END:

                    return Status.FINISH;
            }
            return Status.IDLE;
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

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

        public void setPtime(int ptime) {
            this.ptime = ptime;
        }

        public int getPtime() {
            return this.ptime;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getCost() {
            return this.cost;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getCoverImage() {
            return this.coverImage;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }
    }
}
