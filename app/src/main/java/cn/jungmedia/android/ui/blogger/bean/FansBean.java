package cn.jungmedia.android.ui.blogger.bean;


import java.util.List;

import cn.jungmedia.android.bean.Counter;
import cn.jungmedia.android.ui.user.bean.UserInfo;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/15. 下午10:02
 *
 *
 */
public class FansBean {

    private List<Favorite> favorites;

    private Counter counter;

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Favorite> getFavorites() {
        return this.favorites;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public Counter getCounter() {
        return this.counter;
    }

    public class Favorite {
        private int uid;

        private UserInfo.User user;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getUid() {
            return this.uid;
        }

        public void setUser(UserInfo.User user) {
            this.user = user;
        }

        public UserInfo.User getUser() {
            return this.user;
        }

    }
}
