package com.jung.android.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/6. 下午10:03
 *
 *
 */
public class ArticleDetail {

    String url;

    ArticleModel.Article article;

//    patrons


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArticleModel.Article getArticle() {
        return article;
    }

    public void setArticle(ArticleModel.Article article) {
        this.article = article;
    }
}
