package com.jung.finance.bean;


import com.google.gson.annotations.SerializedName;

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
 * @date 2018/3/28. 下午11:00
 *
 *
 */
public class FastModel {

    @SerializedName("articles")
    private  List<FastComment> fastComments;
    private Counter counter;

    public List<FastComment> getFastComments() {
        return fastComments;
    }

    public void setFastComments(List<FastComment> fastComments) {
        this.fastComments = fastComments;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public class FastComment {
        private String content;

        private String author;

        private String title;

        private int sort;

        private int objectId;

        private String image;

        private int pv;

        private long vtime;

        private String pTime;
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setAuthor(String author){
            this.author = author;
        }
        public String getAuthor(){
            return this.author;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setSort(int sort){
            this.sort = sort;
        }
        public int getSort(){
            return this.sort;
        }
        public void setObjectId(int objectId){
            this.objectId = objectId;
        }
        public int getObjectId(){
            return this.objectId;
        }
        public void setImage(String image){
            this.image = image;
        }
        public String getImage(){
            return this.image;
        }
        public void setPv(int pv){
            this.pv = pv;
        }
        public int getPv(){
            return this.pv;
        }
        public void setVtime(long vtime){
            this.vtime = vtime;
        }
        public long getVtime(){
            return this.vtime;
        }

        public String getpTime() {
            return pTime;
        }

        public void setPtime(String pTime) {
            this.pTime = pTime;
        }
    }

}
