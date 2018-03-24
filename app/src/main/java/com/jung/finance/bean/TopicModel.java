package com.jung.finance.bean;


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
 * @date 2018/3/20. 上午12:13
 *
 *
 */
public class TopicModel {

    private List<Theme> themes;

    private Counter counter;

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public List<Theme> getThemes() {
        return this.themes;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return this.counter;
    }

    public class Theme {
        private String summary;

        private int parentId;

        private String title;

        private int indexId;

        private String wapImage;

        private int objectId;

        private String keyword;

        private int mtime;

        private int pv;

        private int surveyId;

        private int ctime;

        private int articleNum;

        private String pubTime;

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSummary() {
            return this.summary;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getParentId() {
            return this.parentId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setIndexId(int indexId) {
            this.indexId = indexId;
        }

        public int getIndexId() {
            return this.indexId;
        }

        public void setWapImage(String wapImage) {
            this.wapImage = wapImage;
        }

        public String getWapImage() {
            return this.wapImage;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return this.keyword;
        }

        public void setMtime(int mtime) {
            this.mtime = mtime;
        }

        public int getMtime() {
            return this.mtime;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public int getPv() {
            return this.pv;
        }

        public void setSurveyId(int surveyId) {
            this.surveyId = surveyId;
        }

        public int getSurveyId() {
            return this.surveyId;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

        public void setArticleNum(int articleNum) {
            this.articleNum = articleNum;
        }

        public int getArticleNum() {
            return this.articleNum;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }
    }
}
