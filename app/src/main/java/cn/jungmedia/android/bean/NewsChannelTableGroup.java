package com.jung.android.bean;


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
 * @date 2018/3/31. 下午8:37
 *
 *
 */
public class NewsChannelTableGroup implements Serializable{

    List<NewsChannelTable> news;

    List<NewsChannelTable> colleges;

    List<NewsChannelTable> markets;

    public List<NewsChannelTable> getNews() {
        return news;
    }

    public void setNews(List<NewsChannelTable> news) {
        this.news = news;
    }

    public List<NewsChannelTable> getColleges() {
        return colleges;
    }

    public void setColleges(List<NewsChannelTable> colleges) {
        this.colleges = colleges;
    }

    public List<NewsChannelTable> getMarkets() {
        return markets;
    }

    public void setMarkets(List<NewsChannelTable> markets) {
        this.markets = markets;
    }
}
