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
 * @date 2018/5/17. 上午12:01
 *
 *
 */
public class ArticleRelevant {

    private List<Articles> articles;

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public class Articles {

        private Detail detail;
        private int sourceId;

        public void setDetail(Detail detail) {
            this.detail = detail;
        }

        public Detail getDetail() {
            return detail;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }

        public int getSourceId() {
            return sourceId;
        }

    }

    public class Column {

        private String title;
        private int objectId;
        private String keyword;

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }

    }

    public class Detail {

        private String summary;
        private int isHead;
        private String keyword;
        private String image;
        private int columnId;
        private long vtime;
        private String author;
        private String title;
        private String source;
        private int objectId;
        private Column column;
        private int pv;
        private List<String> attributes;
        private int isRecommend;
        private String ptime;

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSummary() {
            return summary;
        }

        public void setIsHead(int isHead) {
            this.isHead = isHead;
        }

        public int getIsHead() {
            return isHead;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setColumnId(int columnId) {
            this.columnId = columnId;
        }

        public int getColumnId() {
            return columnId;
        }

        public void setVtime(long vtime) {
            this.vtime = vtime;
        }

        public long getVtime() {
            return vtime;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return author;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource() {
            return source;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setColumn(Column column) {
            this.column = column;
        }

        public Column getColumn() {
            return column;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public int getPv() {
            return pv;
        }

        public void setAttributes(List<String> attributes) {
            this.attributes = attributes;
        }

        public List<String> getAttributes() {
            return attributes;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getPtime() {
            return ptime;
        }
    }
}
