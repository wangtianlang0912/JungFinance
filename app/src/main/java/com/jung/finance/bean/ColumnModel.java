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
 * @date 2018/3/19. 下午11:37
 *
 *
 */
public class ColumnModel {

    private List<Column> columns;

    private Counter counter;

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return this.counter;
    }

    public class Column {
        private String summary;

        private int parentId;

        private String title;

        private int indexId;

        private int status;

        private int objectId;

        private String keyword;

        private int mtime;

        private String image;

        private int columnId;

//        private List<Attribute> attributes;

        private int type;

        private int ctime;

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

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
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

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

    }

}
