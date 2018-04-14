package com.jung.android.bean;


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

    private List<Column> college;

    private List<Column> news;

    private List<Column> market;

    public List<Column> getCollege() {
        return college;
    }

    public void setCollege(List<Column> college) {
        this.college = college;
    }

    public List<Column> getNews() {
        return news;
    }

    public void setNews(List<Column> news) {
        this.news = news;
    }

    public List<Column> getMarket() {
        return market;
    }

    public void setMarket(List<Column> market) {
        this.market = market;
    }


    public class Column {

        private String title;

        private int objectId;

        private int type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getObjectId() {
            return objectId;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
