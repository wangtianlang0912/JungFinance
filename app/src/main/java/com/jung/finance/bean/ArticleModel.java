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
 * @date 2018/3/19. 下午11:59
 *
 *
 */
public class ArticleModel {

    private List<Article> articles;

    private Counter counter;

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return this.counter;
    }


    public class Column {
        private int objectId;

        private String title;

        private String keyword;

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return this.keyword;
        }

    }

    public enum ContentType {

        DEFAULT, AD, PIC;
    }

    public static class Article {
        private String summary;

        private int isHead;

        private String keyword;

        private String image;

        private int columnId;

        private long vtime;

        private String ptime;

        private String author;

        private String title;

        private String source;

        private int objectId;

        private Column column;

        private String subtitle;

        private int pv;

        private int fav;

//        private List<Attributes> attributes ;

        private int isRecommend;

        private BloggerModel.Media media;

        private String content;

        private ContentType type = ContentType.DEFAULT;

        public BloggerModel.Media getMedia() {
            return media;
        }

        public void setMedia(BloggerModel.Media media) {
            this.media = media;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSummary() {
            return this.summary;
        }

        public void setIsHead(int isHead) {
            this.isHead = isHead;
        }

        public int getIsHead() {
            return this.isHead;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return this.keyword;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return this.image;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public int getColumnId() {
            return this.columnId;
        }

        public void setVtime(int vtime) {
            this.vtime = vtime;
        }

        public long getVtime() {
            return this.vtime;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return this.author;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource() {
            return this.source;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

        public void setColumn(Column column) {
            this.column = column;
        }

        public Column getColumn() {
            return this.column;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getSubtitle() {
            return this.subtitle;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public int getPv() {
            return this.pv;
        }
//        public void setAttributes(List<Attributes> attributes){
//            this.attributes = attributes;
//        }
//        public List<Attributes> getAttributes(){
//            return this.attributes;
//        }


        public boolean hasFaved() {
            return fav == 1;
        }

        public void setFav(int fav) {
            this.fav = fav;
        }

        public ContentType getType() {
            return type;
        }

        public void setType(ContentType type) {
            this.type = type;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getIsRecommend() {
            return this.isRecommend;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
