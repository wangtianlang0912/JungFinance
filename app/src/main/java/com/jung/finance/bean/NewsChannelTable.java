package com.jung.finance.bean;


import java.io.Serializable;

public class NewsChannelTable implements Serializable {

    private String newsChannelName;
    private String newsChannelId;
    private boolean newsChannelSelect;
    private int newsChannelIndex;
    private Boolean newsChannelFixed;

    public NewsChannelTable() {
    }

    public NewsChannelTable(String newsChannelName) {
        this.newsChannelName = newsChannelName;
    }

    public NewsChannelTable(String newsChannelName, String newsChannelId, boolean newsChannelSelect, int newsChannelIndex, Boolean newsChannelFixed) {
        this.newsChannelName = newsChannelName;
        this.newsChannelId = newsChannelId;
        this.newsChannelSelect = newsChannelSelect;
        this.newsChannelIndex = newsChannelIndex;
        this.newsChannelFixed = newsChannelFixed;
    }

    public String getNewsChannelName() {
        return newsChannelName;
    }

    public void setNewsChannelName(String newsChannelName) {
        this.newsChannelName = newsChannelName;
    }

    public String getNewsChannelId() {
        return newsChannelId;
    }

    public void setNewsChannelId(String newsChannelId) {
        this.newsChannelId = newsChannelId;
    }

    public boolean getNewsChannelSelect() {
        return newsChannelSelect;
    }

    public void setNewsChannelSelect(boolean newsChannelSelect) {
        this.newsChannelSelect = newsChannelSelect;
    }

    public int getNewsChannelIndex() {
        return newsChannelIndex;
    }

    public void setNewsChannelIndex(int newsChannelIndex) {
        this.newsChannelIndex = newsChannelIndex;
    }

    public Boolean getNewsChannelFixed() {
        return newsChannelFixed;
    }

    public void setNewsChannelFixed(Boolean newsChannelFixed) {
        this.newsChannelFixed = newsChannelFixed;
    }


}
