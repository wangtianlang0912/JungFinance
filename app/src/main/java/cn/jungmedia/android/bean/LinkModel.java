package cn.jungmedia.android.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/20. 上午12:20
 *
 *
 */
public class LinkModel {

    private Link link;

    public void setLink(Link link) {
        this.link = link;
    }

    public Link getLink() {
        return this.link;
    }

    public class Link {
        private String image;

        private int ltime;

        private String title;

        private String wapImage;

        private String url;

        private int objectId;

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage() {
            return this.image;
        }

        public void setLtime(int ltime) {
            this.ltime = ltime;
        }

        public int getLtime() {
            return this.ltime;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setWapImage(String wapImage) {
            this.wapImage = wapImage;
        }

        public String getWapImage() {
            return this.wapImage;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

    }
}
