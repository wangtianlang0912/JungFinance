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
 * @date 2018/4/7. 下午10:41
 *
 *
 */
public class CommentListModel {


    List<CommentCreateModel.Comment> comments;

    Counter counter;

    public List<CommentCreateModel.Comment> getComments() {
        return comments;
    }

    public void setComments(List<CommentCreateModel.Comment> comments) {
        this.comments = comments;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }
}
