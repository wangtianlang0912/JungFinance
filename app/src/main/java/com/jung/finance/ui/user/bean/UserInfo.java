package com.jung.finance.ui.user.bean;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/17. 下午9:02
 *
 *
 */
public class UserInfo {
    private String token;

    private User user;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public class User {
        private int uid;

        private String logo;

        private String phone;

        private int btime;

        private int mtime;

        private boolean blacked;

        private int ctime;

        private int parentId;

        private int walletId;

        private int fCount;

        private int mCount;

        private int indexId;

        private String nick;

        private int objectId;

        private int role;

        private int aCount;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUid() {
            return this.uid;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo() {
            return this.logo;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return this.phone;
        }

        public void setBtime(int btime) {
            this.btime = btime;
        }

        public int getBtime() {
            return this.btime;
        }

        public void setMtime(int mtime) {
            this.mtime = mtime;
        }

        public int getMtime() {
            return this.mtime;
        }

        public void setBlacked(boolean blacked) {
            this.blacked = blacked;
        }

        public boolean getBlacked() {
            return this.blacked;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getCtime() {
            return this.ctime;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getParentId() {
            return this.parentId;
        }

        public void setWalletId(int walletId) {
            this.walletId = walletId;
        }

        public int getWalletId() {
            return this.walletId;
        }

        public void setFCount(int fCount) {
            this.fCount = fCount;
        }

        public int getFCount() {
            return this.fCount;
        }

        public void setMCount(int mCount) {
            this.mCount = mCount;
        }

        public int getMCount() {
            return this.mCount;
        }

        public void setIndexId(int indexId) {
            this.indexId = indexId;
        }

        public int getIndexId() {
            return this.indexId;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getNick() {
            return this.nick;
        }

        public void setObjectId(int objectId) {
            this.objectId = objectId;
        }

        public int getObjectId() {
            return this.objectId;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getRole() {
            return this.role;
        }

        public void setACount(int aCount) {
            this.aCount = aCount;
        }

        public int getACount() {
            return this.aCount;
        }

    }
}