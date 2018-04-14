package cn.jungmedia.android.ui.main.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/14. 下午9:48
 *
 *
 */
public class CrewBean {


    private Crew crew;

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public Crew getCrew() {
        return this.crew;
    }

    public class Crew {
        private int uid;

        private int parentId;

        private String phone;

        private int indexId;

        private int status;

        private int objectId;

        private String company;

        private String name;

        private int mtime;

        private String number;

        private int aid;

        private int ctime;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUid() {
            return this.uid;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getParentId() {
            return this.parentId;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return this.phone;
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

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompany() {
            return this.company;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setMtime(int mtime) {
            this.mtime = mtime;
        }

        public int getMtime() {
            return this.mtime;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getNumber() {
            return this.number;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getAid() {
            return this.aid;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

    }
}
