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
 * @date 2018/3/20. 上午12:01
 *
 *
 */
public class BannerModel {

    private List<Banner> banners ;

    private Counter counter;

    public void setBanners(List<Banner> banners){
        this.banners = banners;
    }
    public List<Banner> getBanners(){
        return this.banners;
    }
    public void setCounter(Counter counter){
        this.counter = counter;
    }
    public Counter getCounter(){
        return this.counter;
    }
    public class Banner {
        private String classify;

        private int parentId;

        private String title;

        private int indexId;

        private int objectId;

        private int mtime;

        private String image;

//        private List<Attributes> attributes ;

        private int ctime;

        private String url;

        public void setClassify(String classify){
            this.classify = classify;
        }
        public String getClassify(){
            return this.classify;
        }
        public void setParentId(int parentId){
            this.parentId = parentId;
        }
        public int getParentId(){
            return this.parentId;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setIndexId(int indexId){
            this.indexId = indexId;
        }
        public int getIndexId(){
            return this.indexId;
        }
        public void setObjectId(int objectId){
            this.objectId = objectId;
        }
        public int getObjectId(){
            return this.objectId;
        }
        public void setMtime(int mtime){
            this.mtime = mtime;
        }
        public int getMtime(){
            return this.mtime;
        }
        public void setImage(String image){
            this.image = image;
        }
        public String getImage(){
            return this.image;
        }
        public void setCtime(int ctime){
            this.ctime = ctime;
        }
        public int getCtime(){
            return this.ctime;
        }
        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }

    }
}
